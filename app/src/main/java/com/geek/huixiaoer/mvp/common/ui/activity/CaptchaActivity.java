package com.geek.huixiaoer.mvp.common.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.utils.RegexUtils;
import com.geek.huixiaoer.mvp.common.contract.CaptchaContract;
import com.geek.huixiaoer.mvp.common.di.component.DaggerCaptchaComponent;
import com.geek.huixiaoer.mvp.common.di.module.CaptchaModule;
import com.geek.huixiaoer.mvp.common.presenter.CaptchaPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.text.MessageFormat;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class CaptchaActivity extends BaseActivity<CaptchaPresenter> implements CaptchaContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etMobile)
    EditText etMobile;
    @BindView(R.id.etCaptcha)
    EditText etCaptcha;
    @BindView(R.id.tvSend)
    TextView tvSend;
    @BindView(R.id.btn_next_step)
    Button btnNextStep;

    @BindString(R.string.btn_count_down)
    String count_down;
    @BindString(R.string.error_mobile_null)
    String error_mobile_null;
    @BindString(R.string.error_captcha_null)
    String error_captcha_null;
    @BindString(R.string.error_mobile_regex)
    String error_mobile_regex;
    @BindString(R.string.error_captcha)
    String error_captcha;

    private int type;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerCaptchaComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .captchaModule(new CaptchaModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_captcha; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        tvToolbarTitle.setText(R.string.title_get_captcha);

        type = getIntent().getIntExtra(Constants.INTENT_TYPE, 0);
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
    public void countDown(Long time) {
        if (time != 0) {
            tvSend.setEnabled(false);
            tvSend.setText(MessageFormat.format("{0}{1}", time, count_down));
        } else {
            tvSend.setEnabled(true);
            tvSend.setText(R.string.btn_resend);
        }
    }

    @OnClick({R.id.tvSend, R.id.btn_next_step})
    public void onViewClicked(View view) {
        String mobile = etMobile.getText().toString();
        String captcha = etCaptcha.getText().toString();
        switch (view.getId()) {
            case R.id.tvSend:
                if (TextUtils.isEmpty(mobile)) {
                    showMessage(error_mobile_null);
                } else if (!RegexUtils.isMobileSimple(mobile)) {
                    showMessage(error_mobile_regex);
                } else {
                    mPresenter.sendCaptcha(mobile, type);
                }
                break;
            case R.id.btn_next_step:
                if (TextUtils.isEmpty(captcha)) {
                    showMessage(error_captcha_null);
                } else if (captcha.length() != 6) {
                    showMessage(error_captcha);
                } else {
                    mPresenter.checkCaptcha(mobile, captcha);
                }
                break;
        }
    }
}
