package com.geek.huixiaoer.mvp.person.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.person.di.module.OrderListModule;

import com.geek.huixiaoer.mvp.person.ui.fragment.OrderListFragment;

@ActivityScope
@Component(modules = OrderListModule.class, dependencies = AppComponent.class)
public interface OrderListComponent {
    void inject(OrderListFragment fragment);
}