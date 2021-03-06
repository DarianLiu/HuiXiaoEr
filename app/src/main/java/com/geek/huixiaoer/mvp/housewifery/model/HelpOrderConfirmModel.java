package com.geek.huixiaoer.mvp.housewifery.model;

import android.app.Application;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.housewifery.CreateServiceOrderBean;
import com.geek.huixiaoer.storage.entity.shop.OrderCreateResultBean;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.housewifery.contract.HelpOrderConfirmContract;

import io.reactivex.Observable;


@ActivityScope
public class HelpOrderConfirmModel extends BaseModel implements HelpOrderConfirmContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public HelpOrderConfirmModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<CreateServiceOrderBean>> createServiceOrder(String token,String consignee, String address, String zipCode, String mobile, String productId, String amount, String memo,String id) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).createServiceOrder(token,consignee,address,zipCode,mobile,productId,amount,memo,id);
    }
    @Override
    public Observable<BaseResponse<OrderCreateResultBean>> paymentSubmitNo(
            String token, String paymentPluginId, String outTradeNo, String amount) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).paymentSubmitNo(
                token, paymentPluginId, outTradeNo, amount);
    }
}