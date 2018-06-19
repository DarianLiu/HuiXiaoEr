package com.geek.huixiaoer.mvp.person.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.config.EventBusTags;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.widget.OptionView;
import com.geek.huixiaoer.mvp.common.ui.activity.CaptchaActivity;
import com.geek.huixiaoer.mvp.person.contract.TabMineContract;
import com.geek.huixiaoer.mvp.person.di.component.DaggerTabMineComponent;
import com.geek.huixiaoer.mvp.person.di.module.TabMineModule;
import com.geek.huixiaoer.mvp.person.presenter.TabMinePresenter;
import com.geek.huixiaoer.mvp.person.ui.activity.UpdateMobileActivity;
import com.geek.huixiaoer.storage.entity.UserBean;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;

import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class TabMineFragment extends BaseFragment<TabMinePresenter> implements TabMineContract.View {

    @BindView(R.id.option_nickname)
    OptionView optionNickname;
    @BindView(R.id.option_password)
    OptionView optionPassword;
    @BindView(R.id.option_mobile)
    OptionView optionMobile;
    @BindView(R.id.btn_login_out)
    Button btnLoginOut;
    @BindView(R.id.ll_error)
    LinearLayout llError;

    public static TabMineFragment newInstance() {
        return new TabMineFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTabMineComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .tabMineModule(new TabMineModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_mine, container, false);
    }

    @Subscriber(tag = EventBusTags.ISLogin)
    public void updateLoginState(boolean isLogin) {
        if (isLogin) {
            updateView();
        } else {
            hideAllView();
        }
    }

    @Subscriber(tag = EventBusTags.mobileUpdate)
    public void updateMobile(String mobile) {
        if (!TextUtils.isEmpty(mobile)) {
            optionMobile.setRightText(mobile);
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        optionPassword.setRightText("修改");
        if (TextUtils.isEmpty(DataHelper.getStringSF(getActivity(), Constants.SP_TOKEN))) {
            hideAllView();
        } else {
            updateView();
        }
    }


    private void updateView() {
        UserBean userBean = DataHelper.getDeviceData(getActivity(), Constants.SP_USER_INFO);
        optionNickname.setRightText(TextUtils.isEmpty(userBean.getUserInfo().getNickname()) ?
                userBean.getUserInfo().getNickname() : userBean.getUserInfo().getUsername());
        optionMobile.setRightText(userBean.getMobile());
        optionNickname.setVisibility(View.VISIBLE);
        optionPassword.setVisibility(View.VISIBLE);
        optionMobile.setVisibility(View.VISIBLE);
        btnLoginOut.setVisibility(View.VISIBLE);
        llError.setVisibility(View.GONE);
    }

    private void hideAllView() {
        optionNickname.setVisibility(View.GONE);
        optionPassword.setVisibility(View.GONE);
        optionMobile.setVisibility(View.GONE);
        btnLoginOut.setVisibility(View.GONE);
        llError.setVisibility(View.VISIBLE);
    }

    @Override
    public void setData(@Nullable Object data) {

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

    }

    @OnClick({R.id.option_nickname, R.id.option_password, R.id.option_mobile, R.id.btn_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.option_nickname:
//                launchActivity(new Intent(getActivity(), CaptchaActivity.class));
                break;
            case R.id.option_password:
                launchActivity(new Intent(getActivity(), CaptchaActivity.class));
                break;
            case R.id.option_mobile:
                launchActivity(new Intent(getActivity(), UpdateMobileActivity.class));
                break;
            case R.id.btn_login_out:
                DataHelper.clearShareprefrence(getActivity());
                RongIM.getInstance().disconnect();
                hideAllView();
                break;
        }
    }
}
