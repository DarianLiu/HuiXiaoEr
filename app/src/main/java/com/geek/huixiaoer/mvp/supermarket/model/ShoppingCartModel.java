package com.geek.huixiaoer.mvp.supermarket.model;

import android.app.Application;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.shop.OrderBean;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.supermarket.contract.ShoppingCartContract;

import io.reactivex.Observable;


@ActivityScope
public class ShoppingCartModel extends BaseModel implements ShoppingCartContract.Model {

    @Inject
    ShoppingCartModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Observable<BaseResponse<BaseArrayData<OrderBean>>> cartList(String token) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).cartList(token);
    }
}