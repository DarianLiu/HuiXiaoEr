package com.geek.huixiaoer.mvp.recycle.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.recycle.di.module.RankModule;

import com.geek.huixiaoer.mvp.recycle.ui.fragment.RankFragment;

@ActivityScope
@Component(modules = RankModule.class, dependencies = AppComponent.class)
public interface RankComponent {
    void inject(RankFragment fragment);
}