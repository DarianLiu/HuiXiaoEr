package com.geek.huixiaoer.mvp.housewifery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.widget.autoviewpager.AutoScrollViewPager;
import com.geek.huixiaoer.common.widget.recyclerview.GridSpacingItemDecoration;
import com.geek.huixiaoer.mvp.housewifery.contract.HomeServicesContract;
import com.geek.huixiaoer.mvp.housewifery.di.component.DaggerHomeServicesComponent;
import com.geek.huixiaoer.mvp.housewifery.di.module.HomeServicesModule;
import com.geek.huixiaoer.mvp.housewifery.presenter.HomeServicesPresenter;
import com.geek.huixiaoer.mvp.housewifery.ui.adapter.ServiceAdapter;
import com.geek.huixiaoer.storage.entity.BannerBean;
import com.geek.huixiaoer.storage.entity.shop.GoodsBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 家政服务项目列表
 */
public class HomeServicesActivity extends BaseActivity<HomeServicesPresenter> implements HomeServicesContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.autoScrollViewPager)
    AutoScrollViewPager autoScrollViewPager;
    @BindView(R.id.autoScrollIndicator)
    LinearLayout autoScrollIndicator;
    @BindView(R.id.rl_banner)
    RelativeLayout rlBanner;

    private ServiceAdapter mAdapter;
    private List<GoodsBean> mHomeServices = new ArrayList<>();

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerHomeServicesComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .homeServicesModule(new HomeServicesModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_home_services; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        tvToolbarTitle.setText(R.string.title_home_service);

        setBannerHeight();
        mPresenter.getBanner();
        initRefreshLayout();
        initRecyclerView();
    }


    /**
     * 初始化刷新控件
     */
    private void initRefreshLayout() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(HomeServicesActivity.this));
        refreshLayout.setDisableContentWhenRefresh(true);//刷新的时候禁止列表的操作
        refreshLayout.setOnRefreshListener(refreshlayout ->
                mPresenter.homeServiceList()
        );

        // 自动刷新
        refreshLayout.autoRefresh();
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));
        recyclerView.setHasFixedSize(true);
        mAdapter = new ServiceAdapter(mHomeServices);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void updateBanner(List<BannerBean> bannerBean) {
        mBannerBeen = bannerBean;
        addScrollImage(bannerBean.size());
        initAutoScrollViewPager();
    }

    @Override
    public void updateView(List<GoodsBean> homeServices) {
        mHomeServices.clear();
        mHomeServices.addAll(homeServices);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener((view, viewType, data, position) ->
                RongIM.getInstance().startConversation(HomeServicesActivity.this,
                        Conversation.ConversationType.PRIVATE, "002", String.valueOf(homeServices.get(position).getId())));
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
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter = null;
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
    public void endRefresh() {
        refreshLayout.finishRefresh();
    }


}
