package com.geek.huixiaoer.mvp.common.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.common.di.module.CaptchaModule;

import com.geek.huixiaoer.mvp.common.ui.activity.CaptchaActivity;

@ActivityScope
@Component(modules = CaptchaModule.class, dependencies = AppComponent.class)
public interface CaptchaComponent {
    void inject(CaptchaActivity activity);
}