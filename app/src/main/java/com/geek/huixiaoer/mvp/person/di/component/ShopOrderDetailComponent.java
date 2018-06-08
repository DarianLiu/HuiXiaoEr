package com.geek.huixiaoer.mvp.person.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.person.di.module.ShopOrderDetailModule;

import com.geek.huixiaoer.mvp.person.ui.activity.ShopOrderDetailActivity;

@ActivityScope
@Component(modules = ShopOrderDetailModule.class, dependencies = AppComponent.class)
public interface ShopOrderDetailComponent {
    void inject(ShopOrderDetailActivity activity);
}