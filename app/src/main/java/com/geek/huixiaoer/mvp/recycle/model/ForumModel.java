package com.geek.huixiaoer.mvp.recycle.model;

import android.app.Application;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.recycle.ArticleBean;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.recycle.contract.ForumContract;

import io.reactivex.Observable;


@ActivityScope
public class ForumModel extends BaseModel implements ForumContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ForumModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<BaseArrayData<ArticleBean>>> articleList(int pageNumber, int pageSize, String type, String category) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).articleList(pageNumber,
                pageSize, type, category);
    }
}