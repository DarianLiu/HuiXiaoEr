package com.geek.huixiaoer.mvp.person.presenter;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.common.config.EventBusTags;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.mvp.common.ui.activity.ForgetPasswordActivity;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.entity.SingleResultBean;
import com.geek.huixiaoer.storage.entity.UserBean;
import com.geek.huixiaoer.storage.entity.UserInfoBean;
import com.geek.huixiaoer.storage.entity.recycle.RankBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.person.contract.UpdateMobileContract;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;

import org.simple.eventbus.EventBus;

import java.util.concurrent.TimeUnit;


@ActivityScope
public class UpdateMobilePresenter extends BasePresenter<UpdateMobileContract.Model, UpdateMobileContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public UpdateMobilePresenter(UpdateMobileContract.Model model, UpdateMobileContract.View rootView) {
        super(model, rootView);
    }

    public void mobileUpdate(String token, String mobile) {
        mModel.mobileUpdate(token, mobile).compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mApplication))
                .subscribeWith(new ErrorHandleSubscriber<BaseArrayData<RankBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseArrayData<RankBean> rankBeanBaseArrayData) {
                        mRootView.showMessage("修改成功");
                        UserBean userBean = DataHelper.getDeviceData(mApplication, Constants.SP_USER_INFO);
                        userBean.setMobile(mobile);
                        DataHelper.saveDeviceData(mApplication, Constants.SP_USER_INFO, userBean);
                        EventBus.getDefault().post(mobile, EventBusTags.mobileUpdate);
                        mRootView.killMyself();
                    }
                });
    }

    private String veryCode = "";

    /**
     * @param mobile 手机号码
     * @param type   0:注册/1:忘记密码
     */
    public void sendCaptcha(String mobile, int type) {
        mModel.verificationCode(mobile, type).retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<SingleResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull SingleResultBean singleResultBean) {
                        veryCode = singleResultBean.getResult();
                        Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                                .take(Constants.SMS_MAX_TIME)
                                .map(aLong -> Constants.SMS_MAX_TIME - (aLong + 1))
                                .observeOn(AndroidSchedulers.mainThread())
                                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                                .subscribe(new Observer<Long>() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onNext(@NonNull Long aLong) {
                                        mRootView.countDown(aLong);
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        mRootView.countDown(Long.parseLong("0"));
                                    }
                                });
                    }
                });
    }

    /**
     * 验证短信验证码
     *
     * @param mobile  手机号码
     * @param captcha 短信验证码
     */
    public void checkCaptcha(String mobile, String captcha) {
        if (!TextUtils.equals(captcha, veryCode)) {
            mRootView.showMessage("请输入正确的验证码");
        } else {
            String token = DataHelper.getStringSF(mApplication, Constants.SP_TOKEN);
            mobileUpdate(token, mobile);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
