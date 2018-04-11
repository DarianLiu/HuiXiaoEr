package com.geek.huixiaoer.mvp.dinner.model;

import android.app.Application;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.shop.GoodsBean;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.dinner.contract.DinnerContract;

import io.reactivex.Observable;


@ActivityScope
public class DinnerModel extends BaseModel implements DinnerContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public DinnerModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse<BaseArrayData<GoodsBean>>> dishList(int pageNumber, int pageSize, String startPrice, String endPrice, String orderType) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).dishList(pageNumber, pageSize, startPrice, endPrice, orderType);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


}