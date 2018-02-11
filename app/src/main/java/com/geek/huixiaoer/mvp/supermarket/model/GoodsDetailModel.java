package com.geek.huixiaoer.mvp.supermarket.model;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.GoodsBean;
import com.geek.huixiaoer.storage.entity.SingleResultBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.supermarket.contract.GoodsDetailContract;

import io.reactivex.Observable;


@ActivityScope
public class GoodsDetailModel extends BaseModel implements GoodsDetailContract.Model {

    @Inject
    GoodsDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
    @Override
    public Observable<BaseResponse<SingleResultBean>> goodsHasFavorite(String token, String goods_sn) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).goodsHasFavorite(token, goods_sn);
    }

    @Override
    public Observable<BaseResponse<SingleResultBean>> goodsFavoriteAdd(String token, String goods_sn) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).goodsFavoriteAdd(token, goods_sn);
    }

    @Override
    public Observable<BaseResponse<SingleResultBean>> goodsFavoriteDelete(String token, String goods_sn) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).goodsFavoriteDelete(token, goods_sn);
    }

    @Override
    public Observable<BaseResponse<GoodsBean>> cartAdd(String token, String goods_sn) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).cartAdd(token, goods_sn);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}