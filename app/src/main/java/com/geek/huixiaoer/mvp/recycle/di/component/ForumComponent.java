package com.geek.huixiaoer.mvp.recycle.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.recycle.di.module.ForumModule;

import com.geek.huixiaoer.mvp.recycle.ui.activity.ForumActivity;

@ActivityScope
@Component(modules = ForumModule.class, dependencies = AppComponent.class)
public interface ForumComponent {
    void inject(ForumActivity activity);
}