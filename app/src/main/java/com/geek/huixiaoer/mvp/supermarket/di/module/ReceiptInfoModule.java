package com.geek.huixiaoer.mvp.supermarket.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.supermarket.contract.ReceiptInfoContract;
import com.geek.huixiaoer.mvp.supermarket.model.ReceiptInfoModel;


@Module
public class ReceiptInfoModule {
    private ReceiptInfoContract.View view;

    /**
     * 构建ReceiptInfoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ReceiptInfoModule(ReceiptInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ReceiptInfoContract.View provideReceiptInfoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ReceiptInfoContract.Model provideReceiptInfoModel(ReceiptInfoModel model) {
        return model;
    }
}