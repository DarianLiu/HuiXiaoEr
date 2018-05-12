package com.geek.huixiaoer.mvp.recycle.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.recycle.di.module.ForumPostModule;

import com.geek.huixiaoer.mvp.recycle.ui.activity.ForumPostActivity;

@ActivityScope
@Component(modules = ForumPostModule.class, dependencies = AppComponent.class)
public interface ForumPostComponent {
    void inject(ForumPostActivity activity);
}