package com.geek.huixiaoer.mvp.recycle.presenter;

import android.app.Application;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.entity.BannerBean;
import com.geek.huixiaoer.storage.entity.MessageBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.recycle.contract.RecycleHomeContract;


@ActivityScope
public class RecycleHomePresenter extends BasePresenter<RecycleHomeContract.Model, RecycleHomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public RecycleHomePresenter(RecycleHomeContract.Model model, RecycleHomeContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取轮播图
     */
    public void getBanner() {
        mModel.banner(10).retryWhen(new RetryWithDelay(2, 1))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mApplication))
                .subscribeWith(new ErrorHandleSubscriber<BaseArrayData<BannerBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseArrayData<BannerBean> bannerBeanBaseArrayData) {
                        mRootView.updateBanner(bannerBeanBaseArrayData.getPageData());
                    }
                });
    }

    /**
     * 消息列表
     *
     * @param pageSize 每页数量
     */
    public void messageList(int pageSize) {
//        String token = DataHelper.getStringSF(mApplication, Constants.SP_TOKEN);
        mModel.messageList(1, 10, 4)
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<BaseArrayData<MessageBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseArrayData<MessageBean> bannerBeanBaseArrayData) {
                        mRootView.setMessageList(bannerBeanBaseArrayData);
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
