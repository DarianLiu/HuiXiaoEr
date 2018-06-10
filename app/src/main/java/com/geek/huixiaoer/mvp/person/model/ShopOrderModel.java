package com.geek.huixiaoer.mvp.person.model;

import android.app.Application;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.mvp.person.contract.ShopOrderContract;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.shop.OrderBean;
import com.geek.huixiaoer.storage.entity.shop.OrderCreateResultBean;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * 购物订单列表
 * Created by Administrator on 2018/3/28.
 */

public class ShopOrderModel extends BaseModel implements ShopOrderContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public ShopOrderModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public Observable<BaseResponse<BaseArrayData<OrderBean>>> shopOrderList(String token, int pageNumber, int pageSize, String status, String type) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).shopOrderList(token, pageNumber, pageSize, status, type);
    }

    @Override
    public Observable<BaseResponse<OrderBean>> shopOrderCancel(String token, String sn, String outTradeNo) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).shopOrderCancel(token, sn, outTradeNo);
    }

    @Override
    public Observable<BaseResponse<OrderBean>> shopOrderReceive(String token, String sn) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).shopOrderReceive(token, sn);
    }

    @Override
    public Observable<BaseResponse<OrderCreateResultBean>> paymentSubmitSn(String token, String paymentPluginId, String sn, String amount) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).paymentSubmitSn(token, paymentPluginId, sn, amount);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

}
