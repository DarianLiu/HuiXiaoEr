package com.geek.huixiaoer.mvp.common.ui.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.geek.huixiaoer.R;
import com.geek.huixiaoer.api.APIs;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.utils.DateUtil;
import com.geek.huixiaoer.common.widget.OptionView;
import com.geek.huixiaoer.common.widget.autoviewpager.AutoScrollViewPager;
import com.geek.huixiaoer.mvp.common.contract.TabHomeContract;
import com.geek.huixiaoer.mvp.common.di.component.DaggerTabHomeComponent;
import com.geek.huixiaoer.mvp.common.di.module.TabHomeModule;
import com.geek.huixiaoer.mvp.common.presenter.TabHomePresenter;
import com.geek.huixiaoer.mvp.dinner.ui.activity.DinnerActivity;
import com.geek.huixiaoer.mvp.housewifery.ui.activity.HomeServicesActivity;
import com.geek.huixiaoer.mvp.recycle.ui.activity.RecycleHomeActivity;
import com.geek.huixiaoer.mvp.recycle.ui.activity.RecycleListActivity;
import com.geek.huixiaoer.mvp.supermarket.ui.activity.GoodsDetailActivity;
import com.geek.huixiaoer.mvp.supermarket.ui.activity.ShopActivity;
import com.geek.huixiaoer.storage.entity.BannerBean;
import com.geek.huixiaoer.storage.entity.recycle.ArticleBean;
import com.geek.huixiaoer.storage.entity.shop.GoodsBean;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class TabHomeFragment extends BaseFragment<TabHomePresenter> implements TabHomeContract.View {

    @BindView(R.id.autoScrollViewPager)
    AutoScrollViewPager autoScrollViewPager;
    @BindView(R.id.autoScrollIndicator)
    LinearLayout autoScrollIndicator;
    @BindView(R.id.rl_banner)
    RelativeLayout rlBanner;
    @BindView(R.id.tv_environment_protect)
    TextView tvEnvironmentProtect;
    @BindView(R.id.tv_help_you)
    TextView tvHelpYou;
    @BindView(R.id.tv_discount_store)
    TextView tvDiscountStore;
    @BindView(R.id.tv_specialty)
    TextView tvSpecialty;
    @BindView(R.id.iv_goods_one)
    ImageView ivGoodsOne;
    @BindView(R.id.tv_title_one)
    TextView tvTitleOne;
    @BindView(R.id.tv_content_one)
    TextView tvContentOne;
    @BindView(R.id.tv_discount_price_one)
    TextView tvDiscountPriceOne;
    @BindView(R.id.tv_cost_price_one)
    TextView tvCostPriceOne;
    @BindView(R.id.iv_goods_two)
    ImageView ivGoodsTwo;
    @BindView(R.id.tv_title_two)
    TextView tvTitleTwo;
    @BindView(R.id.tv_content_two)
    TextView tvContentTwo;
    @BindView(R.id.tv_discount_price_two)
    TextView tvDiscountPriceTwo;
    @BindView(R.id.tv_cost_price_two)
    TextView tvCostPriceTwo;
    @BindView(R.id.iv_dish_one)
    ImageView ivDishOne;
    @BindView(R.id.iv_dish_two)
    ImageView ivDishTwo;
    @BindView(R.id.iv_dish_three)
    ImageView ivDishThree;
    @BindView(R.id.iv_dish_four)
    ImageView ivDishFour;
    @BindView(R.id.iv_dish_five)
    ImageView ivDishFive;
    @BindView(R.id.iv_dish_six)
    ImageView ivDishSix;
    @BindView(R.id.tv_article_name_first)
    TextView tvArticleNameFirst;
    @BindView(R.id.tv_article_hits_first)
    TextView tvArticleHitsFirst;
    @BindView(R.id.tv_article_name_second)
    TextView tvArticleNameSecond;
    @BindView(R.id.tv_article_hits_second)
    TextView tvArticleHitsSecond;
    @BindView(R.id.tv_article_name_third)
    TextView tvArticleNameThird;
    @BindView(R.id.tv_article_hits_third)
    TextView tvArticleHitsThird;
    @BindView(R.id.rl_hotSport_first)
    RelativeLayout rlHotSportFirst;
    @BindView(R.id.rl_hotSport_second)
    RelativeLayout rlHotSportSecond;
    @BindView(R.id.rl_hotSport_third)
    RelativeLayout rlHotSportThird;
    @BindView(R.id.tv_clear_day)
    TextView tvClearDay;
    @BindView(R.id.tv_clear_room)
    TextView tvClearRoom;
    @BindView(R.id.tv_clear_deep)
    TextView tvClearDeep;
    @BindView(R.id.rl_goods_one)
    RelativeLayout rlGoodsOne;
    @BindView(R.id.rl_goods_two)
    RelativeLayout rlGoodsTwo;
    @BindView(R.id.option_hotSport)
    OptionView optionHotSport;

    private ImageLoader mImageLoader;

    public static TabHomeFragment newInstance() {
        return new TabHomeFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTabHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .tabHomeModule(new TabHomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_home, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        AppComponent mAppComponent = ArmsUtils.obtainAppComponentFromContext(getActivity());
        mImageLoader = mAppComponent.imageLoader();

        setBannerHeight();

        mPresenter.getBanner();
        mPresenter.hotspotList(1, 3);
        mPresenter.goodsExplosion(1, 2, 2);
        mPresenter.goodsExplosion(1, 6, 3);

        tvClearDay.setOnClickListener(v -> {
            RongIM.getInstance().startConversation(getActivity(),
                    Conversation.ConversationType.PRIVATE, "002", "test");
        });
        tvClearRoom.setOnClickListener(v -> {
            RongIM.getInstance().startConversation(getActivity(),
                    Conversation.ConversationType.PRIVATE, "002", "test");
        });
        tvClearDeep.setOnClickListener(v -> {
            RongIM.getInstance().startConversation(getActivity(),
                    Conversation.ConversationType.PRIVATE, "002", "test");
        });
        optionHotSport.setRightText("更多");
        optionHotSport.setOnClickListener(v ->
                launchActivity(new Intent(getActivity(), RecycleListActivity.class)));
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    /**
     * 设置banner控件的高度
     */
    private void setBannerHeight() {
        int screenWidth = ArmsUtils.getScreenWidth(getActivity());
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

    /**
     * 更新享環保熱帖
     *
     * @param hotspotList 环保热帖列表
     */
    @Override
    public void updateHotspot(List<ArticleBean> hotspotList) {
        tvArticleNameFirst.setText(hotspotList.get(0).getContent());
        tvArticleHitsFirst.setText(String.valueOf(hotspotList.get(0).getHits()));
//        rlHotSportFirst.setOnClickListener(v -> );

        tvArticleNameSecond.setText(hotspotList.get(1).getContent());
        tvArticleHitsSecond.setText(String.valueOf(hotspotList.get(1).getHits()));
//        rlHotSportFirst.setOnClickListener(v -> );

        tvArticleNameThird.setText(hotspotList.get(2).getContent());
        tvArticleHitsThird.setText(String.valueOf(hotspotList.get(2).getHits()));
//        rlHotSportFirst.setOnClickListener(v -> );
    }

    /**
     * 更新折扣店列表
     *
     * @param goodsList 商品列表
     */
    @Override
    public void updateGoodsExplosion(List<GoodsBean> goodsList) {
        GoodsBean goodsOne = goodsList.get(0);
        if (goodsOne.getMediumImage() != null) {
            mImageLoader.loadImage(getActivity(), ImageConfigImpl.builder()
                    .url(goodsOne.getMediumImage().getUrl()).fallback(R.mipmap.ic_launcher)
                    .errorPic(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher)
                    .transformation(new CenterCrop()).imageView(ivGoodsOne).build());
        }
        tvTitleOne.setText(goodsOne.getName());
        tvContentOne.setText(goodsOne.getCaption());
        StringBuilder priceSB = new StringBuilder();
        priceSB.append(goodsOne.getPrice()).append("元");
        if (!TextUtils.isEmpty(goodsOne.getUnit())) {
            priceSB.append("/").append(goodsOne.getUnit());
        }
        tvDiscountPriceOne.setText(priceSB.toString());
        tvCostPriceOne.setText(getString(R.string.cost_price) + "：" + goodsOne.getMarketPrice());
        tvCostPriceOne.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        rlGoodsOne.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
            intent.putExtra(Constants.INTENT_GOODS_NAME, goodsOne.getName());
            intent.putExtra(Constants.INTENT_GOODS_SN, goodsOne.getSn());
            intent.putExtra(Constants.INTENT_GOODS_URL, APIs.GOODS_URL +
                    DateUtil.getDateYMToString(goodsOne.getCreateDate()) + "/" + +goodsOne.getId()
                    + ".html");
            launchActivity(intent);
        });


        GoodsBean goodsTwo = goodsList.get(1);
        if (goodsTwo.getMediumImage() != null) {
            mImageLoader.loadImage(getActivity(), ImageConfigImpl.builder()
                    .url(goodsTwo.getMediumImage().getUrl()).fallback(R.mipmap.ic_launcher)
                    .errorPic(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher)
                    .transformation(new CenterCrop()).imageView(ivGoodsTwo).build());
        }
        tvTitleTwo.setText(goodsTwo.getName());
        tvContentTwo.setText(goodsTwo.getCaption());
        priceSB = new StringBuilder();
        priceSB.append(goodsTwo.getPrice()).append("元");
        if (!TextUtils.isEmpty(goodsTwo.getUnit())) {
            priceSB.append("/").append(goodsTwo.getUnit());
        }
        tvDiscountPriceTwo.setText(priceSB.toString());
        tvCostPriceTwo.setText(getString(R.string.cost_price) + "：" + goodsTwo.getMarketPrice() + "元");
        tvCostPriceTwo.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        rlGoodsTwo.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
            intent.putExtra(Constants.INTENT_GOODS_NAME, goodsTwo.getName());
            intent.putExtra(Constants.INTENT_GOODS_SN, goodsTwo.getSn());
            intent.putExtra(Constants.INTENT_GOODS_URL, APIs.GOODS_URL +
                    DateUtil.getDateYMToString(goodsTwo.getCreateDate()) + "/" + +goodsTwo.getId()
                    + ".html");
            launchActivity(intent);
        });
    }

    /**
     * 更新招牌菜列表
     *
     * @param dishList 招牌菜列表
     */
    @Override
    public void updateDishExplosion(List<GoodsBean> dishList) {
        if (dishList.get(0).getMediumImage() != null) {
            mImageLoader.loadImage(getActivity(), ImageConfigImpl.builder()
                    .url(dishList.get(0).getMediumImage().getUrl()).fallback(R.mipmap.ic_launcher)
                    .errorPic(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher)
                    .transformation(new CenterCrop()).imageView(ivDishOne).build());
            ivDishOne.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra(Constants.INTENT_GOODS_NAME, dishList.get(0).getName());
                intent.putExtra(Constants.INTENT_GOODS_SN, dishList.get(0).getSn());
                intent.putExtra(Constants.INTENT_GOODS_URL, APIs.GOODS_URL +
                        DateUtil.getDateYMToString(dishList.get(0).getCreateDate()) + "/" + +dishList.get(0).getId()
                        + ".html");
                launchActivity(intent);
            });
        }


        if (dishList.get(1).getMediumImage() != null) {
            mImageLoader.loadImage(getActivity(), ImageConfigImpl.builder()
                    .url(dishList.get(1).getMediumImage().getUrl()).fallback(R.mipmap.ic_launcher)
                    .errorPic(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher)
                    .transformation(new CenterCrop()).imageView(ivDishTwo).build());
            ivDishTwo.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra(Constants.INTENT_GOODS_NAME, dishList.get(1).getName());
                intent.putExtra(Constants.INTENT_GOODS_SN, dishList.get(1).getSn());
                intent.putExtra(Constants.INTENT_GOODS_URL, APIs.GOODS_URL +
                        DateUtil.getDateYMToString(dishList.get(1).getCreateDate()) + "/" + +dishList.get(1).getId()
                        + ".html");
                launchActivity(intent);
            });
        }


        if (dishList.get(2).getMediumImage() != null) {
            mImageLoader.loadImage(getActivity(), ImageConfigImpl.builder()
                    .url(dishList.get(2).getMediumImage().getUrl()).fallback(R.mipmap.ic_launcher)
                    .errorPic(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher)
                    .transformation(new CenterCrop()).imageView(ivDishThree).build());
            ivDishThree.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra(Constants.INTENT_GOODS_NAME, dishList.get(2).getName());
                intent.putExtra(Constants.INTENT_GOODS_SN, dishList.get(2).getSn());
                intent.putExtra(Constants.INTENT_GOODS_URL, APIs.GOODS_URL +
                        DateUtil.getDateYMToString(dishList.get(2).getCreateDate()) + "/" + +dishList.get(2).getId()
                        + ".html");
                launchActivity(intent);
            });
        }

        if (dishList.get(3).getMediumImage() != null) {
            mImageLoader.loadImage(getActivity(), ImageConfigImpl.builder()
                    .url(dishList.get(3).getMediumImage().getUrl()).fallback(R.mipmap.ic_launcher)
                    .errorPic(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher)
                    .transformation(new CenterCrop()).imageView(ivDishFour).build());
            ivDishFour.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra(Constants.INTENT_GOODS_NAME, dishList.get(3).getName());
                intent.putExtra(Constants.INTENT_GOODS_SN, dishList.get(3).getSn());
                intent.putExtra(Constants.INTENT_GOODS_URL, APIs.GOODS_URL +
                        DateUtil.getDateYMToString(dishList.get(3).getCreateDate()) + "/" + +dishList.get(3).getId()
                        + ".html");
                launchActivity(intent);
            });
        }

        if (dishList.get(4).getMediumImage() != null) {
            mImageLoader.loadImage(getActivity(), ImageConfigImpl.builder()
                    .url(dishList.get(4).getMediumImage().getUrl()).fallback(R.mipmap.ic_launcher)
                    .errorPic(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher)
                    .transformation(new CenterCrop()).imageView(ivDishFive).build());
            ivDishFive.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra(Constants.INTENT_GOODS_NAME, dishList.get(4).getName());
                intent.putExtra(Constants.INTENT_GOODS_SN, dishList.get(4).getSn());
                intent.putExtra(Constants.INTENT_GOODS_URL, APIs.GOODS_URL +
                        DateUtil.getDateYMToString(dishList.get(4).getCreateDate()) + "/" + +dishList.get(4).getId()
                        + ".html");
                launchActivity(intent);
            });
        }

        if (dishList.get(5).getMediumImage() != null) {
            mImageLoader.loadImage(getActivity(), ImageConfigImpl.builder()
                    .url(dishList.get(5).getMediumImage().getUrl()).fallback(R.mipmap.ic_launcher)
                    .errorPic(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher)
                    .transformation(new CenterCrop()).imageView(ivDishSix).build());
            ivDishSix.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra(Constants.INTENT_GOODS_NAME, dishList.get(5).getName());
                intent.putExtra(Constants.INTENT_GOODS_SN, dishList.get(5).getSn());
                intent.putExtra(Constants.INTENT_GOODS_URL, APIs.GOODS_URL +
                        DateUtil.getDateYMToString(dishList.get(5).getCreateDate()) + "/" + +dishList.get(5).getId()
                        + ".html");
                launchActivity(intent);
            });
        }
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
            ImageView iv = new ImageView(getActivity());
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

    }

    /**
     * 享环保、帮你忙、折扣店、招牌菜点击事件
     */
    @OnClick({R.id.tv_environment_protect, R.id.tv_help_you, R.id.tv_discount_store, R.id.tv_specialty})
    public void onModuleClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_environment_protect:
                launchActivity(new Intent(getActivity(), RecycleHomeActivity.class));
                break;
            case R.id.tv_help_you:
                launchActivity(new Intent(getActivity(), HomeServicesActivity.class));
                break;
            case R.id.tv_discount_store:
                launchActivity(new Intent(getActivity(), ShopActivity.class));
                break;
            case R.id.tv_specialty:
                launchActivity(new Intent(getActivity(), DinnerActivity.class));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPagerAdapter = null;
    }

}
