package com.geek.huixiaoer.mvp.person.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.person.contract.ShopOrderDetailContract;
import com.geek.huixiaoer.mvp.person.model.ShopOrderDetailModel;


@Module
public class ShopOrderDetailModule {
    private ShopOrderDetailContract.View view;

    /**
     * 构建ShopOrderDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ShopOrderDetailModule(ShopOrderDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ShopOrderDetailContract.View provideShopOrderDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ShopOrderDetailContract.Model provideShopOrderDetailModel(ShopOrderDetailModel model) {
        return model;
    }
}