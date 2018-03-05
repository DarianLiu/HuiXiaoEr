//package com.geek.huixiaoer.mvp.supermarket.presenter;
//
//import android.app.Application;
//
//import com.geek.huixiaoer.api.utils.RxUtil;
//import com.geek.huixiaoer.common.utils.Constants;
//import com.geek.huixiaoer.storage.BaseArrayData;
//import com.geek.huixiaoer.storage.entity.shop.GoodsBean;
//import com.geek.huixiaoer.storage.entity.shop.SpecificationBean;
//import com.jess.arms.integration.AppManager;
//import com.jess.arms.di.scope.ActivityScope;
//import com.jess.arms.mvp.BasePresenter;
//import com.jess.arms.http.imageloader.ImageLoader;
//
//import io.reactivex.annotations.NonNull;
//import me.jessyan.rxerrorhandler.core.RxErrorHandler;
//import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
//import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
//
//import javax.inject.Inject;
//
//import com.geek.huixiaoer.mvp.supermarket.contract.ProductSelectContract;
//import com.jess.arms.utils.DataHelper;
//
//
//@ActivityScope
//public class ProductSelectPresenter extends BasePresenter<ProductSelectContract.Model, ProductSelectContract.View> {
//    @Inject
//    RxErrorHandler mErrorHandler;
//    @Inject
//    Application mApplication;
//    @Inject
//    ImageLoader mImageLoader;
//    @Inject
//    AppManager mAppManager;
//
//    @Inject
//    ProductSelectPresenter(ProductSelectContract.Model model, ProductSelectContract.View rootView) {
//        super(model, rootView);
//    }
//
//    /**
//     * 商品规格检索
//     *
//     * @param goods_sn 商品SN号
//     */
//    public void goodsSpecification(String goods_sn) {
//        mModel.goodsSpecification(goods_sn).retryWhen(new RetryWithDelay(3, 2))
//                .compose(RxUtil.applySchedulers(mRootView))
//                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
//                .safeSubscribe(new ErrorHandleSubscriber<BaseArrayData<SpecificationBean>>(mErrorHandler) {
//                    @Override
//                    public void onNext(@NonNull BaseArrayData<SpecificationBean> arrayData) {
//                        mRootView.updateView(arrayData.getPageData());
//                    }
//                });
//    }
//
//    /**
//     * 添加购物车
//     *
//     * @param quantity 商品数量
//     * @param productId 产品ID
//     */
//    public void addCart(String productId, int quantity) {
//        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
//        mModel.cartAdd(token, productId, quantity).retryWhen(new RetryWithDelay(3, 2))
//                .compose(RxUtil.applySchedulers(mRootView))
//                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
//                .subscribeWith(new ErrorHandleSubscriber<GoodsBean>(mErrorHandler) {
//                    @Override
//                    public void onNext(@NonNull GoodsBean goodsBean) {
//                        mRootView.showMessage("成功加入购物车");
//                    }
//                });
//    }
//
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        this.mErrorHandler = null;
//        this.mAppManager = null;
//        this.mImageLoader = null;
//        this.mApplication = null;
//    }
//}
