package com.geek.huixiaoer.mvp.recycle.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.geek.huixiaoer.storage.entity.recycle.ArticleBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.geek.huixiaoer.mvp.recycle.di.component.DaggerRecycleDetailComponent;
import com.geek.huixiaoer.mvp.recycle.di.module.RecycleDetailModule;
import com.geek.huixiaoer.mvp.recycle.contract.RecycleDetailContract;
import com.geek.huixiaoer.mvp.recycle.presenter.RecycleDetailPresenter;

import com.geek.huixiaoer.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 论坛详情页
 */
public class RecycleDetailActivity extends BaseActivity<RecycleDetailPresenter> implements RecycleDetailContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
//    @BindView(R.id.refreshLayout)
//    SmartRefreshLayout refreshLayout;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.Adapter mAdapter;
    @Inject
    List<ArticleBean> mList;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRecycleDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .recycleDetailModule(new RecycleDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_recycle_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        tvToolbarTitle.setText("查看详细");

        initRecycleView();
        ArticleBean bean = (ArticleBean) getIntent().getSerializableExtra("recycle");
        mPresenter.articleDetail(bean.getId());
        mList.add(bean);
        mAdapter.notifyDataSetChanged();

//        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
//        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
//        refreshLayout.setDisableContentWhenRefresh(true);//刷新的时候禁止列表的操作
//        refreshLayout.setDisableContentWhenLoading(true);//加载更多的时候禁止列表的操作
//        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
////                mPresenter.getRecycleList(false);
//            }
//
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
////                mPresenter.getRecycleList(true);
//            }
//        });
//
//        // 自动刷新
//        refreshLayout.autoRefresh();
    }

    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(mAdapter);
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
    public Activity getActivity() {
        return RecycleDetailActivity.this;
    }

    @Override
    public void endRefresh() {
//        refreshLayout.finishRefresh();
    }

    @Override
    public void endLoadMore() {
//        refreshLayout.finishLoadmore();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter = null;
        mLayoutManager = null;
    }
}
