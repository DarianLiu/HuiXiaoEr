package com.geek.huixiaoer.mvp.blacklist.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.blacklist.contract.blacklistContract;
import com.geek.huixiaoer.mvp.blacklist.model.blacklistModel;


@Module
public class blacklistModule {
    private blacklistContract.View view;

    /**
     * 构建blacklistModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public blacklistModule(blacklistContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    blacklistContract.View provideblacklistView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    blacklistContract.Model provideblacklistModel(blacklistModel model) {
        return model;
    }
}