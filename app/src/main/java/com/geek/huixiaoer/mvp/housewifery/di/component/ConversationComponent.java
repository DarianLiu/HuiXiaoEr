package com.geek.huixiaoer.mvp.housewifery.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.housewifery.di.module.ConversationModule;

import com.geek.huixiaoer.mvp.housewifery.ui.activity.ConversationActivity;

@ActivityScope
@Component(modules = ConversationModule.class, dependencies = AppComponent.class)
public interface ConversationComponent {
    void inject(ConversationActivity activity);
}