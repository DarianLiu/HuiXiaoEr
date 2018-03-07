package com.geek.huixiaoer.mvp.housewifery.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.housewifery.contract.HomeServicesContract;
import com.geek.huixiaoer.mvp.housewifery.model.HomeServicesModel;


@Module
public class HomeServicesModule {
    private HomeServicesContract.View view;

    /**
     * 构建HomeServicesModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public HomeServicesModule(HomeServicesContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    HomeServicesContract.View provideHomeServicesView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    HomeServicesContract.Model provideHomeServicesModel(HomeServicesModel model) {
        return model;
    }
}