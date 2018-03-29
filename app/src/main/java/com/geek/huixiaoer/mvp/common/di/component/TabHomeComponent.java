package com.geek.huixiaoer.mvp.common.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.common.di.module.TabHomeModule;

import com.geek.huixiaoer.mvp.common.ui.fragment.TabHomeFragment;

@ActivityScope
@Component(modules = TabHomeModule.class, dependencies = AppComponent.class)
public interface TabHomeComponent {
    void inject(TabHomeFragment fragment);
}