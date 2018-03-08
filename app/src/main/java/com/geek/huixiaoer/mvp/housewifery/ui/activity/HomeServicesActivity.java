package com.geek.huixiaoer.mvp.housewifery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.mvp.housewifery.contract.HomeServicesContract;
import com.geek.huixiaoer.mvp.housewifery.di.component.DaggerHomeServicesComponent;
import com.geek.huixiaoer.mvp.housewifery.di.module.HomeServicesModule;
import com.geek.huixiaoer.mvp.housewifery.presenter.HomeServicesPresenter;
import com.geek.huixiaoer.mvp.supermarket.ui.adapter.HomeServicesAdapter;
import com.geek.huixiaoer.storage.entity.housewifery.HomeServiceBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.tv_housekeeping_service)
    TextView tvHousekeepingService;

    private HomeServicesAdapter mAdapter;
    private List<HomeServiceBean> mHomeServices = new ArrayList<>();

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(false);
        mAdapter = new HomeServicesAdapter(mHomeServices);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void updateView(List<HomeServiceBean> homeServices) {
        mHomeServices.clear();
        mHomeServices.addAll(homeServices);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.tv_housekeeping_service)
    public void onViewClicked() {
        launchActivity(new Intent(HomeServicesActivity.this,CustomerServiceActivity.class));
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
    public void endRefresh() {
        refreshLayout.finishRefresh();
    }

}
