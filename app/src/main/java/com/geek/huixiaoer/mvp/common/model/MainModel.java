package com.geek.huixiaoer.mvp.common.model;

import android.app.Application;

import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.Banner;
import com.geek.huixiaoer.api.BaseApi;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.common.contract.MainContract;

import io.reactivex.Observable;


@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {

    @Inject
    MainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Observable<BaseResponse<BaseArrayData<Banner>>> articleBanner() {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).articleBanner();
    }
}