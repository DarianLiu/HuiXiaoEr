package com.geek.huixiaoer.mvp.blacklist.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.blacklist.di.module.blacklistModule;

import com.geek.huixiaoer.mvp.blacklist.ui.activity.blacklistActivity;

@ActivityScope
@Component(modules = blacklistModule.class, dependencies = AppComponent.class)
public interface blacklistComponent {
    void inject(blacklistActivity activity);
}