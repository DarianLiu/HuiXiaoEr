package com.geek.huixiaoer.mvp.person.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.mvp.person.contract.ShopOrderContract;
import com.geek.huixiaoer.mvp.person.di.component.DaggerShopOrderComponent;
import com.geek.huixiaoer.mvp.person.di.module.ShopOrderModule;
import com.geek.huixiaoer.mvp.person.presenter.ShopOrderPresenter;
import com.geek.huixiaoer.mvp.person.ui.adapter.ShopOrderAdapter;
import com.geek.huixiaoer.storage.entity.shop.OrderBean;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 购物订单列表
 */
public class ShopOrderFragment extends BaseFragment<ShopOrderPresenter> implements ShopOrderContract.View {


    private SmartRefreshLayout refreshLayout;
    private ExpandableListView expendableListView;

    private List<OrderBean> mOrderList;
    private ShopOrderAdapter mAdapter;

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerShopOrderComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .shopOrderModule(new ShopOrderModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.include_expandable_listview, container, false);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        expendableListView = view.findViewById(R.id.expendableListView);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        String state = getArguments().getString("status");
        intRefreshView(state);
        initExpandableListView();
    }

    /**
     * 初始化刷新事件
     *
     * @param state 订单状态
     */
    private void intRefreshView(String state) {
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setDisableContentWhenRefresh(true);//刷新的时候禁止列表的操作
        refreshLayout.setDisableContentWhenLoading(true);//加载更多的时候禁止列表的操作
        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.orderList(false, state);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.orderList(true, state);
            }
        });

        // 自动刷新
        refreshLayout.autoRefresh();
    }

    public void initExpandableListView() {
        expendableListView.setGroupIndicator(null);
        //点击group不收缩
        expendableListView.setOnGroupClickListener((parent, v, groupPosition, id) -> true);
        mOrderList = new ArrayList<>();
        mAdapter = new ShopOrderAdapter(getActivity(), mOrderList);
        expendableListView.setAdapter(mAdapter);
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

    /**
     * 展开所有组
     */
    private void expandAllGroup() {
        for (int i = 0; i < expendableListView.getCount(); i++) {
            if (!expendableListView.isGroupExpanded(i))
                expendableListView.expandGroup(i);
        }
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

    @Override
    public void updateView(boolean isRefresh, List<OrderBean> orderList) {
        if (isRefresh) {
            mOrderList.clear();
        }
        mOrderList.addAll(orderList);
        mAdapter.notifyDataSetChanged();
        expandAllGroup();
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

}
