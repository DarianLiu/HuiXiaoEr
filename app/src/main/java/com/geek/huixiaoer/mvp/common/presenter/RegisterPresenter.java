package com.geek.huixiaoer.mvp.common.presenter;

import android.app.Application;
import android.content.Intent;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.common.utils.AndroidUtil;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.mvp.common.contract.RegisterContract;
import com.geek.huixiaoer.mvp.common.ui.activity.LoginActivity;
import com.geek.huixiaoer.storage.entity.SingleResultBean;
import com.geek.huixiaoer.storage.entity.UserBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class RegisterPresenter extends BasePresenter<RegisterContract.Model, RegisterContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    RegisterPresenter(RegisterContract.Model model, RegisterContract.View rootView
            , RxErrorHandler handler, AppManager appManager, Application application) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
        this.mApplication = application;
    }

    public String veryCode = "";
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
     * 获取市行政区数据
     */
    public void getRegionData() {
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            String result = AndroidUtil.readAssert(mApplication, "street.txt");
            emitter.onNext(result);
            emitter.onComplete();
        }).subscribeOn(Schedulers.newThread())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示进度条
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(mRootView::hideLoading)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribeWith(new ErrorHandleSubscriber<String>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull String result) {
                        mRootView.setRegionWheel(result);
                    }
                });
    }

    /**
     * 注册资料提交
     *
     * @param nickname    昵称
     * @param card        身份证号码
     * @param cityCode    镇（街道）Code
     * @param areaCode    村（社区）code
     * @param address     详细地址（小区、楼号、门牌号）
     * @param mobile      手机号码
     * @param enPassword  MD5加密密码
     * @param dynamicCode 短信验证码
     * @param volunteer   是否志愿者
     */
    public void registerSubmit(String nickname, String card, String cityCode, String areaCode, String communityCode,
                               String address, String mobile, String enPassword, String dynamicCode,
                               int volunteer) {
        mModel.register(nickname, card, cityCode, areaCode, communityCode, address, mobile, enPassword, dynamicCode, volunteer)
                .retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResultShowMessage(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<UserBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull UserBean userBean) {
                        mRootView.launchActivity(new Intent(mAppManager.getTopActivity(), LoginActivity.class));
                        mRootView.killMyself();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }

}
