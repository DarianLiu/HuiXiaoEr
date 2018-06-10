package com.geek.huixiaoer.mvp.person.presenter;

import android.app.Application;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.mvp.person.contract.ShopOrderContract;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.entity.shop.OrderBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import timber.log.Timber;


@ActivityScope
public class ShopOrderPresenter extends BasePresenter<ShopOrderContract.Model, ShopOrderContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ShopOrderPresenter(ShopOrderContract.Model model, ShopOrderContract.View rootView) {
        super(model, rootView);
    }

    private int page_no;//当前页数

    private String getToken() {
        return DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
    }

    /**
     * 订单列表
     *
     * @param isRefresh 是否刷新
     * @param status    订单状态
     */
    public void orderList(boolean isRefresh, String status) {
        if (isRefresh)
            page_no = 0;
        Timber.d("====token" + getToken());
        mModel.shopOrderList(getToken(), page_no + 1, 10, status, "general").retryWhen(new RetryWithDelay(3, 2))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (isRefresh) {
                        mRootView.endRefresh();//隐藏下拉刷新的进度条
                    } else {
                        mRootView.endLoadMore();//隐藏加载更多的进度条
                    }
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<BaseArrayData<OrderBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseArrayData<OrderBean> arrayData) {
                        if (arrayData.getPageData() != null && arrayData.getPageData().size() != 0) {
                            page_no = arrayData.getPageNumber();
                            mRootView.updateView(isRefresh, arrayData.getPageData());
                        }
                    }
                });
    }

    /**
     * 取消订单
     *
     * @param order_sn 订单SN号
     */
    public void orderCancel(String order_sn, String outTradeNo) {
        mModel.shopOrderCancel(getToken(), order_sn, outTradeNo).retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mApplication))
                .subscribeWith(new ErrorHandleSubscriber<OrderBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull OrderBean orderBean) {

                    }
                });
    }

    /**
     * 取消订单
     *
     * @param order_sn 订单SN号
     */
    public void shopOrderReceive(String order_sn) {
        mModel.shopOrderReceive(getToken(), order_sn).retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mApplication))
                .subscribeWith(new ErrorHandleSubscriber<OrderBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull OrderBean orderBean) {

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
