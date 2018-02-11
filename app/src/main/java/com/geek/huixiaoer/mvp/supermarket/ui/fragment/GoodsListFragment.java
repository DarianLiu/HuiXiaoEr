package com.geek.huixiaoer.mvp.supermarket.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.api.APIs;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.utils.DateUtil;
import com.geek.huixiaoer.common.widget.recyclerview.GridSpacingItemDecoration;
import com.geek.huixiaoer.mvp.supermarket.contract.GoodsListContract;
import com.geek.huixiaoer.mvp.supermarket.di.component.DaggerGoodsListComponent;
import com.geek.huixiaoer.mvp.supermarket.di.module.GoodsListModule;
import com.geek.huixiaoer.mvp.supermarket.presenter.GoodsListPresenter;
import com.geek.huixiaoer.mvp.supermarket.ui.activity.GoodsDetailActivity;
import com.geek.huixiaoer.mvp.supermarket.ui.adapter.GoodsAdapter;
import com.geek.huixiaoer.storage.entity.GoodsBean;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class GoodsListFragment extends BaseFragment<GoodsListPresenter> implements GoodsListContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.Adapter mAdapter;

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerGoodsListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .goodsListModule(new GoodsListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.include_recyclerview, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        int category_id = getArguments().getInt("id");

        initRecycleView();

        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setDisableContentWhenRefresh(true);//刷新的时候禁止列表的操作
        refreshLayout.setDisableContentWhenLoading(true);//加载更多的时候禁止列表的操作
        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.getGoodsList(false, category_id);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getGoodsList(true, category_id);
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
            Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
            intent.putExtra(Constants.INTENT_GOODS_NAME, goods.getName());
            intent.putExtra(Constants.INTENT_GOODS_SN, goods.getSn());
            intent.putExtra(Constants.INTENT_GOODS_URL, APIs.GOODS_URL +
                    DateUtil.getDateYMToString(goods.getCreateDate()) + "/" + +goods.getId()
                    + ".html");
            launchActivity(intent);
        });
    }

    @Override
    public void setData(Object data) {

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

    }

    @Override
    public Activity getContext() {
        return getActivity();
    }

    @Override
    public void endRefresh() {
        refreshLayout.finishRefresh();
    }

    @Override
    public void endLoadMore() {
        refreshLayout.finishLoadmore();
    }

}
