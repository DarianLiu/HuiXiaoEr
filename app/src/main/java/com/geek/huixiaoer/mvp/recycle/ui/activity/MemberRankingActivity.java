package com.geek.huixiaoer.mvp.recycle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.widget.adapter.DefaultStatePagerAdapter;
import com.geek.huixiaoer.mvp.person.ui.fragment.ShopOrderFragment;
import com.geek.huixiaoer.mvp.recycle.contract.MemberRankingContract;
import com.geek.huixiaoer.mvp.recycle.di.component.DaggerMemberRankingComponent;
import com.geek.huixiaoer.mvp.recycle.di.module.MemberRankingModule;
import com.geek.huixiaoer.mvp.recycle.presenter.MemberRankingPresenter;
import com.geek.huixiaoer.mvp.recycle.ui.fragment.RankFragment;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 会员积分排行榜
 */
public class MemberRankingActivity extends BaseActivity<MemberRankingPresenter> implements MemberRankingContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

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
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        tvToolbarTitle.setText(R.string.title_member_rank);

        initViewPager();
    }


    /**
     * 初始化ViewPager
     */
    private void initViewPager() {

        List<Fragment> list_fragment = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        //初始化fragment
        RankFragment fragmentWeight = new RankFragment();
        RankFragment fragmentPoint = new RankFragment();
        list_fragment.add(fragmentWeight);
        list_fragment.add(fragmentPoint);
        titles.add("按重量");
        titles.add("按积分");


        //给fragment绑定参数
        for (int i = 0; i < 2; i++) {
            //为TabLayout添加tab内容
            Bundle args = new Bundle();
            args.putInt("order", i);
            list_fragment.get(i).setArguments(args);
        }

        DefaultStatePagerAdapter pagerAdapter = new DefaultStatePagerAdapter(
                getSupportFragmentManager(), list_fragment, titles);
        viewPager.setAdapter(pagerAdapter);
        //TabLayout加载viewpager
        tabLayout.setupWithViewPager(viewPager);
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
