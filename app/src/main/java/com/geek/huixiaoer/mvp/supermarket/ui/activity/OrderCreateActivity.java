package com.geek.huixiaoer.mvp.supermarket.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.widget.CircleProgressDialog;
import com.geek.huixiaoer.mvp.supermarket.contract.OrderCreateContract;
import com.geek.huixiaoer.mvp.supermarket.di.component.DaggerOrderCreateComponent;
import com.geek.huixiaoer.mvp.supermarket.di.module.OrderCreateModule;
import com.geek.huixiaoer.mvp.supermarket.presenter.OrderCreatePresenter;
import com.geek.huixiaoer.mvp.supermarket.ui.adapter.OrderCreateAdapter;
import com.geek.huixiaoer.storage.entity.shop.MerchantBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 创建订单
 */
public class OrderCreateActivity extends BaseActivity<OrderCreatePresenter> implements OrderCreateContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.elv_cart)
    ExpandableListView elvCart;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_order_amount)
    TextView tvOrderAmount;
    @BindView(R.id.tv_order_submit)
    TextView tvOrderSubmit;

    //头部视图
    private TextView tvReceiveUser, tvReceivePhone, tvReceiveAddress;

    //底部视图
    private TextView tvBalance;//余额
    private SwitchCompat switchBalance;//余额开关
    private TextView tvPoint;//积分
    private AppCompatSpinner spinnerCoupon;//优惠券下拉框2
    private TextView tvRate;//税率、税金
    private SwitchCompat switchInvoice;//发票开关
    private TextView tvInvoice;//发票抬头

    private CircleProgressDialog loadingDialog;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerOrderCreateComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .orderCreateModule(new OrderCreateModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_order_create; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        tvToolbarTitle.setText(R.string.title_order_submit);

        @SuppressWarnings("unchecked")
        List<MerchantBean> mCartList = (ArrayList<MerchantBean>) getIntent().getExtras()
                .getSerializable(Constants.INTENT_CART_LIST);

        intRefreshLayout();
        initCartExpendableListView(mCartList);
    }

    /**
     * 初始化刷新控件
     */
    private void intRefreshLayout() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(OrderCreateActivity.this));
//        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setDisableContentWhenRefresh(true);//刷新的时候禁止列表的操作
//        refreshLayout.setDisableContentWhenLoading(true);//加载更多的时候禁止列表的操作
        refreshLayout.setOnRefreshListener(refreshlayout ->
                        showMessage("刷新")
//                mPresenter.cartList(true)
        );

        // 自动刷新
//        refreshLayout.autoRefresh();
    }

    /**
     * 初始化购物车列表
     */
    private void initCartExpendableListView(List<MerchantBean> cartList) {
        //隐藏expandableListView自带的图标
        elvCart.setGroupIndicator(null);

        //expandableListView的组项点击事件监听,返回值为true则点击后group不收缩
        elvCart.setOnGroupClickListener((parent, v, groupPosition, id) -> true);

        //添加头部视图
        /*
      头部视图（收货地址相关信息）
     */
        View headView = LayoutInflater.from(OrderCreateActivity.this).inflate(R.layout.include_receive_address, null);
        tvReceiveUser = headView.findViewById(R.id.tv_receive_user);
        tvReceivePhone = headView.findViewById(R.id.tv_receive_phone);
        tvReceiveAddress = headView.findViewById(R.id.tv_receive_address);
        elvCart.addHeaderView(headView);

        //添加尾部视图
        /*
      尾部视图（）
     */
        View footView = LayoutInflater.from(OrderCreateActivity.this).inflate(R.layout.include_order_set, null);
        tvBalance = footView.findViewById(R.id.tv_balance);
        switchBalance = footView.findViewById(R.id.switch_balance);
        tvPoint = footView.findViewById(R.id.tv_point);
        spinnerCoupon = footView.findViewById(R.id.spinner_coupon);
        tvRate = footView.findViewById(R.id.tv_rate);
        switchInvoice = footView.findViewById(R.id.switch_invoice);
        tvInvoice = footView.findViewById(R.id.tv_invoice);
        elvCart.addFooterView(footView);

        elvCart.setAdapter(new OrderCreateAdapter(OrderCreateActivity.this, cartList));
        //展开所有组
        expandAllGroup(cartList);
    }

    /**
     * 展开所有组
     */
    private void expandAllGroup(List<MerchantBean> cartList) {
        //展开所有组的item
        for (int i = 0; i < cartList.size(); i++) {
            elvCart.expandGroup(i);
        }
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
    public void onDestroy() {
        super.onDestroy();
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
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

}
