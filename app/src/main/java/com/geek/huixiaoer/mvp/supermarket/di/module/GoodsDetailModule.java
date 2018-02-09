package com.geek.huixiaoer.mvp.supermarket.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.supermarket.contract.GoodsDetailContract;
import com.geek.huixiaoer.mvp.supermarket.model.GoodsDetailModel;


@Module
public class GoodsDetailModule {
    private GoodsDetailContract.View view;

    /**
     * 构建GoodsDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public GoodsDetailModule(GoodsDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    GoodsDetailContract.View provideGoodsDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    GoodsDetailContract.Model provideGoodsDetailModel(GoodsDetailModel model) {
        return model;
    }
}