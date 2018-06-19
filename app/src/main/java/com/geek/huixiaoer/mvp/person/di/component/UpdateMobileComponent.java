package com.geek.huixiaoer.mvp.person.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.person.di.module.UpdateMobileModule;

import com.geek.huixiaoer.mvp.person.ui.activity.UpdateMobileActivity;

@ActivityScope
@Component(modules = UpdateMobileModule.class, dependencies = AppComponent.class)
public interface UpdateMobileComponent {
    void inject(UpdateMobileActivity activity);
}