package com.geek.huixiaoer.mvp.person.ui.fragment;

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
import com.geek.huixiaoer.mvp.person.contract.TabOrderContract;
import com.geek.huixiaoer.mvp.person.di.component.DaggerTabOrderComponent;
import com.geek.huixiaoer.mvp.person.di.module.TabOrderModule;
import com.geek.huixiaoer.mvp.person.presenter.TabOrderPresenter;
import com.geek.huixiaoer.mvp.person.ui.activity.MyShopOrderActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class TabOrderFragment extends BaseFragment<TabOrderPresenter> implements TabOrderContract.View {

    @BindView(R.id.option_order_discount_store)
    OptionView optionOrderDiscountStore;
    @BindView(R.id.option_order_specialty)
    OptionView optionOrderSpecialty;
    @BindView(R.id.option_order_help_you)
    OptionView optionOrderHelpYou;

    public static TabOrderFragment newInstance() {
        return new TabOrderFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTabOrderComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .tabOrderModule(new TabOrderModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_order, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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

    @OnClick({R.id.option_order_discount_store, R.id.option_order_specialty, R.id.option_order_help_you})
    public void onViewClicked(View view) {
        if (checkToken()){
            switch (view.getId()) {
                case R.id.option_order_discount_store:
                    launchActivity(new Intent(getActivity(), MyShopOrderActivity.class));
                    break;
                case R.id.option_order_specialty:
                    launchActivity(new Intent(getActivity(), MyShopOrderActivity.class));
                    break;
                case R.id.option_order_help_you:
                    launchActivity(new Intent(getActivity(), MyShopOrderActivity.class));
                    break;
            }
        }
    }

    private boolean checkToken(){
        String token = DataHelper.getStringSF(getActivity(), Constants.SP_TOKEN);
        if (TextUtils.isEmpty(token)){
            showMessage("您还没有登录哦！");
            return false;
        }else {
            return true;
        }
    }
}
