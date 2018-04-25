package com.geek.huixiaoer.mvp.recycle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.mvp.recycle.contract.MemberRankingContract;
import com.geek.huixiaoer.mvp.recycle.di.component.DaggerMemberRankingComponent;
import com.geek.huixiaoer.mvp.recycle.di.module.MemberRankingModule;
import com.geek.huixiaoer.mvp.recycle.presenter.MemberRankingPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 会员积分排行榜
 */
public class MemberRankingActivity extends BaseActivity<MemberRankingPresenter> implements MemberRankingContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMemberRankingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .memberRankingModule(new MemberRankingModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_member_ranking; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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
