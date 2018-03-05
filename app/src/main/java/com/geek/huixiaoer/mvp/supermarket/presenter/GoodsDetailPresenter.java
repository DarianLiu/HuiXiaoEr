package com.geek.huixiaoer.mvp.supermarket.presenter;

import android.text.TextUtils;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.entity.shop.GoodsBean;
import com.geek.huixiaoer.storage.entity.SingleResultBean;
import com.geek.huixiaoer.storage.entity.shop.SpecificationBean;
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
     * 商品规格检索
     *
     * @param goods_sn 商品SN号
     */
    public void goodsSpecification(String goods_sn) {
        mModel.goodsSpecification(goods_sn).retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .safeSubscribe(new ErrorHandleSubscriber<BaseArrayData<SpecificationBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseArrayData<SpecificationBean> arrayData) {
                        mRootView.updateView(arrayData.getPageData());
                    }
                });
    }

    /**
     * 添加购物车
     *
     * @param quantity 商品数量
     * @param productId 产品ID
     */
    public void addCart(String productId, int quantity) {
        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
        mModel.cartAdd(token, productId, quantity).retryWhen(new RetryWithDelay(3, 2))
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
