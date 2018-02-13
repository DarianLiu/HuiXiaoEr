package com.geek.huixiaoer.mvp.supermarket.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.geek.huixiaoer.mvp.supermarket.di.component.DaggerOrderCreateComponent;
import com.geek.huixiaoer.mvp.supermarket.di.module.OrderCreateModule;
import com.geek.huixiaoer.mvp.supermarket.contract.OrderCreateContract;
import com.geek.huixiaoer.mvp.supermarket.presenter.OrderCreatePresenter;

import com.geek.huixiaoer.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class OrderCreateActivity extends BaseActivity<OrderCreatePresenter> implements OrderCreateContract.View {


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerOrderCreateComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .orderCreateModule(new OrderCreateModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_order_create; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


}
