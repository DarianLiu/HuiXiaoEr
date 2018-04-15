package com.geek.huixiaoer.mvp.common.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.widget.OptionView;
import com.geek.huixiaoer.mvp.common.contract.TabMessageContract;
import com.geek.huixiaoer.mvp.common.di.component.DaggerTabMessageComponent;
import com.geek.huixiaoer.mvp.common.di.module.TabMessageModule;
import com.geek.huixiaoer.mvp.common.presenter.TabMessagePresenter;
import com.geek.huixiaoer.mvp.person.ui.activity.MessageListActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class TabMessageFragment extends BaseFragment<TabMessagePresenter> implements TabMessageContract.View {

    @BindView(R.id.option_msg)
    OptionView optionMsg;
    Unbinder unbinder;

    public static TabMessageFragment newInstance() {
        return new TabMessageFragment();
    }

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
//        optionMsg.setRightText("查看更多");
        optionMsg.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(DataHelper.getStringSF(getActivity(), Constants.SP_TOKEN))) {
                launchActivity(new Intent(getActivity(), MessageListActivity.class));
            }
        });
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

}
