package com.geek.huixiaoer.mvp.person.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.person.contract.UpdateMobileContract;
import com.geek.huixiaoer.mvp.person.model.UpdateMobileModel;


@Module
public class UpdateMobileModule {
    private UpdateMobileContract.View view;

    /**
     * 构建UpdateMobileModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public UpdateMobileModule(UpdateMobileContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UpdateMobileContract.View provideUpdateMobileView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    UpdateMobileContract.Model provideUpdateMobileModel(UpdateMobileModel model) {
        return model;
    }
}