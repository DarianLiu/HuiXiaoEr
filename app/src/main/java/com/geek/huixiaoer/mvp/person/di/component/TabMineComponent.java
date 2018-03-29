package com.geek.huixiaoer.mvp.person.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.person.di.module.TabMineModule;

import com.geek.huixiaoer.mvp.person.ui.fragment.TabMineFragment;

@ActivityScope
@Component(modules = TabMineModule.class, dependencies = AppComponent.class)
public interface TabMineComponent {
    void inject(TabMineFragment fragment);
}