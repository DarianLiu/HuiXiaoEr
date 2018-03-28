package com.geek.huixiaoer.mvp.person.di.module;

import com.geek.huixiaoer.mvp.person.contract.ShopOrderContract;
import com.geek.huixiaoer.mvp.person.model.ShopOrderModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class ShopOrderModule {
    private ShopOrderContract.View view;

    /**
     * 构建ShopOrderModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ShopOrderModule(ShopOrderContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ShopOrderContract.View provideShopOrderView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ShopOrderContract.Model provideShopOrderModel(ShopOrderModel model) {
        return model;
    }
}