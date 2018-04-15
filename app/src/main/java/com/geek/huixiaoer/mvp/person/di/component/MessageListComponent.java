package com.geek.huixiaoer.mvp.person.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.person.di.module.MessageListModule;

import com.geek.huixiaoer.mvp.person.ui.activity.MessageListActivity;

@ActivityScope
@Component(modules = MessageListModule.class, dependencies = AppComponent.class)
public interface MessageListComponent {
    void inject(MessageListActivity activity);
}