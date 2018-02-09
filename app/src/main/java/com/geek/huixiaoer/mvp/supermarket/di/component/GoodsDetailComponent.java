package com.geek.huixiaoer.mvp.supermarket.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.supermarket.di.module.GoodsDetailModule;

import com.geek.huixiaoer.mvp.supermarket.ui.activity.GoodsDetailActivity;

@ActivityScope
@Component(modules = GoodsDetailModule.class, dependencies = AppComponent.class)
public interface GoodsDetailComponent {
    void inject(GoodsDetailActivity activity);
}