package com.geek.huixiaoer.mvp.supermarket.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.widget.adapter.DefaultStatePagerAdapter;
import com.geek.huixiaoer.mvp.supermarket.contract.ShopContract;
import com.geek.huixiaoer.mvp.supermarket.di.component.DaggerShopComponent;
import com.geek.huixiaoer.mvp.supermarket.di.module.ShopModule;
import com.geek.huixiaoer.mvp.supermarket.presenter.ShopPresenter;
import com.geek.huixiaoer.mvp.supermarket.ui.fragment.GoodsFragment;
import com.geek.huixiaoer.storage.entity.Category;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ShopActivity extends BaseActivity<ShopPresenter> implements ShopContract.View {

    @BindView(R.id.title)
    TextView title;
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
    public void setViewPager(List<Category> categoryList) {
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        if (categoryList != null) {
            GoodsFragment goodsFragment;
            for (int i = 0; i < categoryList.size(); i++) {
                Category category = categoryList.get(i);
                //设置TabLayout的模式
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                goodsFragment = new GoodsFragment();
                Bundle args = new Bundle();
                args.putInt("id", category.getId());
                goodsFragment.setArguments(args);
                fragmentList.add(goodsFragment);
                titles.add(category.getName());

            }
            DefaultStatePagerAdapter pagerAdapter = new DefaultStatePagerAdapter(
                    getSupportFragmentManager(), fragmentList, titles);
            viewPager.setAdapter(pagerAdapter);
            //TabLayout加载viewpager
            tabLayout.setupWithViewPager(viewPager);
        }
    }

}
