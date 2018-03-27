package com.geek.huixiaoer.mvp.person.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.person.contract.ReceiverAddContract;
import com.geek.huixiaoer.mvp.person.model.ReceiverAddModel;


@Module
public class ReceiverAddModule {
    private ReceiverAddContract.View view;

    /**
     * 构建ReceiverAddModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ReceiverAddModule(ReceiverAddContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ReceiverAddContract.View provideReceiverAddView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ReceiverAddContract.Model provideReceiverAddModel(ReceiverAddModel model) {
        return model;
    }
}