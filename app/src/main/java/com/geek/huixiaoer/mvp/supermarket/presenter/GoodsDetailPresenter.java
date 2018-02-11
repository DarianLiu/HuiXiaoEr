package com.geek.huixiaoer.mvp.supermarket.presenter;

import android.text.TextUtils;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.storage.entity.GoodsBean;
import com.geek.huixiaoer.storage.entity.SingleResultBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.supermarket.contract.GoodsDetailContract;
import com.jess.arms.utils.DataHelper;

@ActivityScope
public class GoodsDetailPresenter extends BasePresenter<GoodsDetailContract.Model, GoodsDetailContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private boolean isFavorite;

    @Inject
    GoodsDetailPresenter(GoodsDetailContract.Model model, GoodsDetailContract.View rootView
            , RxErrorHandler handler, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
    }

    /**
     * 查看商品是否收藏
     *
     * @param goods_sn 商品SN号
     */
    public void goodsHasFavorite(String goods_sn) {
        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
        mModel.goodsHasFavorite(token, goods_sn).retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<SingleResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull SingleResultBean result) {
                        isFavorite = TextUtils.equals(result.getResult(), "1");
                        mRootView.updateFavoriteState(isFavorite);
                    }
                });
    }

    /**
     * 收藏
     *
     * @param goods_sn 商品SN号
     */
    public void favorite(String goods_sn) {
        if (isFavorite) {
            favoriteDelete(goods_sn);
        } else {
            favoriteAdd(goods_sn);
        }
    }

    /**
     * 商品添加收藏
     *
     * @param goods_sn 商品SN号
     */
    private void favoriteAdd(String goods_sn) {
        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
        mModel.goodsFavoriteAdd(token, goods_sn).retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<SingleResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull SingleResultBean result) {
                        mRootView.updateFavoriteState(true);
                    }
                });
    }

    /**
     * 商品删除收藏
     *
     * @param goods_sn 商品SN号
     */
    private void favoriteDelete(String goods_sn) {
        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
        mModel.goodsFavoriteDelete(token, goods_sn).retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<SingleResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull SingleResultBean result) {
                        mRootView.updateFavoriteState(false);
                    }
                });
    }

    /**
     * 添加购物车
     *
     * @param goods_sn 商品SN号
     */
    public void addCart(String goods_sn) {
        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
        mModel.cartAdd(token, goods_sn).retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<GoodsBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull GoodsBean goodsBean) {
                        mRootView.showMessage("成功加入购物车");
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
