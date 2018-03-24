package com.geek.huixiaoer.mvp.person.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.person.di.module.MyReceiverModule;

import com.geek.huixiaoer.mvp.person.ui.activity.MyReceiverActivity;

@ActivityScope
@Component(modules = MyReceiverModule.class, dependencies = AppComponent.class)
public interface MyReceiverComponent {
    void inject(MyReceiverActivity activity);
}