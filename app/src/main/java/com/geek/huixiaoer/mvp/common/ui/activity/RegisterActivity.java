package com.geek.huixiaoer.mvp.common.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.utils.RegexUtils;
import com.geek.huixiaoer.common.utils.StringUtils;
import com.geek.huixiaoer.common.widget.dialog.CircleProgressDialog;
import com.geek.huixiaoer.mvp.common.contract.RegisterContract;
import com.geek.huixiaoer.mvp.common.di.component.DaggerRegisterComponent;
import com.geek.huixiaoer.mvp.common.di.module.RegisterModule;
import com.geek.huixiaoer.mvp.common.presenter.RegisterPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 注册页面
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.til_nickname)
    TextInputLayout tilNickname;
    @BindView(R.id.et_nickname)
    TextInputEditText etNickname;
    @BindView(R.id.til_new_password)
    TextInputLayout tilNewPassword;
    @BindView(R.id.et_new_password)
    TextInputEditText etNewPassword;
    @BindView(R.id.til_confirm_password)
    TextInputLayout tilConfirmPassword;
    @BindView(R.id.et_confirm_password)
    TextInputEditText etConfirmPassword;
    @BindView(R.id.til_invitation_code)
    TextInputLayout tilInvitationCode;
    @BindView(R.id.et_invitation_code)
    TextInputEditText etInvitationCode;
    @BindView(R.id.et_cardNo)
    TextInputEditText etCardNo;
    @BindView(R.id.til_cardNo)
    TextInputLayout tilCardNo;
    @BindView(R.id.et_address)
    TextInputEditText etAddress;
    @BindView(R.id.til_address)
    TextInputLayout tilAddress;
    @BindView(R.id.rb_yes)
    RadioButton rbYes;
    @BindView(R.id.rb_no)
    RadioButton rbNo;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @BindString(R.string.error_nickname_null)
    String error_nickname_null;
    @BindString(R.string.error_nickname)
    String error_nickname;
    @BindString(R.string.error_password_new_null)
    String error_password_new_null;
    @BindString(R.string.error_password_confirm_null)
    String error_password_confirm_null;
    @BindString(R.string.error_password_not_equals)
    String error_password_not_equals;
    @BindString(R.string.error_street_null)
    String error_street_null;
    @BindString(R.string.error_cardNo_null)
    String error_cardNo_null;
    @BindString(R.string.error_address_null)
    String error_address_null;

    private String mobile, cityCode ="512", areaCode ="513";
    boolean volunteer;//是否志愿者

    private CircleProgressDialog loadingDialog;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerRegisterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .registerModule(new RegisterModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_register; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> finish());
        tvToolbarTitle.setText(R.string.title_register);

        mobile = getIntent().getStringExtra(Constants.INTENT_MOBILE);
        rbNo.setChecked(true);
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

    /**
     * 验证昵称
     *
     * @param nickname 昵称
     */
    private boolean validateNickname(String nickname) {
        if (TextUtils.isEmpty(nickname)) {
            showError(tilNickname, error_nickname_null);
            return false;
        } else if (!RegexUtils.isUsername(nickname)) {
            showError(tilNickname, error_nickname);
            return false;
        }
        tilNickname.setErrorEnabled(false);
        return true;
    }

    /**
     * 验证密码
     *
     * @param password 密码
     */
    private boolean validatePassword(String password, String confirmPassword) {
        if (TextUtils.isEmpty(password)) {
            showError(tilNewPassword, error_password_new_null);
            return false;
        } else if (TextUtils.isEmpty(confirmPassword)) {
            showError(tilConfirmPassword, error_password_confirm_null);
            return false;
        } else if (!TextUtils.equals(password, confirmPassword)) {
            showError(tilConfirmPassword, error_password_not_equals);
            return false;
        }
        tilNewPassword.setErrorEnabled(false);
        tilConfirmPassword.setErrorEnabled(false);
        return true;
    }

    /**
     * 其他信息校验
     *
     * @param cardNo   身份证号码
     * @param cityCode 镇（街道）code
     * @param areaCode 村（社区）code
     * @param address  详细地址
     */
    private boolean validateOther(String cardNo, String cityCode, String areaCode, String address) {
        if (TextUtils.isEmpty(cardNo)) {
            showError(tilCardNo, error_cardNo_null);
            return false;
        } else if (TextUtils.isEmpty(cityCode) || TextUtils.isEmpty(areaCode)) {
            showMessage(error_street_null);
            return false;
        } else if (TextUtils.isEmpty(address)) {
            showError(tilAddress, error_address_null);
            return false;
        }
        tilCardNo.setErrorEnabled(false);
        tilAddress.setErrorEnabled(false);
        return true;
    }

    /**
     * 显示错误提示
     *
     * @param textInputLayout 对应控件
     * @param error           错误信息
     */
    private void showError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
    }

    @OnClick(R.id.btn_register)
    public void onViewClicked() {
        String nickname = etNickname.getText().toString();
        String password = etNewPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        String code = etInvitationCode.getText().toString();
        String cardNo = etCardNo.getText().toString();
        String address = etAddress.getText().toString();

        if (rbYes.isChecked()) {
            volunteer = true;
        } else if (rbNo.isChecked()) {
            volunteer = false;
        }
        tilNickname.setErrorEnabled(true);
        tilNewPassword.setErrorEnabled(true);
        tilConfirmPassword.setErrorEnabled(true);
        tilCardNo.setErrorEnabled(true);
        tilAddress.setErrorEnabled(true);
        if (validateNickname(nickname) && validatePassword(password, confirmPassword)
                && validateOther(cardNo, cityCode, areaCode, address)) {
            mPresenter.registerSubmit(StringUtils.stringUTF8(nickname), cardNo, cityCode, areaCode,
                    address, mobile, ArmsUtils.encodeToMD5(password), "123456", volunteer);
        }
    }

}
