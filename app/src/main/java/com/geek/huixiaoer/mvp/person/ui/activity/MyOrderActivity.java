package com.geek.huixiaoer.mvp.person.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.geek.huixiaoer.mvp.person.di.component.DaggerMyOrderComponent;
import com.geek.huixiaoer.mvp.person.di.module.MyOrderModule;
import com.geek.huixiaoer.mvp.person.contract.MyOrderContract;
import com.geek.huixiaoer.mvp.person.presenter.MyOrderPresenter;

import com.geek.huixiaoer.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MyOrderActivity extends BaseActivity<MyOrderPresenter> implements MyOrderContract.View {


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMyOrderComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myOrderModule(new MyOrderModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_my_order; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
