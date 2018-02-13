package com.geek.huixiaoer.mvp.person.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.person.contract.PersonCenterContract;
import com.geek.huixiaoer.mvp.person.model.PersonCenterModel;


@Module
public class PersonCenterModule {
    private PersonCenterContract.View view;

    /**
     * 构建PersonCenterModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PersonCenterModule(PersonCenterContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PersonCenterContract.View providePersonCenterView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PersonCenterContract.Model providePersonCenterModel(PersonCenterModel model) {
        return model;
    }
}