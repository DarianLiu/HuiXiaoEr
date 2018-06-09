package com.geek.huixiaoer.mvp.common.model;

import android.app.Application;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.SingleResultBean;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.common.contract.ForgetPasswordContract;

import io.reactivex.Observable;


@ActivityScope
public class ForgetPasswordModel extends BaseModel implements ForgetPasswordContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public ForgetPasswordModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<SingleResultBean>> resetPassword(String mobile, String enPassword) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).resetPassword(mobile, enPassword);
    }
}