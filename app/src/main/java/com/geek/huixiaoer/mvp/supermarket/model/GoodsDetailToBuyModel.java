package com.geek.huixiaoer.mvp.supermarket.model;

import android.app.Application;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.housewifery.CreateServiceOrderBean;
import com.geek.huixiaoer.storage.entity.shop.SpecificationBean;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.supermarket.contract.GoodsDetailToBuyContract;

import io.reactivex.Observable;


@ActivityScope
public class GoodsDetailToBuyModel extends BaseModel implements GoodsDetailToBuyContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public GoodsDetailToBuyModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
    @Override
    public Observable<BaseResponse<CreateServiceOrderBean>> createShopOrder(String consignee, String address, String zipCode, String mobile, String goodsId, String amount, String memo) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).createShopOrder(consignee,address,zipCode,mobile,goodsId,amount,memo);
    }
    @Override
    public Observable<BaseResponse<BaseArrayData<SpecificationBean>>> goodsSpecification(String goods_sn) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).goodsSpecification(goods_sn);
    }

}