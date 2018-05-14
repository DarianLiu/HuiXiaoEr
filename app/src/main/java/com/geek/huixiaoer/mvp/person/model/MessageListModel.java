package com.geek.huixiaoer.mvp.person.model;

import android.app.Application;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.BannerBean;
import com.geek.huixiaoer.storage.entity.MessageBean;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.person.contract.MessageListContract;

import io.reactivex.Observable;


@ActivityScope
public class MessageListModel extends BaseModel implements MessageListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MessageListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<BaseArrayData<MessageBean>>> messageList(int pageNumber, int pageSize,int messageType) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).messageList(pageNumber, pageSize, messageType);
    }
}