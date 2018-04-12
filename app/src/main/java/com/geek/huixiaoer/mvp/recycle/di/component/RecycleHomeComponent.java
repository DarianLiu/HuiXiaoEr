package com.geek.huixiaoer.mvp.recycle.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.recycle.di.module.RecycleHomeModule;

import com.geek.huixiaoer.mvp.recycle.ui.activity.RecycleHomeActivity;

@ActivityScope
@Component(modules = RecycleHomeModule.class, dependencies = AppComponent.class)
public interface RecycleHomeComponent {
    void inject(RecycleHomeActivity activity);
}