package com.geek.huixiaoer.mvp.common.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.common.contract.CaptchaContract;
import com.geek.huixiaoer.mvp.common.model.CaptchaModel;


@Module
public class CaptchaModule {
    private CaptchaContract.View view;

    /**
     * 构建CaptchaModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CaptchaModule(CaptchaContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CaptchaContract.View provideCaptchaView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CaptchaContract.Model provideCaptchaModel(CaptchaModel model) {
        return model;
    }
}