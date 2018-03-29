package com.geek.huixiaoer.mvp.person.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.person.contract.TabMineContract;
import com.geek.huixiaoer.mvp.person.model.TabMineModel;


@Module
public class TabMineModule {
    private TabMineContract.View view;

    /**
     * 构建TabMineModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public TabMineModule(TabMineContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    TabMineContract.View provideTabMineView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    TabMineContract.Model provideTabMineModel(TabMineModel model) {
        return model;
    }
}