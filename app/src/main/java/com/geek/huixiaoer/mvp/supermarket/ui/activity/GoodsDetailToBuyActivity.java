package com.geek.huixiaoer.mvp.supermarket.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.widget.dialog.CircleProgressDialog;
import com.geek.huixiaoer.storage.entity.shop.SpecificationBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.geek.huixiaoer.mvp.supermarket.di.component.DaggerGoodsDetailToBuyComponent;
import com.geek.huixiaoer.mvp.supermarket.di.module.GoodsDetailToBuyModule;
import com.geek.huixiaoer.mvp.supermarket.contract.GoodsDetailToBuyContract;
import com.geek.huixiaoer.mvp.supermarket.presenter.GoodsDetailToBuyPresenter;

import com.geek.huixiaoer.R;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class GoodsDetailToBuyActivity extends BaseActivity<GoodsDetailToBuyPresenter> implements GoodsDetailToBuyContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.goodsWebView)
    WebView webView;

    private CircleProgressDialog loadingDialog;
    private ArrayList<SpecificationBean> specificationList;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGoodsDetailToBuyComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .goodsDetailToBuyModule(new GoodsDetailToBuyModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_goods_detail_to_buy; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> finish());
        String goods_name = getIntent().getStringExtra(Constants.INTENT_GOODS_NAME);
        String goods_url = getIntent().getStringExtra(Constants.INTENT_GOODS_URL);
        String goods_sn = getIntent().getStringExtra(Constants.INTENT_GOODS_SN);

        Timber.d("========goods_url：" + goods_url);

        toolbarTitle.setText(goods_name == null ? "商品详情" : goods_name);
        initWebView(goods_url);

        mPresenter.goodsSpecification(goods_sn);
    }

    /**
     * 初始化WebView
     */
    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView(String url) {
        // 加上这句话才能使用javascript方法
        webView.getSettings().setJavaScriptEnabled(true);
        // 加上这句话，支持html5的某些alert提示框的弹出
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        webView.getSettings().setLoadWithOverviewMode(true);//设置网页充满全屏
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.setInitialScale(100);
        webView.requestFocus();
        webView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webView.loadUrl(url);
    }

    @OnClick({R.id.buyBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buyBtn:
                startActivityForResult(new Intent(this, ReceiptInfoActivity.class),100);
//                mPresenter.createShopOrder(trueName,address,"00000",mobile,"128",price,"2018-04-17");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==100 && data!=null) {
           String trueName = data.getStringExtra("trueName");
            String mobile = data.getStringExtra("mobile");
            String address = data.getStringExtra("address");
            SpecificationBean bean  = specificationList.get(0);
            mPresenter.createShopOrder(trueName,address,"00000",mobile,bean.getId(),String.valueOf(bean.getPrice()),"2018-04-17");
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new CircleProgressDialog.Builder(this).create();
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
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
    public void updateView() {

    }
    @Override
    public void updateView(List<SpecificationBean> specificationList) {
        this.specificationList = (ArrayList<SpecificationBean>) specificationList;
    }
}
