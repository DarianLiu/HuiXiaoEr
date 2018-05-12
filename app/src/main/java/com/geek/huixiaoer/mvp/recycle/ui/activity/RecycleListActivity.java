package com.geek.huixiaoer.mvp.recycle.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.config.EventBusTags;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.widget.recyclerview.OnItemClickListener;
import com.geek.huixiaoer.mvp.common.ui.activity.LoginActivity;
import com.geek.huixiaoer.mvp.recycle.contract.RecycleListContract;
import com.geek.huixiaoer.mvp.recycle.di.component.DaggerRecycleListComponent;
import com.geek.huixiaoer.mvp.recycle.di.module.RecycleListModule;
import com.geek.huixiaoer.mvp.recycle.presenter.RecycleListPresenter;
import com.geek.huixiaoer.mvp.recycle.ui.adpater.RecycleAdapter;
import com.geek.huixiaoer.storage.entity.recycle.ArticleBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.simple.eventbus.Subscriber;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class RecycleListActivity extends BaseActivity<RecycleListPresenter> implements RecycleListContract.View {

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
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerRecycleListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .recycleListModule(new RecycleListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_recycle_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

//    @Subscriber(tag = EventBusTags.Tag_Recycle)
//    private void onEventVersion(ArticleBean recycle) {
//        mPresenter.recycleAdd(recycle);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                String token = DataHelper.getStringSF(RecycleListActivity.this, Constants.SP_TOKEN);
                if (!TextUtils.isEmpty(token)){
                    launchActivity(new Intent(RecycleListActivity.this, ForumPostActivity.class));
                }else {
                    launchActivity(new Intent(RecycleListActivity.this, LoginActivity.class));
                }
                break;
        }
        return true;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        tvToolbarTitle.setText(R.string.title_recycle_forum);

        initRecycleView();
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setDisableContentWhenRefresh(true);//刷新的时候禁止列表的操作
        refreshLayout.setDisableContentWhenLoading(true);//加载更多的时候禁止列表的操作
        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.getRecycleList(false);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getRecycleList(true);
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
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(mAdapter);
        RecycleAdapter adapter = (RecycleAdapter)mAdapter;
        adapter.setOnItemClickListener(new OnItemClickListener<ArticleBean>() {
            @Override
            public void OnItemClick(int position, ArticleBean data) {
                Intent intent = new Intent(RecycleListActivity.this,RecycleDetailActivity.class);
                intent.putExtra("recycle",data);
                launchActivity(intent);
            }
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
    public Activity getActivity() {
        return RecycleListActivity.this;
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
        mAdapter = null;
        mLayoutManager = null;
    }
}
