package com.geek.huixiaoer.mvp.common.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.utils.RegexUtils;
import com.geek.huixiaoer.common.utils.StringUtils;
import com.geek.huixiaoer.common.widget.CircleProgressDialog;
import com.geek.huixiaoer.mvp.common.contract.RegisterContract;
import com.geek.huixiaoer.mvp.common.di.component.DaggerRegisterComponent;
import com.geek.huixiaoer.mvp.common.di.module.RegisterModule;
import com.geek.huixiaoer.mvp.common.presenter.RegisterPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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

    private String mobile;

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
        tilNickname.setErrorEnabled(true);
        tilNewPassword.setErrorEnabled(true);
        tilConfirmPassword.setErrorEnabled(true);
        if (validateNickname(nickname) && validatePassword(password, confirmPassword)) {
            mPresenter.registerSubmit(StringUtils.stringUTF8(nickname), mobile, ArmsUtils.encodeToMD5(password), code);
        }
    }

}
