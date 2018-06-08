package com.geek.huixiaoer.mvp.person.model;

import android.app.Application;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.mvp.person.contract.ShopOrderDetailContract;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.shop.OrderBean;
import com.geek.huixiaoer.storage.entity.shop.OrderCreateResultBean;
import com.geek.huixiaoer.storage.entity.shop.OrderDetailBean;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class ShopOrderDetailModel extends BaseModel implements ShopOrderDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ShopOrderDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<OrderDetailBean>> orderDetail(String token, String sn) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).orderDetail(token, sn);
    }

    @Override
    public Observable<BaseResponse<OrderBean>> shopOrderCancel(String token, String sn) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).shopOrderCancel(token, sn);
    }

    @Override
    public Observable<BaseResponse<OrderCreateResultBean>> paymentSubmitSn(String token, String paymentPluginId, String sn, String amount) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).paymentSubmitSn(token, paymentPluginId, sn, amount);
    }
}