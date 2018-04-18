package com.geek.huixiaoer.mvp.blacklist.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.blacklist.contract.BlacklistActivityContract;
import com.geek.huixiaoer.mvp.blacklist.model.BlacklistActivityModel;


@Module
public class BlacklistActivityModule {
    private BlacklistActivityContract.View view;

    /**
     * 构建BlacklistActivityModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public BlacklistActivityModule(BlacklistActivityContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    BlacklistActivityContract.View provideBlacklistActivityView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    BlacklistActivityContract.Model provideBlacklistActivityModel(BlacklistActivityModel model) {
        return model;
    }
}