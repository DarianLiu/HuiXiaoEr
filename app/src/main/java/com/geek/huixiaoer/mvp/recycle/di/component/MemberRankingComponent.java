package com.geek.huixiaoer.mvp.recycle.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.recycle.di.module.MemberRankingModule;

import com.geek.huixiaoer.mvp.recycle.ui.activity.MemberRankingActivity;

@ActivityScope
@Component(modules = MemberRankingModule.class, dependencies = AppComponent.class)
public interface MemberRankingComponent {
    void inject(MemberRankingActivity activity);
}