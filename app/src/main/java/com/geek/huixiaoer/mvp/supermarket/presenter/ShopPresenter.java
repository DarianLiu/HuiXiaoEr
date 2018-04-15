package com.geek.huixiaoer.mvp.supermarket.presenter;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.entity.BannerBean;
import com.geek.huixiaoer.storage.entity.shop.CategoryBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.supermarket.contract.ShopContract;


@ActivityScope
public class ShopPresenter extends BasePresenter<ShopContract.Model, ShopContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;

    @Inject
    ShopPresenter(ShopContract.Model model, ShopContract.View rootView, RxErrorHandler handler, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
    }

    /**
     * 获取轮播图
     */
    public void getBanner() {
        mModel.banner(8).retryWhen(new RetryWithDelay(2, 1))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<BaseArrayData<BannerBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseArrayData<BannerBean> bannerBeanBaseArrayData) {
                        mRootView.updateBanner(bannerBeanBaseArrayData.getPageData());
                    }
                });
    }

    public void getGoodsCategorys() {
        mModel.goodsCategoryRoot().retryWhen(new RetryWithDelay(0, 30))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<BaseArrayData<CategoryBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseArrayData<CategoryBean> categoryBaseArrayData) {
                        mRootView.setViewPager(categoryBaseArrayData.getPageData());
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
