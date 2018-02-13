package com.geek.huixiaoer.mvp.person.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.person.contract.MyOrderContract;
import com.geek.huixiaoer.mvp.person.model.MyOrderModel;


@Module
public class MyOrderModule {
    private MyOrderContract.View view;

    /**
     * 构建MyOrderModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MyOrderModule(MyOrderContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyOrderContract.View provideMyOrderView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyOrderContract.Model provideMyOrderModel(MyOrderModel model) {
        return model;
    }
}