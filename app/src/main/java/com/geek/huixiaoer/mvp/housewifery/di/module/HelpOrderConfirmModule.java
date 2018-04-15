package com.geek.huixiaoer.mvp.housewifery.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.housewifery.contract.HelpOrderConfirmContract;
import com.geek.huixiaoer.mvp.housewifery.model.HelpOrderConfirmModel;


@Module
public class HelpOrderConfirmModule {
    private HelpOrderConfirmContract.View view;

    /**
     * 构建HelpOrderConfirmModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public HelpOrderConfirmModule(HelpOrderConfirmContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    HelpOrderConfirmContract.View provideHelpOrderConfirmView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    HelpOrderConfirmContract.Model provideHelpOrderConfirmModel(HelpOrderConfirmModel model) {
        return model;
    }
}