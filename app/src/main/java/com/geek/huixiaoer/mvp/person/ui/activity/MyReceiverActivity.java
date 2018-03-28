package com.geek.huixiaoer.mvp.person.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.widget.dialog.CircleProgressDialog;
import com.geek.huixiaoer.mvp.person.contract.MyReceiverContract;
import com.geek.huixiaoer.mvp.person.di.component.DaggerMyReceiverComponent;
import com.geek.huixiaoer.mvp.person.di.module.MyReceiverModule;
import com.geek.huixiaoer.mvp.person.presenter.MyReceiverPresenter;
import com.geek.huixiaoer.mvp.person.ui.adapter.ReceiverItemHolder;
import com.geek.huixiaoer.storage.event.ReceiverEvent;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.simple.eventbus.Subscriber;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 收货地址列表
 */
public class MyReceiverActivity extends BaseActivity<MyReceiverPresenter> implements MyReceiverContract.View {

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

    private CircleProgressDialog loadingDialog;
    private int mSelectedPos = -1;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMyReceiverComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myReceiverModule(new MyReceiverModule(this))
                .build()
                .inject(this);
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
                Intent intent = new Intent(MyReceiverActivity.this, ReceiverAddActivity.class);
                intent.putExtra(Constants.INTENT_TYPE, "add");
                launchActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_recycle_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Subscriber()
    private void onReceiveUpdate(ReceiverEvent event) {
        switch (event.getEventType()) {
            case 0://保存
                break;
            case 1://设为默认地址
                if (event.isChange()) {
                    mPresenter.receiverUpdate(mSelectedPos, event.getSelectedPos());
                } else {
                    mSelectedPos = event.getSelectedPos();
                }
                break;
            case 2://收货地址更新
                mPresenter.updateItem(event.getSelectedPos(),event.getReceiverBean());
                    break;
            case 3://删除
                mPresenter.receiverDelete(event.getSelectedPos());
                break;
            default:
                break;
        }

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        tvToolbarTitle.setText(R.string.title_receiver);

        initRecycleView();

        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setDisableContentWhenRefresh(true);//刷新的时候禁止列表的操作
        refreshLayout.setDisableContentWhenLoading(false);//加载更多的时候禁止列表的操作
        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.receiverList(false);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.receiverList(true);
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
    public Activity getActivity() {
        return MyReceiverActivity.this;
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
    public void receiverUpdate(int position, boolean isDefault) {
        if (isDefault) {
            //如果设置默认地址成功，则将原来的默认地址状态设为未选中
            ReceiverItemHolder receiverItemHolder = (ReceiverItemHolder)
                    recyclerView.findViewHolderForAdapterPosition(mSelectedPos);
            //当item不可见时ViewHolder为空
            if (receiverItemHolder != null) {
                receiverItemHolder.rbDefault.setChecked(true);
            } else {
                mAdapter.notifyItemChanged(mSelectedPos);
            }
            mSelectedPos = position;
        } else {
            //如果设置默认地址失败，则将position位置的设为未选中
            ReceiverItemHolder receiverItemHolder = (ReceiverItemHolder)
                    recyclerView.findViewHolderForAdapterPosition(position);
            //当item不可见时ViewHolder为空
            if (receiverItemHolder != null) {
                receiverItemHolder.rbDefault.setChecked(false);
            } else {
                mAdapter.notifyItemChanged(position);
            }
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
    protected void onDestroy() {
        super.onDestroy();
        mAdapter = null;
        mLayoutManager = null;
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        loadingDialog = null;
    }

}
