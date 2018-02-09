package com.geek.huixiaoer.mvp.supermarket.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.supermarket.contract.ShopContract;
import com.geek.huixiaoer.mvp.supermarket.model.ShopModel;


@Module
public class ShopModule {
    private ShopContract.View view;

    /**
     * 构建ShopModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ShopModule(ShopContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ShopContract.View provideShopView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ShopContract.Model provideShopModel(ShopModel model) {
        return model;
    }
}