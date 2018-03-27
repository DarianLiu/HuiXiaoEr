package com.geek.huixiaoer.mvp.person.presenter;

import android.app.Application;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.common.utils.AndroidUtil;
import com.geek.huixiaoer.mvp.person.contract.ReceiverAddContract;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class ReceiverAddPresenter extends BasePresenter<ReceiverAddContract.Model, ReceiverAddContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;

    @Inject
    public ReceiverAddPresenter(ReceiverAddContract.Model model, ReceiverAddContract.View rootView) {
        super(model, rootView);
    }

    public void getCityData() {
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            String result = AndroidUtil.readAssert(mApplication,"area.txt");
            emitter.onNext(result);
            emitter.onComplete();
        }).compose(RxUtil.applySchedulers(mRootView))
                .subscribeWith(new ErrorHandleSubscriber<String>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull String result) {
                        mRootView.setCityWheel(result);
                    }
                });
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
