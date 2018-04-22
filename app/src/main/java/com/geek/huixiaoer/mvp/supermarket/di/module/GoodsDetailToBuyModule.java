package com.geek.huixiaoer.mvp.supermarket.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.supermarket.contract.GoodsDetailToBuyContract;
import com.geek.huixiaoer.mvp.supermarket.model.GoodsDetailToBuyModel;


@Module
public class GoodsDetailToBuyModule {
    private GoodsDetailToBuyContract.View view;

    /**
     * 构建GoodsDetailToBuyModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public GoodsDetailToBuyModule(GoodsDetailToBuyContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    GoodsDetailToBuyContract.View provideGoodsDetailToBuyView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    GoodsDetailToBuyContract.Model provideGoodsDetailToBuyModel(GoodsDetailToBuyModel model) {
        return model;
    }
}