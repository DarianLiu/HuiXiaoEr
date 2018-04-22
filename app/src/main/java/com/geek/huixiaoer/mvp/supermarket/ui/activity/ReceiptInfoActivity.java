package com.geek.huixiaoer.mvp.supermarket.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.geek.huixiaoer.common.widget.dialog.CircleProgressDialog;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.geek.huixiaoer.mvp.supermarket.di.component.DaggerReceiptInfoComponent;
import com.geek.huixiaoer.mvp.supermarket.di.module.ReceiptInfoModule;
import com.geek.huixiaoer.mvp.supermarket.contract.ReceiptInfoContract;
import com.geek.huixiaoer.mvp.supermarket.presenter.ReceiptInfoPresenter;

import com.geek.huixiaoer.R;


import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ReceiptInfoActivity extends BaseActivity<ReceiptInfoPresenter> implements ReceiptInfoContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.trueNameEdit)
    EditText trueNameEdit;
    @BindView(R.id.mobileEdit)
    EditText mobileEdit;
    @BindView(R.id.addressEdit)
    EditText addressEdit;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerReceiptInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .receiptInfoModule(new ReceiptInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_receipt_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvToolbarTitle.setText("确认订单");
    }

    @OnClick({R.id.saveBtn})
    public void onViewClicked(View view) {
        String trueName = trueNameEdit.getText().toString();
        if (TextUtils.isEmpty(trueName)) {
            Toast.makeText(this,"姓名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        String mobile = mobileEdit.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(this,"手机号不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        String address = addressEdit.getText().toString();
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this,"地址不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()) {
            case R.id.saveBtn:
                //consignee,address,zipCode,mobile,goodsId,amount,memo
//                mPresenter.createServiceOrder(trueName,address,"00000",mobile,"128",price,"2018-04-17");
                Intent it = new Intent();
                it.putExtra("trueName",trueNameEdit.getText().toString());
                it.putExtra("mobile",mobileEdit.getText().toString());
                it.putExtra("address",addressEdit.getText().toString());
                setResult(100, it);
                killMyself();
                break;
            case R.id.tv_register:
//                launchActivity(new Intent(LoginActivity.this, CaptchaActivity.class));
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
}
