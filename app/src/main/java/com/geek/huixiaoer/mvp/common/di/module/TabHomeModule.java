package com.geek.huixiaoer.mvp.common.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.common.contract.TabHomeContract;
import com.geek.huixiaoer.mvp.common.model.TabHomeModel;


@Module
public class TabHomeModule {
    private TabHomeContract.View view;

    /**
     * 构建TabHomeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public TabHomeModule(TabHomeContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    TabHomeContract.View provideTabHomeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    TabHomeContract.Model provideTabHomeModel(TabHomeModel model) {
        return model;
    }
}