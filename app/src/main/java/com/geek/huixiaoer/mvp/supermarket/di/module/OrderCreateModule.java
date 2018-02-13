package com.geek.huixiaoer.mvp.supermarket.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.supermarket.contract.OrderCreateContract;
import com.geek.huixiaoer.mvp.supermarket.model.OrderCreateModel;


@Module
public class OrderCreateModule {
    private OrderCreateContract.View view;

    /**
     * 构建OrderCreateModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public OrderCreateModule(OrderCreateContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    OrderCreateContract.View provideOrderCreateView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    OrderCreateContract.Model provideOrderCreateModel(OrderCreateModel model) {
        return model;
    }
}