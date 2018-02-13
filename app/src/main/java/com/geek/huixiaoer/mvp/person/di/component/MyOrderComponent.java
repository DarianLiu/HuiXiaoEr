package com.geek.huixiaoer.mvp.person.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.person.di.module.MyOrderModule;

import com.geek.huixiaoer.mvp.person.ui.activity.MyOrderActivity;

@ActivityScope
@Component(modules = MyOrderModule.class, dependencies = AppComponent.class)
public interface MyOrderComponent {
    void inject(MyOrderActivity activity);
}