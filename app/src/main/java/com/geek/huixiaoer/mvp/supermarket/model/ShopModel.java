package com.geek.huixiaoer.mvp.supermarket.model;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.BannerBean;
import com.geek.huixiaoer.storage.entity.MessageBean;
import com.geek.huixiaoer.storage.entity.shop.CategoryBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.supermarket.contract.ShopContract;

import io.reactivex.Observable;


@ActivityScope
public class ShopModel extends BaseModel implements ShopContract.Model {

    @Inject
    ShopModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Observable<BaseResponse<BaseArrayData<BannerBean>>> banner(int positonId) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).banner(positonId);
    }

    @Override
    public Observable<BaseResponse<BaseArrayData<CategoryBean>>> goodsCategoryRoot() {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).goodsCategoryRoot();
    }

    @Override
    public Observable<BaseResponse<BaseArrayData<MessageBean>>> messageList(int pageNumber, int pageSize, int messageType) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).messageList(pageNumber, pageSize, messageType);
    }
}