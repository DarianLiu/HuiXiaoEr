package com.geek.huixiaoer.mvp.supermarket.model;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.mvp.supermarket.contract.OrderCreateContract;
import com.geek.huixiaoer.mvp.supermarket.ui.activity.CartEditResultBean;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.shop.OrderCalculateResultBean;
import com.geek.huixiaoer.storage.entity.shop.OrderCheckResultBean;
import com.geek.huixiaoer.storage.entity.shop.OrderCreateResultBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class OrderCreateModel extends BaseModel implements OrderCreateContract.Model {

    @Inject
    OrderCreateModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Observable<BaseResponse<OrderCheckResultBean>> orderCheckout(String token) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).orderCheckout(token);
    }

    @Override
    public Observable<BaseResponse<OrderCalculateResultBean>> orderCalculate(
            String token, String receiverId, String paymentMethodId, String shippingMethodId,
            String code, String invoiceTitle, String useBalance, String memo) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).orderCalculate(
                token, receiverId, paymentMethodId, shippingMethodId, code, invoiceTitle,
                useBalance, memo);
    }

    @Override
    public Observable<BaseResponse<CartEditResultBean>> paymentSubmitNo(
            String token, String paymentPluginId, String outTradeNo, String amount) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).paymentSubmitNo(
                token, paymentPluginId, outTradeNo, amount);
    }

    @Override
    public Observable<BaseResponse<OrderCreateResultBean>> orderCreate(
            String token, String receiverId, String code, String invoiceTitle,
            String useBalance, String memo) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).orderCreate(
                token, receiverId, code, invoiceTitle, useBalance, memo);
    }
}