package com.geek.huixiaoer.mvp.supermarket.ui.activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.widget.dialog.CircleProgressDialog;
import com.geek.huixiaoer.mvp.common.ui.activity.LoginActivity;
import com.geek.huixiaoer.mvp.supermarket.contract.GoodsDetailContract;
import com.geek.huixiaoer.mvp.supermarket.di.component.DaggerGoodsDetailComponent;
import com.geek.huixiaoer.mvp.supermarket.di.module.GoodsDetailModule;
import com.geek.huixiaoer.mvp.supermarket.presenter.GoodsDetailPresenter;
import com.geek.huixiaoer.mvp.supermarket.ui.fragment.ProductSelectFragment;
import com.geek.huixiaoer.storage.entity.shop.SpecificationBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.btn_favorite)
    Button btnFavorite;
    @BindView(R.id.btn_add_cart)
    Button btnAddCart;

    private CircleProgressDialog loadingDialog;

    private ArrayList<SpecificationBean> specificationList;

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

        toolbarTitle.setText(goods_name == null ? "商品详情" : goods_name);
        initWebView(goods_url);

        mPresenter.goodsSpecification(goods_sn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shopping_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_shopping_cart:
                String token = DataHelper.getStringSF(GoodsDetailActivity.this, Constants.SP_TOKEN);
                if (TextUtils.isEmpty(token)) {
                    launchActivity(new Intent(GoodsDetailActivity.this, LoginActivity.class));
                } else {
                    launchActivity(new Intent(GoodsDetailActivity.this, ShoppingCartActivity.class));
                }
                break;
        }
        return true;
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


    @OnClick({R.id.tv_favorite, R.id.tv_add_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_favorite:
                showMessage("该功能暂未开放");
//                mPresenter.favorite(goods_sn);
                break;
            case R.id.btn_add_cart:
                String token = DataHelper.getStringSF(GoodsDetailActivity.this, Constants.SP_TOKEN);
                if (TextUtils.isEmpty(token)) {
                    launchActivity(new Intent(GoodsDetailActivity.this, LoginActivity.class));
                } else {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    String TAG = "dialog_specification";
                    Fragment fragment = getFragmentManager().findFragmentByTag(TAG);
                    if (null != fragment) {
                        ft.remove(fragment);
                    }
                    ProductSelectFragment dialogFragment = new ProductSelectFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("specification", specificationList);
                    dialogFragment.setArguments(bundle);
                    dialogFragment.setOnClickListener((productId, quantity) ->
                            mPresenter.addCart(productId, quantity));
                    dialogFragment.show(getSupportFragmentManager(), TAG);
                }
                break;
        }
    }

    @Override
    public void updateFavoriteState(boolean isFavorite) {

    }

    @Override
    public void updateView(List<SpecificationBean> specificationList) {
        this.specificationList = (ArrayList<SpecificationBean>) specificationList;
    }
}
