package com.geek.huixiaoer.mvp.common.model;

import android.app.Application;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.BannerBean;
import com.geek.huixiaoer.storage.entity.recycle.ArticleBean;
import com.geek.huixiaoer.storage.entity.shop.GoodsBean;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.common.contract.TabHomeContract;

import io.reactivex.Observable;


@ActivityScope
public class TabHomeModel extends BaseModel implements TabHomeContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public TabHomeModel(IRepositoryManager repositoryManager) {
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
    public Observable<BaseResponse<BaseArrayData<ArticleBean>>> hotspotList(int pageNumber, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).articleList(pageNumber, pageSize, null, "mood");
    }

    @Override
    public Observable<BaseResponse<BaseArrayData<GoodsBean>>> goodsExplosion(int pageNumber, int pageSize, int tagId) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).goodsExplosion(pageNumber, pageSize, tagId);
    }
}