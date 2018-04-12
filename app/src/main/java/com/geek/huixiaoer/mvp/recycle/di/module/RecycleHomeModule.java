package com.geek.huixiaoer.mvp.recycle.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.recycle.contract.RecycleHomeContract;
import com.geek.huixiaoer.mvp.recycle.model.RecycleHomeModel;


@Module
public class RecycleHomeModule {
    private RecycleHomeContract.View view;

    /**
     * 构建RecycleHomeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public RecycleHomeModule(RecycleHomeContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RecycleHomeContract.View provideRecycleHomeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    RecycleHomeContract.Model provideRecycleHomeModel(RecycleHomeModel model) {
        return model;
    }
}