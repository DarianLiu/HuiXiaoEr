package com.geek.huixiaoer.mvp.person.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.person.contract.TabOrderContract;
import com.geek.huixiaoer.mvp.person.model.TabOrderModel;


@Module
public class TabOrderModule {
    private TabOrderContract.View view;

    /**
     * 构建TabOrderModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public TabOrderModule(TabOrderContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    TabOrderContract.View provideTabOrderView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    TabOrderContract.Model provideTabOrderModel(TabOrderModel model) {
        return model;
    }
}