package com.geek.huixiaoer.mvp.supermarket.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.mvp.supermarket.contract.GoodsDetailContract;
import com.geek.huixiaoer.mvp.supermarket.di.component.DaggerGoodsDetailComponent;
import com.geek.huixiaoer.mvp.supermarket.di.module.GoodsDetailModule;
import com.geek.huixiaoer.mvp.supermarket.presenter.GoodsDetailPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class GoodsDetailActivity extends BaseActivity<GoodsDetailPresenter> implements GoodsDetailContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.goodsWebView)
    WebView webView;
    @BindView(R.id.tv_favorite)
    TextView tvFavorite;
    @BindView(R.id.tv_add_cart)
    TextView tvAddCart;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerGoodsDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .goodsDetailModule(new GoodsDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_goods_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> finish());

        String goods_name = getIntent().getStringExtra(Constants.INTENT_GOODS_NAME);
        String goods_url = getIntent().getStringExtra(Constants.INTENT_GOODS_URL);
        String goods_sn = getIntent().getStringExtra(Constants.INTENT_GOODS_SN);

        Timber.d("========goods_url：" + goods_url);

        toolbarTitle.setText(goods_name == null ? "" : goods_name);
        initWebView(goods_url);
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
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.setInitialScale(100);
        webView.requestFocus();
        webView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webView.loadUrl(url);
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


    @OnClick({R.id.tv_favorite, R.id.tv_add_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_favorite:
                break;
            case R.id.tv_add_cart:
                break;
        }
    }
}
