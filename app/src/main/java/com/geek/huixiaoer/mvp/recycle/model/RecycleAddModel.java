package com.geek.huixiaoer.mvp.recycle.model;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.mvp.recycle.contract.RecycleAddContract;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.recycle.ArticleBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class RecycleAddModel extends BaseModel implements RecycleAddContract.Model {

    @Inject
    RecycleAddModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Observable<BaseResponse<ArticleBean>> recycleAdd(String token,String category,String content) {
        return  mRepositoryManager.obtainRetrofitService(BaseApi.class).articleAdd(token, category, content);
    }
}