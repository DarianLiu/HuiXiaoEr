package com.geek.huixiaoer.mvp.person.model;

import android.app.Application;

import com.geek.huixiaoer.mvp.person.contract.MyShopOrderContract;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;


@ActivityScope
public class MyShopOrderModel extends BaseModel implements MyShopOrderContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public MyShopOrderModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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

}