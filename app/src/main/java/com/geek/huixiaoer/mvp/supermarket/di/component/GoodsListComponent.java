package com.geek.huixiaoer.mvp.supermarket.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.supermarket.di.module.GoodsListModule;

import com.geek.huixiaoer.mvp.supermarket.ui.fragment.GoodsListFragment;

@ActivityScope
@Component(modules = GoodsListModule.class, dependencies = AppComponent.class)
public interface GoodsListComponent {
    void inject(GoodsListFragment fragment);
}