package com.geek.huixiaoer.mvp.housewifery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.geek.huixiaoer.mvp.housewifery.di.component.DaggerConversationComponent;
import com.geek.huixiaoer.mvp.housewifery.di.module.ConversationModule;
import com.geek.huixiaoer.mvp.housewifery.contract.ConversationContract;
import com.geek.huixiaoer.mvp.housewifery.presenter.ConversationPresenter;

import com.geek.huixiaoer.R;


import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;

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
            if (!fragment.onBackPressed()) {
                String ryId =  fragment.getTargetId();
                if (TextUtils.isEmpty(ryId)){

                }else {
                    mPresenter.setServiceF(ryId);
                }
            }
        });
        tvToolbarTitle.setText(R.string.title_home_service);


    }

    @Override
    public void onBackPressed() {
        if (!fragment.onBackPressed()) {
            String ryId =  fragment.getTargetId();
            if (TextUtils.isEmpty(ryId)){

            }else {
                mPresenter.setServiceF(ryId);
            }
        }
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
