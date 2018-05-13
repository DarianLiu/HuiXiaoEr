package com.geek.huixiaoer.mvp.housewifery.model;

import android.app.Application;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.mvp.housewifery.contract.HomeServicesContract;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.BannerBean;
import com.geek.huixiaoer.storage.entity.housewifery.ServiceBean;
import com.geek.huixiaoer.storage.entity.shop.GoodsBean;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class HomeServicesModel extends BaseModel implements HomeServicesContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    HomeServicesModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<BaseArrayData<BannerBean>>> banner(int positonId) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).banner(positonId);
    }

    @Override
    public Observable<BaseResponse<BaseArrayData<GoodsBean>>> homeServiceList(int pageNumber, int pageSize, String startPrice, String endPrice, String orderType) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).serviceList(pageNumber, pageSize, startPrice, endPrice, orderType);
    }

    @Override
    public Observable<BaseResponse<ServiceBean>> findService(String token) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).findService(token);
    }

    @Override
    public Observable<BaseResponse<ServiceBean>> setServiceB(String ryToken) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).setServiceB(ryToken);
    }
}