package com.geek.huixiaoer.mvp.person.di.component;

import com.geek.huixiaoer.mvp.person.di.module.MyShopOrderModule;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.person.ui.activity.MyShopOrderActivity;

@ActivityScope
@Component(modules = MyShopOrderModule.class, dependencies = AppComponent.class)
public interface MyShopOrderComponent {
    void inject(MyShopOrderActivity activity);
}