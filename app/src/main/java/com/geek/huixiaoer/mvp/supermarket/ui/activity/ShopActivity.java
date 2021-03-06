package com.geek.huixiaoer.mvp.supermarket.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.widget.adapter.DefaultStatePagerAdapter;
import com.geek.huixiaoer.common.widget.autoviewpager.AutoScrollViewPager;
import com.geek.huixiaoer.common.widget.dialog.CircleProgressDialog;
import com.geek.huixiaoer.mvp.common.ui.activity.LoginActivity;
import com.geek.huixiaoer.mvp.common.ui.activity.WebViewActivity;
import com.geek.huixiaoer.mvp.supermarket.contract.ShopContract;
import com.geek.huixiaoer.mvp.supermarket.di.component.DaggerShopComponent;
import com.geek.huixiaoer.mvp.supermarket.di.module.ShopModule;
import com.geek.huixiaoer.mvp.supermarket.presenter.ShopPresenter;
import com.geek.huixiaoer.mvp.supermarket.ui.fragment.GoodsListFragment;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.entity.BannerBean;
import com.geek.huixiaoer.storage.entity.MessageBean;
import com.geek.huixiaoer.storage.entity.shop.CategoryBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;

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
    @BindView(R.id.autoScrollViewPager)
    AutoScrollViewPager autoScrollViewPager;
    @BindView(R.id.autoScrollIndicator)
    LinearLayout autoScrollIndicator;
    @BindView(R.id.rl_banner)
    RelativeLayout rlBanner;
    @BindView(R.id.tv_message)
    TextView tvMessage;

    private CircleProgressDialog loadingDialog;

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
        return R.layout.activity_shop; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbarTitle.setText(R.string.title_shop);

        tvMessage.setHorizontallyScrolling(true);
        tvMessage.setSelected(true);

        mPresenter.getBanner();
        mPresenter.getGoodsCategorys();
        mPresenter.messageList(10);
        setBannerHeight();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_searchview, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shopping_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_shopping_cart:
                String token = DataHelper.getStringSF(ShopActivity.this, Constants.SP_TOKEN);
                if (TextUtils.isEmpty(token)) {
                    launchActivity(new Intent(ShopActivity.this, LoginActivity.class));
                } else {
                    launchActivity(new Intent(ShopActivity.this, ShoppingCartActivity.class));
                }
                break;
        }
        return true;
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
            view.setOnClickListener(v -> {
                Intent intent = new Intent(ShopActivity.this, WebViewActivity.class);
                intent.putExtra("title", mBannerBeen.get(position).getTitle());
                intent.putExtra("url", mBannerBeen.get(position).getUrl());
                startActivity(intent);
            });
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
        if (loadingDialog == null) {
            loadingDialog = new CircleProgressDialog.Builder(this).create();
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
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
    public void updateBanner(List<BannerBean> bannerBean) {
        mBannerBeen = bannerBean;
        addScrollImage(bannerBean.size());
        initAutoScrollViewPager();
    }

    @Override
    public void setViewPager(List<CategoryBean> categoryBeanList) {
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        if (categoryBeanList != null) {
            GoodsListFragment goodsListFragment;
//            for (int i = 0; i < categoryBeanList.size(); i++) {
            for (int i = 0; i < 1; i++) {
                CategoryBean categoryBean = categoryBeanList.get(i);
                goodsListFragment = new GoodsListFragment();
                Bundle args = new Bundle();
                args.putInt("id", categoryBean.getId());
                goodsListFragment.setArguments(args);
                fragmentList.add(goodsListFragment);
                titles.add(categoryBean.getName());

            }
            //设置TabLayout的模式
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            DefaultStatePagerAdapter pagerAdapter = new DefaultStatePagerAdapter(
                    getSupportFragmentManager(), fragmentList, titles);
            viewPager.setAdapter(pagerAdapter);
            //TabLayout加载viewpager
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public void setMessageList(BaseArrayData<MessageBean> messageList) {
        StringBuilder stringBuffer = new StringBuilder();
        int size = messageList.getPageData().size();
        for (int i = 0; i < size; i++) {
            MessageBean message = messageList.getPageData().get(i);
            stringBuffer.append("【").append(message.getTitle()).append("】").append(message.getContent());
            if (i < size - 1) {
                stringBuffer.append("      ");
            }
        }
        tvMessage.setText(stringBuffer.toString());
    }

}
