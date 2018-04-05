package com.geek.huixiaoer.mvp.common.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.common.di.module.TabMessageModule;

import com.geek.huixiaoer.mvp.common.ui.fragment.TabMessageFragment;

@ActivityScope
@Component(modules = TabMessageModule.class, dependencies = AppComponent.class)
public interface TabMessageComponent {
    void inject(TabMessageFragment fragment);
}