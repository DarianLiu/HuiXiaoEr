package com.geek.huixiaoer.mvp.supermarket.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.geek.huixiaoer.mvp.supermarket.di.component.DaggerGoodsComponent;
import com.geek.huixiaoer.mvp.supermarket.di.module.GoodsModule;
import com.geek.huixiaoer.mvp.supermarket.contract.GoodsContract;
import com.geek.huixiaoer.mvp.supermarket.presenter.GoodsPresenter;

import com.geek.huixiaoer.R;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class GoodsFragment extends BaseFragment<GoodsPresenter> implements GoodsContract.View {


    public static GoodsFragment newInstance() {
        GoodsFragment fragment = new GoodsFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerGoodsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .goodsModule(new GoodsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void setData(Object data) {

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

    }

}
