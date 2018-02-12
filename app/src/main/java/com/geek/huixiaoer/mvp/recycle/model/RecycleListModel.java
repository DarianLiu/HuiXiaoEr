package com.geek.huixiaoer.mvp.recycle.model;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.mvp.recycle.contract.RecycleListContract;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.article.ArticleBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class RecycleListModel extends BaseModel implements RecycleListContract.Model {

    @Inject
    RecycleListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse<BaseArrayData<ArticleBean>>> articleList(int pageNumber, int pageSize,
                                                                           String type, String category) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).articleList(pageNumber,
                pageSize, type, category);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}