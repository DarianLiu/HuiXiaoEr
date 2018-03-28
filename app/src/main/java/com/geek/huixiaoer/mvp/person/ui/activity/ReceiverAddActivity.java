package com.geek.huixiaoer.mvp.person.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.utils.JsonUtil;
import com.geek.huixiaoer.common.utils.RegexUtils;
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
import com.geek.huixiaoer.storage.entity.shop.ReceiverBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindString;
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

    @BindString(R.string.hint_receiver_area)
    String hint_receiver_area;

    @BindString(R.string.error_receiver_name)
    String error_receiver_name;
    @BindString(R.string.error_receiver_phone)
    String error_receiver_phone;
    @BindString(R.string.error_receiver_area)
    String error_receiver_area;
    @BindString(R.string.error_receiver_address)
    String error_receiver_address;
    @BindString(R.string.error_receiver_postal_code)
    String error_receiver_postal_code;

    private CircleProgressDialog loadingDialog;
    private ChooseAddressWheel chooseAddressWheel;

    private String type;//保存新的收货地址/更新收货地址
    private String mReceiverId;//收货地址ID（更新时用到）
    private int mPostion;//列表选中项（更新时用到）

    private String mAreaName;
    private String mAreaId;

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
        //设置ToolBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        optionReceiverArea.setRightText(hint_receiver_area);

        type = getIntent().getStringExtra(Constants.INTENT_TYPE);
        if (TextUtils.equals(type, "add")) {
            tvToolbarTitle.setText(R.string.title_receiver_add);
        } else {
            tvToolbarTitle.setText(R.string.title_receiver_update);

            mPostion = getIntent().getIntExtra(Constants.INTENT_LIST_POSITION, -1);
            ReceiverBean receiverBean = (ReceiverBean) getIntent().getSerializableExtra(Constants.INTENT_RECEIVER);
            mReceiverId = receiverBean.getId();

            etReceiveName.setText(receiverBean.getConsignee());
            etReceivePhone.setText(receiverBean.getPhone());
            optionReceiverArea.setRightText(receiverBean.getConsignee());
            etReceiveAddress.setText(receiverBean.getAddress());
            etPostalCode.setText(receiverBean.getZipCode());
            cbDefault.setChecked(receiverBean.isIsDefault());

        }


        //初始化城市/地区选择器
        initWheel();
        //获取城市/地区列表数据
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
        String name = etReceiveName.getText().toString();
        String phone = etReceivePhone.getText().toString();
        String address = etReceiveAddress.getText().toString();
        String postal_code = etPostalCode.getText().toString();
        boolean isDefault = cbDefault.isChecked();

        /*数据校验*/
        if (TextUtils.isEmpty(name)) {
            showMessage(error_receiver_name);
        } else if (TextUtils.isEmpty(phone) && !RegexUtils.isMobileExact(phone)) {
            showMessage(error_receiver_phone);
        } else if (TextUtils.isEmpty(mAreaId)) {
            showMessage(error_receiver_area);
        } else if (TextUtils.isEmpty(address)) {
            showMessage(error_receiver_address);
        } else if (TextUtils.isEmpty(postal_code) && postal_code.length() != 6) {
            showMessage(error_receiver_postal_code);
        } else if (TextUtils.equals(type, "add")) {
            mPresenter.receiverSave(name, mAreaName, address, postal_code, phone, isDefault, mAreaId);
        } else {
            mPresenter.receiverUpdate(mPostion, name, mAreaName, address, postal_code, phone, isDefault, mAreaId, mReceiverId, mReceiverId);
        }
    }

    @Override
    public void onAddressChange(String province, String city, String district, String id) {
        StringBuilder stringBuilder = new StringBuilder();
        if (TextUtils.equals(province, district)) {
            stringBuilder.append(district);
        } else if (TextUtils.equals(province, city)) {
            stringBuilder.append(city);
            stringBuilder.append(district);
        } else if (TextUtils.equals(city, district)) {
            stringBuilder.append(province);
            stringBuilder.append(city);
        } else {
            stringBuilder.append(province);
            stringBuilder.append(city);
            stringBuilder.append(district);
        }
        mAreaId = id;
        mAreaName = stringBuilder.toString();
        optionReceiverArea.setRightText(mAreaName);
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
