package com.geek.huixiaoer.mvp.person.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.person.di.module.TabOrderModule;

import com.geek.huixiaoer.mvp.person.ui.fragment.TabOrderFragment;

@ActivityScope
@Component(modules = TabOrderModule.class, dependencies = AppComponent.class)
public interface TabOrderComponent {
    void inject(TabOrderFragment fragment);
}