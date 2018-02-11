package com.geek.huixiaoer.mvp.common.presenter;

import android.content.Intent;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.mvp.common.ui.activity.RegisterActivity;
import com.geek.huixiaoer.storage.entity.SingleResultBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

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

import com.geek.huixiaoer.mvp.common.contract.CaptchaContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.concurrent.TimeUnit;


@ActivityScope
public class CaptchaPresenter extends BasePresenter<CaptchaContract.Model, CaptchaContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;

    @Inject
    CaptchaPresenter(CaptchaContract.Model model, CaptchaContract.View rootView
            , RxErrorHandler handler, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
    }

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
        mModel.checkCode(captcha).retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<SingleResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull SingleResultBean singleResultBean) {
                        Intent intent = new Intent(mAppManager.getTopActivity(), RegisterActivity.class);
                        intent.putExtra(Constants.INTENT_MOBILE, mobile);
                        mRootView.launchActivity(intent);
                    }

                    @Override
                    public void onError(@NonNull Throwable t) {
                        super.onError(t);
                        Intent intent = new Intent(mAppManager.getTopActivity(), RegisterActivity.class);
                        intent.putExtra(Constants.INTENT_MOBILE, mobile);
                        mRootView.launchActivity(intent);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

}
