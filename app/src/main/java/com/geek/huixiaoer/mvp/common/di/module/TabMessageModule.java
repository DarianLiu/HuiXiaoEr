package com.geek.huixiaoer.mvp.common.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.common.contract.TabMessageContract;
import com.geek.huixiaoer.mvp.common.model.TabMessageModel;


@Module
public class TabMessageModule {
    private TabMessageContract.View view;

    /**
     * 构建TabMessageModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public TabMessageModule(TabMessageContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    TabMessageContract.View provideTabMessageView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    TabMessageContract.Model provideTabMessageModel(TabMessageModel model) {
        return model;
    }
}