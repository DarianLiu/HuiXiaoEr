package com.geek.huixiaoer.mvp.recycle.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.recycle.di.module.ForumPostDetailModule;

import com.geek.huixiaoer.mvp.recycle.ui.activity.ForumPostDetailActivity;

@ActivityScope
@Component(modules = ForumPostDetailModule.class, dependencies = AppComponent.class)
public interface ForumPostDetailComponent {
    void inject(ForumPostDetailActivity activity);
}