package com.geek.huixiaoer.mvp.recycle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.widget.autoviewpager.AutoScrollViewPager;
import com.geek.huixiaoer.mvp.common.ui.activity.CaptchaActivity;
import com.geek.huixiaoer.mvp.common.ui.activity.LoginActivity;
import com.geek.huixiaoer.mvp.common.ui.activity.RegisterActivity;
import com.geek.huixiaoer.mvp.person.ui.activity.MessageListActivity;
import com.geek.huixiaoer.mvp.recycle.contract.RecycleHomeContract;
import com.geek.huixiaoer.mvp.recycle.di.component.DaggerRecycleHomeComponent;
import com.geek.huixiaoer.mvp.recycle.di.module.RecycleHomeModule;
import com.geek.huixiaoer.mvp.recycle.presenter.RecycleHomePresenter;
import com.geek.huixiaoer.storage.entity.BannerBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class RecycleHomeActivity extends BaseActivity<RecycleHomePresenter> implements RecycleHomeContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.autoScrollViewPager)
    AutoScrollViewPager autoScrollViewPager;
    @BindView(R.id.autoScrollIndicator)
    LinearLayout autoScrollIndicator;
    @BindView(R.id.rl_banner)
    RelativeLayout rlBanner;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.loginBtn)
    Button loginBtn;
    @BindView(R.id.btn_forum)
    Button btnForum;
    @BindView(R.id.btn_integral)
    Button btnIntegral;
    @BindView(R.id.btn_blacklist)
    Button btnBlacklist;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRecycleHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .recycleHomeModule(new RecycleHomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_recycle_home; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        tvToolbarTitle.setText(R.string.module_environment_protect);

        setBannerHeight();

        if (!TextUtils.isEmpty(DataHelper.getStringSF(this, Constants.SP_TOKEN))) {
            btnRegister.setVisibility(View.GONE);
            loginBtn.setVisibility(View.GONE);
        }
        mPresenter.getBanner();
    }

    /**
     * 设置banner控件的高度
     */
    private void setBannerHeight() {
        int screenWidth = ArmsUtils.getScreenWidth(this);
        int height = (int) (screenWidth * 0.53);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(screenWidth, height);
        autoScrollViewPager.setLayoutParams(params);
    }

    /**
     * 更新轮播图
     *
     * @param bannerBean 轮播图列表
     */
    @Override
    public void updateBanner(List<BannerBean> bannerBean) {
        mBannerBeen = bannerBean;
        addScrollImage(bannerBean.size());
        initAutoScrollViewPager();
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

    //轮播图底部滑动图片
    private ArrayList<ImageView> mScrollImageViews = new ArrayList<>();
    //轮播图图片
    private List<BannerBean> mBannerBeen = new ArrayList<>();
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
            ImageView iv = new ImageView(this);
            iv.setPadding(10, 0, 10, 20);
            if (i != 0) {
                iv.setImageResource(R.drawable.icon_indicator_normal);
            } else {
                iv.setImageResource(R.drawable.icon_indicator_selected);
            }
            iv.setLayoutParams(new ViewGroup.LayoutParams(40, 40));
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

    @Override
    public void onResume() {
        super.onResume();
        autoScrollViewPager.startAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        autoScrollViewPager.stopAutoScroll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPagerAdapter = null;
    }

    @Override
    public void killMyself() {
        finish();
    }

    @OnClick({R.id.loginBtn, R.id.btn_register, R.id.btn_forum, R.id.btn_integral, R.id.btn_blacklist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                launchActivity(new Intent(RecycleHomeActivity.this,LoginActivity.class));
                break;
            case R.id.btn_register:
                launchActivity(new Intent(RecycleHomeActivity.this,RegisterActivity.class));
//                launchActivity(new Intent(RecycleHomeActivity.this, CaptchaActivity.class));
                break;
            case R.id.btn_forum:
                launchActivity(new Intent(RecycleHomeActivity.this,RecycleListActivity.class));
                break;
            case R.id.btn_integral:
                break;
            case R.id.btn_blacklist:
                break;
        }
    }
}
