package com.geek.huixiaoer.mvp.person.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.mvp.person.contract.MessageListContract;
import com.geek.huixiaoer.mvp.person.di.component.DaggerMessageListComponent;
import com.geek.huixiaoer.mvp.person.di.module.MessageListModule;
import com.geek.huixiaoer.mvp.person.presenter.MessageListPresenter;
import com.geek.huixiaoer.mvp.person.ui.adapter.MessageAdapter;
import com.geek.huixiaoer.mvp.person.ui.adapter.MessageListAdapter;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.entity.BannerBean;
import com.geek.huixiaoer.storage.entity.MessageBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MessageListActivity extends BaseActivity<MessageListPresenter> implements MessageListContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


//    private MessageAdapter<String> mAdapter;
    private MessageListAdapter messageListAdapter;
    private List<MessageBean> mMessages;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMessageListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .messageListModule(new MessageListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_message_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        tvToolbarTitle.setText("消息列表");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMessages = new ArrayList<>();
//        mAdapter = new MessageAdapter<>(mMessages);
//        MessageBean bean = new MessageBean();
//        for (int i = 0; i <5 ; i++) {
//            bean.setId(1);
//            bean.setTitle("test");
//            mMessages.add(bean);
//        }
        messageListAdapter = new MessageListAdapter(mMessages);
        recyclerView.setAdapter(messageListAdapter);
        recyclerView.setHasFixedSize(true);

        mPresenter.messageList(50);
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
    public void updateList(BaseArrayData<MessageBean> datas) {
        mMessages.addAll(datas.getPageData());
        messageListAdapter = new MessageListAdapter(mMessages);
        recyclerView.setAdapter(messageListAdapter);
//        messageListAdapter.notifyDataSetChanged();
    }

}
