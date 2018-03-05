package com.geek.huixiaoer.mvp.supermarket.presenter;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.mvp.supermarket.contract.ShoppingCartContract;
import com.geek.huixiaoer.mvp.supermarket.ui.activity.CartEditResultBean;
import com.geek.huixiaoer.storage.entity.shop.CartBean;
import com.jess.arms.di.scope.ActivityScope;
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
public class ShoppingCartPresenter extends BasePresenter<ShoppingCartContract.Model, ShoppingCartContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;

    @Inject
    ShoppingCartPresenter(ShoppingCartContract.Model model, ShoppingCartContract.View rootView
            , RxErrorHandler handler, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
    }

    /**
     * 购物车列表
     *
     * @param isRefresh 是否刷新
     */
    public void cartList(boolean isRefresh) {
        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
        Timber.d("=========token" + token);

        mModel.cartList(token).retryWhen(new RetryWithDelay(3, 2))
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
                .subscribeWith(new ErrorHandleSubscriber<CartBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull CartBean cartBean) {
                        mRootView.updateView(cartBean);
                    }
                });
    }

    /**
     * 编辑购物车项
     *
     * @param cartId   购物车项ID
     * @param quantity 数量
     */
    public void cartEdit(int groupPosition, int childPosition, String cartId, int quantity) {
        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
        mModel.cartEdit(token, cartId, quantity).retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResultShowMessage(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<CartEditResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull CartEditResultBean resultBean) {
                        mRootView.updateCartItem(groupPosition, childPosition, quantity, resultBean.getEffectivePrice());
                    }
                });
    }

    /**
     * 删除购物车项
     *
     * @param cartId 购物车项ID
     */
    public void cartDelete(int groupPosition, int childPosition, String cartId) {
        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
        mModel.cartDelete(token, cartId).retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResultShowMessage(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<CartEditResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull CartEditResultBean resultBean) {
                        mRootView.removeCartItem(groupPosition, childPosition, resultBean.getEffectivePrice());
                    }
                });
    }

    /**
     * 清空购物车
     */
    public void cartClear() {
        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
        mModel.cartClear(token).retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResultShowMessage(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<CartEditResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull CartEditResultBean resultBean) {
                        mRootView.clearCart();
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
