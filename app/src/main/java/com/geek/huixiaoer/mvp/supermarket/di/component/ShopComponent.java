package com.geek.huixiaoer.mvp.supermarket.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.supermarket.di.module.ShopModule;

import com.geek.huixiaoer.mvp.supermarket.ui.activity.ShopActivity;

@ActivityScope
@Component(modules = ShopModule.class, dependencies = AppComponent.class)
public interface ShopComponent {
    void inject(ShopActivity activity);
}