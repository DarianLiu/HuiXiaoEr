package com.geek.huixiaoer.mvp.recycle.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.recycle.di.module.BlackListModule;

import com.geek.huixiaoer.mvp.recycle.ui.activity.BlackListActivity;

@ActivityScope
@Component(modules = BlackListModule.class, dependencies = AppComponent.class)
public interface BlackListComponent {
    void inject(BlackListActivity activity);
}