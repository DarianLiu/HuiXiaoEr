package com.geek.huixiaoer.mvp.person.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.person.di.module.ReceiverAddModule;

import com.geek.huixiaoer.mvp.person.ui.activity.ReceiverAddActivity;

@ActivityScope
@Component(modules = ReceiverAddModule.class, dependencies = AppComponent.class)
public interface ReceiverAddComponent {
    void inject(ReceiverAddActivity activity);
}