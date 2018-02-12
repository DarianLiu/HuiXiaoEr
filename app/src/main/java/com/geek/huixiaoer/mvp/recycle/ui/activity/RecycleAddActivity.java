package com.geek.huixiaoer.mvp.recycle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.geek.huixiaoer.mvp.recycle.di.component.DaggerRecycleAddComponent;
import com.geek.huixiaoer.mvp.recycle.di.module.RecycleAddModule;
import com.geek.huixiaoer.mvp.recycle.contract.RecycleAddContract;
import com.geek.huixiaoer.mvp.recycle.presenter.RecycleAddPresenter;

import com.geek.huixiaoer.mvp.recycle.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class RecycleAddActivity extends BaseActivity<RecycleAddPresenter> implements RecycleAddContract.View {


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerRecycleAddComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .recycleAddModule(new RecycleAddModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_recycle_add; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
