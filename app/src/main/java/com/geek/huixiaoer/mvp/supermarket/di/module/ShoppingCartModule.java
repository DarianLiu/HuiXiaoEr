package com.geek.huixiaoer.mvp.supermarket.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.supermarket.contract.ShoppingCartContract;
import com.geek.huixiaoer.mvp.supermarket.model.ShoppingCartModel;


@Module
public class ShoppingCartModule {
    private ShoppingCartContract.View view;

    /**
     * 构建ShoppingCartModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ShoppingCartModule(ShoppingCartContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ShoppingCartContract.View provideShoppingCartView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ShoppingCartContract.Model provideShoppingCartModel(ShoppingCartModel model) {
        return model;
    }
}