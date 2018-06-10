package com.geek.huixiaoer.mvp.housewifery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.mvp.housewifery.presenter.HomeServicesPresenter;
import com.geek.huixiaoer.storage.entity.UserBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.geek.huixiaoer.mvp.housewifery.di.component.DaggerConversationComponent;
import com.geek.huixiaoer.mvp.housewifery.di.module.ConversationModule;
import com.geek.huixiaoer.mvp.housewifery.contract.ConversationContract;
import com.geek.huixiaoer.mvp.housewifery.presenter.ConversationPresenter;

import com.geek.huixiaoer.R;
import com.jess.arms.utils.DataHelper;


import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ConversationActivity extends BaseActivity<ConversationPresenter> implements ConversationContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ConversationFragment fragment;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerConversationComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .conversationModule(new ConversationModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_conversation; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            setCustomerFee();
        });
        tvToolbarTitle.setText(R.string.title_home_service);

        /*设置当前用户信息， @param userInfo 当前用户信息*/
        RongIM.setConnectionStatusListener(new MyConnectionStatusListener());
        UserBean userBean = DataHelper.getDeviceData(this, Constants.SP_USER_INFO);
        RongIM.getInstance().setCurrentUserInfo(new UserInfo(userBean.getRyId(), userBean.getUserInfo().getNickname(), null));

        /* 设置消息体内是否携带用户信息*/
        RongIM.getInstance().setMessageAttachedUserInfo(true);

    }


    private void setCustomerFee() {
        String ryId = fragment.getTargetId();
        if (TextUtils.isEmpty(ryId)) {
            ryId = HomeServicesPresenter.ryUserId;
        }
        if (!TextUtils.isEmpty(ryId)) {
            Timber.e("=======", "=====  conversation setServiceF ryId： " + ryId);
            mPresenter.setServiceF(ryId);
        }
    }
    private class MyConnectionStatusListener implements RongIMClient.ConnectionStatusListener {

        @Override
        public void onChanged(ConnectionStatus connectionStatus) {

            switch (connectionStatus) {

                case CONNECTED://连接成功。

                    break;
                case DISCONNECTED://断开连接。

                    break;
                case CONNECTING://连接中。

                    break;
                case NETWORK_UNAVAILABLE://网络不可用。

                    break;
                case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线

                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        Log.e("======", "==== onBackPressed");
        if (!fragment.onBackPressed()) {
            setCustomerFee();
//            String ryId = fragment.getTargetId();
//            if (TextUtils.isEmpty(ryId)) {
//
//            } else {
//                Log.e("=======", "=====  conversation setServiceF ryId： " + ryId);
//                mPresenter.setServiceF(ryId);
//            }
        }
    }

    @Override
    protected void onDestroy() {
        setCustomerFee();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
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
}
