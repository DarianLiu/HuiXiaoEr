package com.geek.huixiaoer.mvp.recycle.model;

import android.app.Application;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.common.utils.StringUtils;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.recycle.ArticleBean;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.recycle.contract.ForumPostContract;

import io.reactivex.Observable;


@ActivityScope
public class ForumPostModel extends BaseModel implements ForumPostContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ForumPostModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<ArticleBean>> forumPost(String token, String title, String category, String content) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).forumPost(token,StringUtils.stringUTF8(title), category, StringUtils.stringUTF8(content));
    }
}