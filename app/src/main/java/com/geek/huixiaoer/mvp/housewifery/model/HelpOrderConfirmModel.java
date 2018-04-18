package com.geek.huixiaoer.mvp.housewifery.model;

import android.app.Application;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.housewifery.CreateServiceOrderBean;
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
    public Observable<BaseResponse<CreateServiceOrderBean>> createServiceOrder(String consignee, String address, String zipCode, String mobile, String goodsId, String amount, String memo) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).createServiceOrder(consignee,address,zipCode,mobile,goodsId,amount,memo);
    }
}