package com.geek.huixiaoer.mvp.recycle.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.recycle.di.module.RecycleAddModule;

import com.geek.huixiaoer.mvp.recycle.ui.activity.RecycleAddActivity;

@ActivityScope
@Component(modules = RecycleAddModule.class, dependencies = AppComponent.class)
public interface RecycleAddComponent {
    void inject(RecycleAddActivity activity);
}