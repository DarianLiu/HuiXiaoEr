package com.geek.huixiaoer.mvp.supermarket.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.supermarket.di.module.ShoppingCartModule;

import com.geek.huixiaoer.mvp.supermarket.ui.activity.ShoppingCartActivity;

@ActivityScope
@Component(modules = ShoppingCartModule.class, dependencies = AppComponent.class)
public interface ShoppingCartComponent {
    void inject(ShoppingCartActivity activity);
}