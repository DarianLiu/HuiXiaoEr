package com.geek.huixiaoer.mvp.supermarket.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.supermarket.di.module.ReceiptInfoModule;

import com.geek.huixiaoer.mvp.supermarket.ui.activity.ReceiptInfoActivity;

@ActivityScope
@Component(modules = ReceiptInfoModule.class, dependencies = AppComponent.class)
public interface ReceiptInfoComponent {
    void inject(ReceiptInfoActivity activity);
}