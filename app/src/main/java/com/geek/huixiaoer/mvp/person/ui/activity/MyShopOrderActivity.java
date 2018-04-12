package com.geek.huixiaoer.mvp.person.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.geek.huixiaoer.common.widget.adapter.DefaultStatePagerAdapter;
import com.geek.huixiaoer.mvp.person.contract.MyShopOrderContract;
import com.geek.huixiaoer.mvp.person.presenter.MyShopOrderPresenter;
import com.geek.huixiaoer.mvp.person.ui.fragment.ShopOrderFragment;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.geek.huixiaoer.mvp.person.di.component.DaggerMyShopOrderComponent;
import com.geek.huixiaoer.mvp.person.di.module.MyShopOrderModule;

import com.geek.huixiaoer.R;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 我的购物订单（商超）
 */
public class MyShopOrderActivity extends BaseActivity<MyShopOrderPresenter> implements MyShopOrderContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindString(R.string.order_state_all)
    String order_state_all;
//    @BindString(R.string.order_state_payment)
//    String order_state_payment;
//    @BindString(R.string.order_state_shipment)
//    String order_state_shipment;
//    @BindString(R.string.order_state_receive)
//    String order_state_receive;
//    @BindString(R.string.order_state_completed)
//    String order_state_completed;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMyShopOrderComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myShopOrderModule(new MyShopOrderModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_shop; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbarTitle.setText(R.string.title_my_order);

        initViewPager();
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {

        List<Fragment> list_fragment = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        //初始化fragment
        ShopOrderFragment fragmentAll = new ShopOrderFragment();
//        ShopOrderFragment fragmentWaitPay = new ShopOrderFragment();
//        ShopOrderFragment fragmentWaitReceipt = new ShopOrderFragment();
//        ShopOrderFragment fragmentWaitDelivery = new ShopOrderFragment();
//        ShopOrderFragment fragmentComplete = new ShopOrderFragment();
        list_fragment.add(fragmentAll);
        titles.add(order_state_all);
//        list_fragment.add(fragmentWaitPay);
//        titles.add(order_state_payment);
//        list_fragment.add(fragmentWaitDelivery);
//        titles.add(order_state_shipment);
//        list_fragment.add(fragmentWaitReceipt);
//        titles.add(order_state_receive);
//        list_fragment.add(fragmentComplete);
//        titles.add(order_state_completed);


        //给fragment绑定参数
        String[] status = {"", "pendingPayment", "pendingShipment", "shipped", "completed"};
        for (int i = 0; i < list_fragment.size(); i++) {
            //为TabLayout添加tab内容
            Bundle args = new Bundle();
            args.putString("status", status[i]);
            list_fragment.get(i).setArguments(args);
        }

        DefaultStatePagerAdapter pagerAdapter = new DefaultStatePagerAdapter(
                getSupportFragmentManager(), list_fragment, titles);
        viewPager.setAdapter(pagerAdapter);
        //TabLayout加载viewpager
        tabLayout.setupWithViewPager(viewPager);
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
