package com.geek.huixiaoer.mvp.person.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.mvp.person.contract.ShopOrderDetailContract;
import com.geek.huixiaoer.mvp.person.di.component.DaggerShopOrderDetailComponent;
import com.geek.huixiaoer.mvp.person.di.module.ShopOrderDetailModule;
import com.geek.huixiaoer.mvp.person.presenter.ShopOrderDetailPresenter;
import com.geek.huixiaoer.mvp.person.ui.adapter.OrderDetailAdapter;
import com.geek.huixiaoer.storage.entity.shop.OrderDetailBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ShopOrderDetailActivity extends BaseActivity<ShopOrderDetailPresenter> implements ShopOrderDetailContract.View {
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.cancelText)
    TextView cancelText;
    @BindView(R.id.payText)
    TextView payText;
    @BindView(R.id.goLayout)
    LinearLayout goLayout;

    private String orderSn, amount;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerShopOrderDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .shopOrderDetailModule(new ShopOrderDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_shop_order_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        tvToolbarTitle.setText(R.string.title_order_detail);

        orderSn = getIntent().getStringExtra("orderSn");
        mPresenter.orderDetail(orderSn);

        payText.setOnClickListener(v -> mPresenter.paymentSubmitNo(orderSn, amount));
        cancelText.setOnClickListener(v -> mPresenter.cancelOrder(orderSn));
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

    @Override
    public void updateView(OrderDetailBean orderDetail) {
        amount = String.valueOf(orderDetail.getMerchantCash());
        listView.setAdapter(new OrderDetailAdapter(this, orderDetail));
        if (TextUtils.equals(orderDetail.getOrderStatus(), "支付成功")) {
            goLayout.setVisibility(View.GONE);
        } else if (TextUtils.equals(orderDetail.getOrderStatus(), "等待付款")) {
            goLayout.setVisibility(View.VISIBLE);
        }
    }

}
