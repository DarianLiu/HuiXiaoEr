package com.geek.huixiaoer.mvp.person.di.module;

import com.geek.huixiaoer.mvp.person.contract.MyShopOrderContract;
import com.geek.huixiaoer.mvp.person.model.MyShopOrderModel;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class MyShopOrderModule {
    private MyShopOrderContract.View view;

    /**
     * 构建MyOrderModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MyShopOrderModule(MyShopOrderContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyShopOrderContract.View provideMyOrderView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyShopOrderContract.Model provideMyOrderModel(MyShopOrderModel model) {
        return model;
    }
}