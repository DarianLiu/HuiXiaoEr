package com.geek.huixiaoer.mvp.recycle.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.recycle.contract.RecycleAddContract;
import com.geek.huixiaoer.mvp.recycle.model.RecycleAddModel;


@Module
public class RecycleAddModule {
    private RecycleAddContract.View view;

    /**
     * 构建RecycleAddModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public RecycleAddModule(RecycleAddContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RecycleAddContract.View provideRecycleAddView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    RecycleAddContract.Model provideRecycleAddModel(RecycleAddModel model) {
        return model;
    }
}