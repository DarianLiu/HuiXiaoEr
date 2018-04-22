package com.geek.huixiaoer.mvp.supermarket.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.supermarket.di.module.GoodsDetailToBuyModule;

import com.geek.huixiaoer.mvp.supermarket.ui.activity.GoodsDetailToBuyActivity;

@ActivityScope
@Component(modules = GoodsDetailToBuyModule.class, dependencies = AppComponent.class)
public interface GoodsDetailToBuyComponent {
    void inject(GoodsDetailToBuyActivity activity);
}