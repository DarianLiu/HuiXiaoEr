package com.geek.huixiaoer.mvp.person.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.person.di.module.PersonCenterModule;

import com.geek.huixiaoer.mvp.person.ui.activity.PersonCenterActivity;

@ActivityScope
@Component(modules = PersonCenterModule.class, dependencies = AppComponent.class)
public interface PersonCenterComponent {
    void inject(PersonCenterActivity activity);
}