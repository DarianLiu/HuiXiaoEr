package com.geek.huixiaoer.mvp.recycle.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.recycle.di.module.RecycleDetailModule;

import com.geek.huixiaoer.mvp.recycle.ui.activity.RecycleDetailActivity;

@ActivityScope
@Component(modules = RecycleDetailModule.class, dependencies = AppComponent.class)
public interface RecycleDetailComponent {
    void inject(RecycleDetailActivity activity);
}