package com.geek.huixiaoer.mvp.supermarket.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.supermarket.di.module.OrderCreateModule;

import com.geek.huixiaoer.mvp.supermarket.ui.activity.OrderCreateActivity;

@ActivityScope
@Component(modules = OrderCreateModule.class, dependencies = AppComponent.class)
public interface OrderCreateComponent {
    void inject(OrderCreateActivity activity);
}