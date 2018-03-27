package com.geek.huixiaoer.mvp.person.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.utils.JsonUtil;
import com.geek.huixiaoer.common.widget.OptionView;
import com.geek.huixiaoer.common.widget.dialog.CircleProgressDialog;
import com.geek.huixiaoer.common.widget.wheelview.ChooseAddressWheel;
import com.geek.huixiaoer.common.widget.wheelview.OnAddressChangeListener;
import com.geek.huixiaoer.mvp.person.contract.ReceiverAddContract;
import com.geek.huixiaoer.mvp.person.di.component.DaggerReceiverAddComponent;
import com.geek.huixiaoer.mvp.person.di.module.ReceiverAddModule;
import com.geek.huixiaoer.mvp.person.presenter.ReceiverAddPresenter;
import com.geek.huixiaoer.storage.entity.address.AddressDetailsEntity;
import com.geek.huixiaoer.storage.entity.address.AddressModel;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ReceiverAddActivity extends BaseActivity<ReceiverAddPresenter> implements ReceiverAddContract.View, OnAddressChangeListener {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_receive_name)
    EditText etReceiveName;
    @BindView(R.id.et_receive_phone)
    EditText etReceivePhone;
    @BindView(R.id.option_receiver_area)
    OptionView optionReceiverArea;
    @BindView(R.id.et_receive_address)
    EditText etReceiveAddress;
    @BindView(R.id.et_postal_code)
    EditText etPostalCode;
    @BindView(R.id.cb_default)
    CheckBox cbDefault;
    @BindView(R.id.btn_sure)
    Button btnSure;

    private CircleProgressDialog loadingDialog;
    private ChooseAddressWheel chooseAddressWheel;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerReceiverAddComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .receiverAddModule(new ReceiverAddModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_receiver_add; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initWheel();
        mPresenter.getCityData();
    }

    private void initWheel() {
        chooseAddressWheel = new ChooseAddressWheel(this);
        chooseAddressWheel.setOnAddressChangeListener(this);
    }

    @Override
    public void setCityWheel(String address) {
        AddressModel model = JsonUtil.parseJson(address, AddressModel.class);
        if (model != null) {
            AddressDetailsEntity data = model.Result;
            if (data == null) return;
            if (data.ProvinceItems != null && data.ProvinceItems.Province != null) {
                chooseAddressWheel.setProvince(data.ProvinceItems.Province);
                chooseAddressWheel.defaultValue(data.Province, data.City, data.Area);
            }
        }
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

    @OnClick(R.id.option_receiver_area)
    public void onSelectArea(View view) {
        chooseAddressWheel.show(view);
    }

    @OnClick(R.id.btn_sure)
    public void onAddReceiver() {

    }

    @Override
    public void onAddressChange(String province, String city, String district, String id) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        chooseAddressWheel.destroy();
        chooseAddressWheel = null;
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        loadingDialog = null;
    }
}
