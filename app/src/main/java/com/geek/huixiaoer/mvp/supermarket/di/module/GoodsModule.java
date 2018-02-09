package com.geek.huixiaoer.mvp.supermarket.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.supermarket.contract.GoodsContract;
import com.geek.huixiaoer.mvp.supermarket.model.GoodsModel;


@Module
public class GoodsModule {
    private GoodsContract.View view;

    /**
     * 构建GoodsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public GoodsModule(GoodsContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    GoodsContract.View provideGoodsView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    GoodsContract.Model provideGoodsModel(GoodsModel model) {
        return model;
    }
}