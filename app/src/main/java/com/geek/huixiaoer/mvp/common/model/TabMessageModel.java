package com.geek.huixiaoer.mvp.common.model;

import android.app.Application;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.MessageBean;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.common.contract.TabMessageContract;

import io.reactivex.Observable;


@ActivityScope
public class TabMessageModel extends BaseModel implements TabMessageContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public TabMessageModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<BaseArrayData<MessageBean>>> messageList(int pageNumber, int pageSize, int messageType) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).messageList(pageNumber, pageSize, messageType);
    }
}