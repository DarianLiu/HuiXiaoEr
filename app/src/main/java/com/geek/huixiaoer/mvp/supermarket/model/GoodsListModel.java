package com.geek.huixiaoer.mvp.supermarket.model;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.shop.GoodsBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.supermarket.contract.GoodsListContract;

import io.reactivex.Observable;

@ActivityScope
public class GoodsListModel extends BaseModel implements GoodsListContract.Model {

    @Inject
    GoodsListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Observable<BaseResponse<BaseArrayData<GoodsBean>>> goodsList(int category_id, int pageNum) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).goodsList(category_id, pageNum);
    }
}