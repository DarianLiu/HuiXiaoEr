package com.geek.huixiaoer.mvp.common.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.mvp.common.contract.TabMessageContract;
import com.geek.huixiaoer.mvp.common.di.component.DaggerTabMessageComponent;
import com.geek.huixiaoer.mvp.common.di.module.TabMessageModule;
import com.geek.huixiaoer.mvp.common.presenter.TabMessagePresenter;
import com.geek.huixiaoer.mvp.person.ui.adapter.MessageListAdapter;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.entity.MessageBean;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class TabMessageFragment extends BaseFragment<TabMessagePresenter> implements TabMessageContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    public static TabMessageFragment newInstance() {
        return new TabMessageFragment();
    }

    private MessageListAdapter messageListAdapter;
    private List<MessageBean> mMessages;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTabMessageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .tabMessageModule(new TabMessageModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_message, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        mPresenter.messageList(50);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMessages = new ArrayList<>();
        messageListAdapter = new MessageListAdapter(mMessages);
        recyclerView.setAdapter(messageListAdapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void setData(@Nullable Object data) {

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
    public void setMessageList(BaseArrayData<MessageBean> messageList) {
        mMessages.addAll(messageList.getPageData());
        messageListAdapter.notifyDataSetChanged();
    }
}
