package com.geek.huixiaoer.mvp.supermarket.model;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.mvp.supermarket.contract.ShoppingCartContract;
import com.geek.huixiaoer.mvp.supermarket.ui.activity.CartEditResultBean;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.shop.CartBean;
import com.geek.huixiaoer.storage.entity.shop.GoodsBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

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
    public Observable<BaseResponse<CartBean>> cartList(String token) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).cartList(token);
    }

    @Override
    public Observable<BaseResponse<CartEditResultBean>> cartEdit(String token, String id, int quantity) {
        return  mRepositoryManager.obtainRetrofitService(BaseApi.class).cartEdit(token, id, quantity);
    }

    @Override
    public Observable<BaseResponse<CartEditResultBean>> cartDelete(String token, String id) {
        return  mRepositoryManager.obtainRetrofitService(BaseApi.class).cartDelete(token, id);
    }

    @Override
    public Observable<BaseResponse<CartEditResultBean>> cartClear(String token) {
        return  mRepositoryManager.obtainRetrofitService(BaseApi.class).cartClear(token);
    }
}