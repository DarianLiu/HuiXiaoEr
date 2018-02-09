package com.geek.huixiaoer.mvp.supermarket.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.supermarket.di.module.GoodsModule;

import com.geek.huixiaoer.mvp.supermarket.ui.fragment.GoodsFragment;

@ActivityScope
@Component(modules = GoodsModule.class, dependencies = AppComponent.class)
public interface GoodsComponent {
    void inject(GoodsFragment fragment);
}