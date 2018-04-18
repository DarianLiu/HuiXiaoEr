package com.geek.huixiaoer.mvp.blacklist.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.blacklist.di.module.BlacklistActivityModule;

import com.geek.huixiaoer.mvp.blacklist.ui.activity.BlacklistActivityActivity;

@ActivityScope
@Component(modules = BlacklistActivityModule.class, dependencies = AppComponent.class)
public interface BlacklistActivityComponent {
    void inject(BlacklistActivityActivity activity);
}