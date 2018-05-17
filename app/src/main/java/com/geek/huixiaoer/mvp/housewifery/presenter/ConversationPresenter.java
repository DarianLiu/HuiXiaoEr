package com.geek.huixiaoer.mvp.housewifery.presenter;

import android.app.Application;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.storage.entity.housewifery.ServiceBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.housewifery.contract.ConversationContract;


@ActivityScope
public class ConversationPresenter extends BasePresenter<ConversationContract.Model, ConversationContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ConversationPresenter(ConversationContract.Model model, ConversationContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 设置客服空闲状态
     */
    public void setServiceF(String ryId) {
        mModel.setServiceF(ryId)
//                .retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<ServiceBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull ServiceBean serviceBean) {

                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mRootView.killMyself();
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
