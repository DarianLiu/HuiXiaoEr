package com.geek.huixiaoer.mvp.supermarket.presenter;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.entity.Category;
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

    public void getGoodsCategorys() {
        mModel.goodsCategoryRoot().retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<BaseArrayData<Category>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseArrayData<Category> categoryBaseArrayData) {
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
