package com.geek.huixiaoer.mvp.dinner.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.geek.huixiaoer.api.APIs;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.utils.DateUtil;
import com.geek.huixiaoer.common.widget.recyclerview.GridSpacingItemDecoration;
import com.geek.huixiaoer.mvp.supermarket.ui.activity.GoodsDetailActivity;
import com.geek.huixiaoer.mvp.supermarket.ui.adapter.GoodsAdapter;
import com.geek.huixiaoer.storage.entity.shop.GoodsBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.geek.huixiaoer.mvp.dinner.di.component.DaggerDinnerComponent;
import com.geek.huixiaoer.mvp.dinner.di.module.DinnerModule;
import com.geek.huixiaoer.mvp.dinner.contract.DinnerContract;
import com.geek.huixiaoer.mvp.dinner.presenter.DinnerPresenter;

import com.geek.huixiaoer.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;


import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class DinnerActivity extends BaseActivity<DinnerPresenter> implements DinnerContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.Adapter mAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDinnerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .dinnerModule(new DinnerModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_recycle_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        tvToolbarTitle.setText(R.string.title_dinner);

        initRecycleView();

        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setDisableContentWhenRefresh(true);//刷新的时候禁止列表的操作
        refreshLayout.setDisableContentWhenLoading(true);//加载更多的时候禁止列表的操作
        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.dishList(false, "", "", "");
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.dishList(true, "", "", "");
            }
        });

        // 自动刷新
        refreshLayout.autoRefresh();
    }

    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 15, true));
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(mAdapter);

        GoodsAdapter goodsAdapter = (GoodsAdapter) mAdapter;
        goodsAdapter.setOnItemClickListener((view, viewType, data, position) -> {
            GoodsBean goods = (GoodsBean) data;
            Intent intent = new Intent(DinnerActivity.this, GoodsDetailActivity.class);
            intent.putExtra(Constants.INTENT_GOODS_NAME, goods.getName());
            intent.putExtra(Constants.INTENT_GOODS_SN, goods.getSn());
            intent.putExtra(Constants.INTENT_GOODS_URL, APIs.GOODS_URL +
                    DateUtil.getDateYMToString(goods.getCreateDate()) + "/" + +goods.getId()
                    + ".html");
            launchActivity(intent);
        });
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
    public Activity getContext() {
        return this;
    }

    @Override
    public void endRefresh() {
        refreshLayout.finishRefresh();
    }

    @Override
    public void endLoadMore() {
        refreshLayout.finishLoadmore();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLayoutManager = null;
        mAdapter = null;
    }
}
