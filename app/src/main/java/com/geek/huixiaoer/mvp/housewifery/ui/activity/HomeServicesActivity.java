package com.geek.huixiaoer.mvp.housewifery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.widget.autoviewpager.AutoScrollViewPager;
import com.geek.huixiaoer.common.widget.dialog.CircleProgressDialog;
import com.geek.huixiaoer.common.widget.recyclerview.GridSpacingItemDecoration;
import com.geek.huixiaoer.mvp.common.ui.activity.LoginActivity;
import com.geek.huixiaoer.mvp.housewifery.contract.HomeServicesContract;
import com.geek.huixiaoer.mvp.housewifery.di.component.DaggerHomeServicesComponent;
import com.geek.huixiaoer.mvp.housewifery.di.module.HomeServicesModule;
import com.geek.huixiaoer.mvp.housewifery.presenter.HomeServicesPresenter;
import com.geek.huixiaoer.mvp.housewifery.ui.adapter.ServiceAdapter;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.entity.BannerBean;
import com.geek.huixiaoer.storage.entity.MessageBean;
import com.geek.huixiaoer.storage.entity.UserBean;
import com.geek.huixiaoer.storage.entity.shop.GoodsBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import timber.log.Timber;

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
    @BindView(R.id.tv_message)
    TextView tvMessage;

    private ServiceAdapter mAdapter;
    private List<GoodsBean> mHomeServices = new ArrayList<>();

    private CircleProgressDialog loadingDialog;

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

    String ryToken = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            mPresenter.setServiceF();
        });
        tvToolbarTitle.setText(R.string.title_home_service);

        setBannerHeight();
        mPresenter.getBanner();
        mPresenter.messageList(10);
        initRefreshLayout();
        initRecyclerView();

           /*设置当前用户信息， @param userInfo 当前用户信息*/
        RongIM.setConnectionStatusListener(new MyConnectionStatusListener());

        String token = DataHelper.getStringSF(this, Constants.SP_TOKEN);
        if (!TextUtils.isEmpty(token)) {
            UserBean userBean = DataHelper.getDeviceData(this, Constants.SP_USER_INFO);
             /*设置当前用户信息， @param userInfo 当前用户信息*/
            RongIM.getInstance().setCurrentUserInfo(new UserInfo(userBean.getRyId(), userBean.getUserInfo().getNickname(), null));

        /* 设置消息体内是否携带用户信息*/
            RongIM.getInstance().setMessageAttachedUserInfo(true);

            ryToken = userBean.getRyToken();
            RongIMClient.ConnectionStatusListener.ConnectionStatus status = RongIM.getInstance().getCurrentConnectionStatus();
            if (status != RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTING) {
                RongIM.connect(ryToken
                        , new RongIMClient.ConnectCallback() {
                            @Override
                            public void onTokenIncorrect() {
                                Timber.d("=====融云TokenIncorrect");
                            }

                            @Override
                            public void onSuccess(String s) {
                                Timber.d("=====融云Success：" + s);
                            }

                            @Override
                            public void onError(RongIMClient.ErrorCode errorCode) {
                                Timber.d("=====融云errorCode：" + errorCode);
                            }
                        });
            }
        }

    }


    private class MyConnectionStatusListener implements RongIMClient.ConnectionStatusListener {

        @Override
        public void onChanged(ConnectionStatus connectionStatus) {

            switch (connectionStatus) {

                case CONNECTED://连接成功。

                    break;
                case DISCONNECTED://断开连接。

                    break;
                case CONNECTING://连接中。

                    break;
                case NETWORK_UNAVAILABLE://网络不可用。

                    break;
                case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线

                    break;
            }
        }
    }

    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            mPresenter.setServiceF();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

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
        mAdapter.setOnItemClickListener((view, viewType, data, position) -> {
            String token = DataHelper.getStringSF(this, Constants.SP_TOKEN);
            if (TextUtils.isEmpty(token)) {
                launchActivity(new Intent(this, LoginActivity.class));
            } else {
                RongIMClient.ConnectionStatusListener.ConnectionStatus status = RongIM.getInstance().getCurrentConnectionStatus();
                if (status.getValue() != 0) {
                    RongIM.connect(ryToken
                            , new RongIMClient.ConnectCallback() {
                                @Override
                                public void onTokenIncorrect() {
                                    Timber.d("=====融云TokenIncorrect");
                                }

                                @Override
                                public void onSuccess(String s) {
                                    Timber.d("=====融云Success：" + s);

                                }

                                @Override
                                public void onError(RongIMClient.ErrorCode errorCode) {
                                    Timber.d("=====融云errorCode：" + errorCode);
                                }
                            });
                    Toast.makeText(getApplicationContext(), "正在建立客服连接，请稍后10秒...", Toast.LENGTH_LONG).show();
                    mPresenter.findService(token, mHomeServices.get(position).getId(),mHomeServices.get(position).getName());
                    return;
                }

            }
        });
    }

    @Override
    public void setServiceState(String serviceId, int serverId) {
//        mPresenter.setServiceB(serviceId, serverId);
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
    protected void onDestroy() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
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
