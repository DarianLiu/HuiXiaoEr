package com.geek.huixiaoer.mvp.recycle.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.mvp.common.ui.activity.LoginActivity;
import com.geek.huixiaoer.mvp.recycle.contract.ForumContract;
import com.geek.huixiaoer.mvp.recycle.di.component.DaggerForumComponent;
import com.geek.huixiaoer.mvp.recycle.di.module.ForumModule;
import com.geek.huixiaoer.mvp.recycle.presenter.ForumPresenter;
import com.geek.huixiaoer.mvp.recycle.ui.adpater.ForumAdapter;
import com.geek.huixiaoer.mvp.recycle.ui.adpater.ForumItemHolder;
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

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 享环保论坛
 */
public class ForumActivity extends BaseActivity<ForumPresenter> implements ForumContract.View {

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
        DaggerForumComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .forumModule(new ForumModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_forum; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                String token = DataHelper.getStringSF(this, Constants.SP_TOKEN);
                if (!TextUtils.isEmpty(token)) {
                    launchActivity(new Intent(ForumActivity.this, ForumPostActivity.class));
                } else {
                    launchActivity(new Intent(ForumActivity.this, LoginActivity.class));
                }
                break;
        }
        return true;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        tvToolbarTitle.setText(R.string.title_recycle_forum);

        initRecycleView();
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setDisableContentWhenRefresh(true);//刷新的时候禁止列表的操作
        refreshLayout.setDisableContentWhenLoading(true);//加载更多的时候禁止列表的操作
        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.getForumList(false);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getForumList(true);
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
        ForumAdapter adapter = (ForumAdapter) mAdapter;
        adapter.setOnItemClickListener((view, viewType, data, position) -> {
            ArticleBean articleBean = (ArticleBean) data;
            int hits = Integer.parseInt(articleBean.getHits()) + 1;
            articleBean.setHits(String.valueOf(hits));
            adapter.getHolder(view, viewType).setData(articleBean, position);
            adapter.notifyItemChanged(position);
            Intent intent = new Intent(ForumActivity.this, ForumPostDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("forum", articleBean);
            intent.putExtras(bundle);
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
    public Activity getActivity() {
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
        mAdapter = null;
        mLayoutManager = null;
    }
}
