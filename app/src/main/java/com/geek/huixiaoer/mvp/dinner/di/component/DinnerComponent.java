package com.geek.huixiaoer.mvp.dinner.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.dinner.di.module.DinnerModule;

import com.geek.huixiaoer.mvp.dinner.ui.activity.DinnerActivity;

@ActivityScope
@Component(modules = DinnerModule.class, dependencies = AppComponent.class)
public interface DinnerComponent {
    void inject(DinnerActivity activity);
}