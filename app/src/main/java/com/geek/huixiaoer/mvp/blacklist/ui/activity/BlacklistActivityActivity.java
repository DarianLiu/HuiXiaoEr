package com.geek.huixiaoer.mvp.blacklist.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.geek.huixiaoer.mvp.blacklist.di.component.DaggerBlacklistActivityComponent;
import com.geek.huixiaoer.mvp.blacklist.di.module.BlacklistActivityModule;
import com.geek.huixiaoer.mvp.blacklist.contract.BlacklistActivityContract;
import com.geek.huixiaoer.mvp.blacklist.presenter.BlacklistActivityPresenter;

import com.geek.huixiaoer.mvp.blacklist.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class BlacklistActivityActivity extends BaseActivity<BlacklistActivityPresenter> implements BlacklistActivityContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBlacklistActivityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .blacklistActivityModule(new BlacklistActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_blacklist; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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
