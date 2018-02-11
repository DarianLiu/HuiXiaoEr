package com.geek.huixiaoer.mvp.supermarket.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.widget.adapter.DefaultStatePagerAdapter;
import com.geek.huixiaoer.mvp.supermarket.contract.ShopContract;
import com.geek.huixiaoer.mvp.supermarket.di.component.DaggerShopComponent;
import com.geek.huixiaoer.mvp.supermarket.di.module.ShopModule;
import com.geek.huixiaoer.mvp.supermarket.presenter.ShopPresenter;
import com.geek.huixiaoer.mvp.supermarket.ui.fragment.GoodsListFragment;
import com.geek.huixiaoer.storage.entity.CategoryBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ShopActivity extends BaseActivity<ShopPresenter> implements ShopContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerShopComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .shopModule(new ShopModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_viewpager; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbarTitle.setText(R.string.title_shop);

        mPresenter.getGoodsCategorys();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_searchview, menu);
        return super.onCreateOptionsMenu(menu);
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
    public void setViewPager(List<CategoryBean> categoryBeanList) {
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        if (categoryBeanList != null) {
            GoodsListFragment goodsListFragment;
            for (int i = 0; i < categoryBeanList.size(); i++) {
                CategoryBean categoryBean = categoryBeanList.get(i);
                //设置TabLayout的模式
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                goodsListFragment = new GoodsListFragment();
                Bundle args = new Bundle();
                args.putInt("id", categoryBean.getId());
                goodsListFragment.setArguments(args);
                fragmentList.add(goodsListFragment);
                titles.add(categoryBean.getName());

            }
            DefaultStatePagerAdapter pagerAdapter = new DefaultStatePagerAdapter(
                    getSupportFragmentManager(), fragmentList, titles);
            viewPager.setAdapter(pagerAdapter);
            //TabLayout加载viewpager
            tabLayout.setupWithViewPager(viewPager);
        }
    }

}
