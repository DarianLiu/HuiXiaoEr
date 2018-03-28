package com.geek.huixiaoer.mvp.common.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.widget.autoviewpager.AutoScrollViewPager;
import com.geek.huixiaoer.mvp.common.contract.MainContract;
import com.geek.huixiaoer.mvp.common.di.component.DaggerMainComponent;
import com.geek.huixiaoer.mvp.common.di.module.MainModule;
import com.geek.huixiaoer.mvp.common.presenter.MainPresenter;
import com.geek.huixiaoer.mvp.housewifery.ui.activity.HomeServicesActivity;
import com.geek.huixiaoer.mvp.person.ui.activity.MyShopOrderActivity;
import com.geek.huixiaoer.mvp.recycle.ui.activity.RecycleListActivity;
import com.geek.huixiaoer.mvp.supermarket.ui.activity.ShopActivity;
import com.geek.huixiaoer.mvp.supermarket.ui.activity.ShoppingCartActivity;
import com.geek.huixiaoer.storage.entity.BannerBean;
import com.geek.huixiaoer.storage.entity.UserBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View,
        NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.tv_toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.autoScrollViewPager)
    AutoScrollViewPager autoScrollViewPager;
    @BindView(R.id.autoScrollIndicator)
    LinearLayout autoScrollIndicator;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.iv_recycle)
    ImageView ivRecycle;
    @BindView(R.id.iv_housewifery)
    ImageView ivHousewifery;
    @BindView(R.id.iv_supermarket)
    ImageView ivSupermarket;
    @BindView(R.id.iv_dinning)
    ImageView ivDinning;

    //轮播图底部滑动图片
    private ArrayList<ImageView> mScrollImageViews = new ArrayList<>();
    //轮播图图片
    private List<BannerBean> mBannerBeen = new ArrayList<>();

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText(R.string.app_name);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        setBannerHeight();
        initNavigationView();

        //获取轮播图
        mPresenter.getBanner();

        tvNotice.setSelected(true);

        RongIM.connect("xkvZfF3zvY//gwZCOerYDS/mvXZv/KNkR8ZJyEKI9cfUyZ1DuYjwlfAxq9vCrgF7ND6pM9jyzfw=", null);
    }

    /**
     * 设置banner控件的高度
     */
    private void setBannerHeight() {
        int screenWidth = ArmsUtils.getScreenWidth(MainActivity.this);
        int height = (int) (screenWidth * 0.53);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(screenWidth, height);
        autoScrollViewPager.setLayoutParams(params);
    }

    /**
     * 初始化NavigationView
     */
    private void initNavigationView() {
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        View navigationHeaderView = navigationView.getHeaderView(0);
        ImageView ivHeader = navigationHeaderView.findViewById(R.id.iv_user_head);
        ImageView ivSet = navigationHeaderView.findViewById(R.id.iv_set);
        TextView tvName = navigationHeaderView.findViewById(R.id.tv_user_name);
        TextView tvSign = navigationHeaderView.findViewById(R.id.tv_user_sign);

        UserBean userBean = DataHelper.getDeviceData(MainActivity.this, Constants.SP_USER_INFO);
        if (userBean != null) {
            GlideArms.with(MainActivity.this).load(userBean.getHeadimgurl()).circleCrop()
                    .error(R.drawable.icon_user_head).into(ivHeader);
            tvName.setText(userBean.getUserInfo().getNickname() == null ?
                    userBean.getUserInfo().getUsername() : userBean.getUserInfo().getNickname());
            tvSign.setText(userBean.getUserInfo().getUsersign());
        }
        ivHeader.setOnClickListener(v -> {
            if (userBean == null) {
                launchActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        tvName.setOnClickListener(v -> {
            if (userBean == null) {
                launchActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        ivSet.setOnClickListener(v -> {
//            ArmsUtils.makeText(MainActivity.this, "设置");
            DataHelper.clearShareprefrence(MainActivity.this);
        });
    }

    @Override
    public void setBanner(List<BannerBean> bannerBeen) {
        mBannerBeen = bannerBeen;
        addScrollImage(bannerBeen.size());
        initAutoScrollViewPager();
    }

    /**
     * 初始化轮播图控件
     */
    private void initAutoScrollViewPager() {
        autoScrollViewPager.setAdapter(mPagerAdapter);

        // viewPagerIndicator.setViewPager(autoScrollViewPager);
        // viewPagerIndicator.setSnap(true);

        autoScrollViewPager.setScrollFactgor(10); // 控制滑动速度
//        autoScrollViewPager.setOffscreenPageLimit(6); //设置缓存屏数
        autoScrollViewPager.startAutoScroll(3000);  //设置间隔时间

        autoScrollViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                showSelectScrollImage(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        autoScrollViewPager.startAutoScroll();
    }

    /**
     * 当前滑动的轮播图对应底部的标识
     *
     * @param position 当前位置
     */
    private void showSelectScrollImage(int position) {
        if (position < 0 || position >= mScrollImageViews.size()) return;
        if (mScrollImageViews != null) {
            for (ImageView iv : mScrollImageViews) {
                iv.setImageResource(R.drawable.icon_indicator_normal);
            }
            mScrollImageViews.get(position).setImageResource(R.drawable.icon_indicator_selected);
        }
    }

    /**
     * 轮播图底部的滑动的下划线
     *
     * @param size 轮播图数量
     */
    private void addScrollImage(int size) {
        autoScrollIndicator.removeAllViews();
        mScrollImageViews.clear();

        for (int i = 0; i < size; i++) {
            ImageView iv = new ImageView(this.getApplicationContext());
            iv.setPadding(10, 0, 10, 20);
            if (i != 0) {
                iv.setImageResource(R.drawable.icon_indicator_normal);
            } else {
                iv.setImageResource(R.drawable.icon_indicator_selected);
            }
            iv.setLayoutParams(new ViewGroup.LayoutParams(35, 35));
            autoScrollIndicator.addView(iv);// 将图片加到一个布局里
            mScrollImageViews.add(iv);
        }
    }

    /**
     * 轮播图适配器
     */
    PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return mScrollImageViews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
            View view = getLayoutInflater().inflate(R.layout.include_image, null);
            ImageView ivBanner = view.findViewById(R.id.imageView);

            GlideArms.with(ivBanner.getContext()).load(mBannerBeen.get(position).getPath())
                    .centerCrop().error(R.drawable.icon_banner_default).into(ivBanner);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    };

//    @OnClick(R.id.fab)
//    public void fabOnClick() {
//        showMessage("Replace with your own action");
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String token = DataHelper.getStringSF(MainActivity.this, Constants.SP_TOKEN);
        if (TextUtils.isEmpty(token)) {
            launchActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else {
            switch (item.getItemId()) {
                case R.id.nav_shopping_cart:
                    launchActivity(new Intent(MainActivity.this, ShoppingCartActivity.class));
                    break;
                case R.id.nav_coupon:
                    break;
                case R.id.nav_order:
                    launchActivity(new Intent(MainActivity.this, MyShopOrderActivity.class));
                    break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
    public void onBackPressed() {
        super.onBackPressed();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void killMyself() {
        finish();
    }

    @OnClick({R.id.iv_supermarket, R.id.iv_recycle, R.id.iv_housewifery, R.id.iv_dinning})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_supermarket:
                launchActivity(new Intent(MainActivity.this, ShopActivity.class));
                break;
            case R.id.iv_recycle:
                launchActivity(new Intent(MainActivity.this, RecycleListActivity.class));
                break;
            case R.id.iv_housewifery:
                launchActivity(new Intent(MainActivity.this, HomeServicesActivity.class));
                break;
            case R.id.iv_dinning:
//                launchActivity(new Intent(MainActivity.this, DinningActivity.class));
                break;
//            case R.id.linear_member_login:
//                launchActivity(new Intent(MainActivity.this, LoginActivity.class));
//                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoScrollViewPager.startAutoScroll();
    }

    @Override
    protected void onStop() {
        super.onStop();
        autoScrollViewPager.stopAutoScroll();
    }
}
