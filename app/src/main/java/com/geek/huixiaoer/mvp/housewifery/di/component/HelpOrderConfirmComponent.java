package com.geek.huixiaoer.mvp.housewifery.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.geek.huixiaoer.mvp.housewifery.di.module.HelpOrderConfirmModule;

import com.geek.huixiaoer.mvp.housewifery.ui.activity.HelpOrderConfirmActivity;

@ActivityScope
@Component(modules = HelpOrderConfirmModule.class, dependencies = AppComponent.class)
public interface HelpOrderConfirmComponent {
    void inject(HelpOrderConfirmActivity activity);
}