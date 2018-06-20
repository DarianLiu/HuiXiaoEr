package com.geek.huixiaoer.mvp.housewifery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.utils.StringUtils;
import com.geek.huixiaoer.common.widget.dialog.CircleProgressDialog;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.geek.huixiaoer.mvp.housewifery.di.component.DaggerHelpOrderConfirmComponent;
import com.geek.huixiaoer.mvp.housewifery.di.module.HelpOrderConfirmModule;
import com.geek.huixiaoer.mvp.housewifery.contract.HelpOrderConfirmContract;
import com.geek.huixiaoer.mvp.housewifery.presenter.HelpOrderConfirmPresenter;

import com.geek.huixiaoer.R;
import com.jess.arms.utils.DataHelper;


import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class HelpOrderConfirmActivity extends BaseActivity<HelpOrderConfirmPresenter> implements HelpOrderConfirmContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.trueNameEdit)
    EditText trueNameEdit;
    @BindView(R.id.mobileEdit)
    EditText mobileEdit;
    @BindView(R.id.tv_service_name)
    TextView tvServiceName;
    @BindView(R.id.addressEdit)
    EditText addressEdit;
    @BindView(R.id.priceEdit)
    EditText priceEdit;

    private String targetId;//融云客服ID

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHelpOrderConfirmComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .helpOrderConfirmModule(new HelpOrderConfirmModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_help_order_confirm; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> finish());
        tvToolbarTitle.setText("确认订单");

        targetId = getIntent().getStringExtra("targetId");
        String title = getIntent().getStringExtra("title");
        tvServiceName.setText(title);
//        Log.d("====OrderConfirm-title",title);
    }

    @OnClick({R.id.submitBtn})
    public void onViewClicked(View view) {
        String trueName = trueNameEdit.getText().toString();
        if (TextUtils.isEmpty(trueName)) {
            Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String mobile = mobileEdit.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String address = addressEdit.getText().toString();
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String price = priceEdit.getText().toString();
        if (TextUtils.isEmpty(price)) {
            Toast.makeText(this, "金额不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()) {
            case R.id.submitBtn:
                //consignee,address,zipCode,mobile,goodsId,amount,memo
                String serviceId = DataHelper.getStringSF(this, Constants.CASH_SERVICE_ID);
                mPresenter.createServiceOrder(StringUtils.stringUTF8(trueName),
                        StringUtils.stringUTF8(address), "00000", mobile, serviceId, price, String.valueOf(System.currentTimeMillis()), targetId);
                break;
        }
    }

    private CircleProgressDialog loadingDialog;

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
    protected void onDestroy() {
        super.onDestroy();
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        loadingDialog = null;
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void updateView() {

    }
}
