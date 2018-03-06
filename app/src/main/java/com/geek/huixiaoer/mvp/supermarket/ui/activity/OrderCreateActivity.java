package com.geek.huixiaoer.mvp.supermarket.ui.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.widget.dialog.CircleProgressDialog;
import com.geek.huixiaoer.common.widget.dialog.SimpleEditDialogFragment;
import com.geek.huixiaoer.mvp.supermarket.contract.OrderCreateContract;
import com.geek.huixiaoer.mvp.supermarket.di.component.DaggerOrderCreateComponent;
import com.geek.huixiaoer.mvp.supermarket.di.module.OrderCreateModule;
import com.geek.huixiaoer.mvp.supermarket.presenter.OrderCreatePresenter;
import com.geek.huixiaoer.mvp.supermarket.ui.adapter.OrderCreateAdapter;
import com.geek.huixiaoer.storage.entity.shop.CouponCodeBean;
import com.geek.huixiaoer.storage.entity.shop.MerchantBean;
import com.geek.huixiaoer.storage.entity.shop.OrderCheckResultBean;
import com.geek.huixiaoer.storage.entity.shop.ReceiverBean;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;

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

    @BindString(R.string.tip_balance)
    String tip_balance;
    @BindString(R.string.tip_invoice)
    String tip_invoice;
    @BindString(R.string.tip_rate)
    String tip_rate;
    @BindString(R.string.tip_tax)
    String tip_tax;
    @BindString(R.string.dialog_title_memo)
    String dialog_title_memo;
    @BindString(R.string.dialog_title_invoice)
    String dialog_title_invoice;

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

    private String mReceiverId;
    private String mInvoiceTitle;//发票抬头
    private String isUseBalance = "0";
    private String mCouponCode = "";//优惠码
    private Map<String, String> memoMap = new HashMap<>();//附言列表

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

        initCartExpendableListView(mCartList);
        intRefreshLayout();

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
                mPresenter.orderCheckout()
        );

        // 自动刷新
        refreshLayout.autoRefresh();
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
         * 头部视图（收货地址相关信息）
         */
        View headView = LayoutInflater.from(OrderCreateActivity.this).inflate(R.layout.include_receive_address, null);
        tvReceiveUser = headView.findViewById(R.id.tv_receive_user);
        tvReceivePhone = headView.findViewById(R.id.tv_receive_phone);
        tvReceiveAddress = headView.findViewById(R.id.tv_receive_address);
        elvCart.addHeaderView(headView);

        //添加尾部视图
        /*
         * 尾部视图（）
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

        //默认设置余额、优惠券和发票开关不能用
        switchBalance.setEnabled(false);
        switchInvoice.setChecked(false);
        spinnerCoupon.setEnabled(false);

        OrderCreateAdapter adapter = new OrderCreateAdapter(OrderCreateActivity.this, cartList);
        elvCart.setAdapter(adapter);
        //展开所有组
        expandAllGroup(cartList);
        adapter.setMemoOnClickListener((groupPosition, childPosition, memo, merchantId) ->
                showEditDialog(dialog_title_memo, TextUtils.isEmpty(memo) ? "" : memo, merchantId)
        );
    }

    /**
     * 更新头部跟尾部数据
     */
    @Override
    public void updateView(OrderCheckResultBean resultBean) {
        if (resultBean.getDefaultReceiver() != null && resultBean.getDefaultReceiver().size() != 0) {
            updateReceiver(resultBean.getDefaultReceiver().get(0));
        }

        tvBalance.setText(String.format("%s%s", tip_balance, resultBean.getBalance()));
        if (resultBean.getBalance() != 0) {
            switchBalance.setEnabled(true);
        }
        tvPoint.setText(String.valueOf(resultBean.getRewardPoint()));
        spinnerCoupon.setEnabled(true);
        setSpinnerCouponData(resultBean.getCouponCodeList());
        tvRate.setText(tip_invoice + "（" + tip_rate + resultBean.getTaxRate() + "）");
        switchInvoice.setEnabled(true);

        //使用余额
        switchBalance.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isUseBalance = isChecked ? "1" : "0";
            mPresenter.orderCalculate(mReceiverId, mCouponCode, mInvoiceTitle, isUseBalance,
                    new Gson().toJson(memoMap));
        });

        //开具发票
        switchBalance.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tvInvoice.setVisibility(View.VISIBLE);
            } else {
                mInvoiceTitle = "";
                tvInvoice.setVisibility(View.GONE);
            }
            mPresenter.orderCalculate(mReceiverId, mCouponCode, mInvoiceTitle, isUseBalance,
                    new Gson().toJson(memoMap));
        });
    }

    /**
     * 更新收获信息
     *
     * @param receiverBean 收货地址
     */
    private void updateReceiver(ReceiverBean receiverBean) {
        mReceiverId = receiverBean.getId();
        tvReceiveUser.setText(receiverBean.getConsignee());
        tvReceivePhone.setText(receiverBean.getPhone());
        tvReceiveAddress.setText(String.format("%s%s", receiverBean.getAreaName(), receiverBean.getAddress()));
    }

    /**
     * 设置优惠券下拉框数据
     *
     * @param couponList 优惠券列表
     */
    private void setSpinnerCouponData(List<CouponCodeBean> couponList) {
        CouponCodeBean couponCodeBean = new CouponCodeBean();
        couponCodeBean.setName("不使用优惠券");
        couponCodeBean.setValue("");
        couponList.add(0, couponCodeBean);
        ArrayAdapter<CouponCodeBean> adapter = new ArrayAdapter<>(OrderCreateActivity.this,
                R.layout.item_spinner_coupon, R.id.tv_name, couponList);
        adapter.setDropDownViewResource(R.layout.item_spinner_coupon);
        spinnerCoupon.setAdapter(adapter);

        spinnerCoupon.setOnItemClickListener((parent, view, position, id) -> {
            mCouponCode = couponList.get(position).getValue();
            mPresenter.orderCalculate(mReceiverId, mCouponCode, mInvoiceTitle, isUseBalance,
                    new Gson().toJson(memoMap));
        });
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
    public void endRefresh() {
        refreshLayout.finishRefresh();
    }

    /**
     * 显示输入对话框
     *
     * @param title      标题
     * @param content    内容（携带原有的输入内容）
     * @param merchantId 商户ID
     */
    private void showEditDialog(String title, String content, String merchantId) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentByTag("dialog_edit");
        if (null != fragment) {
            ft.remove(fragment);
        }
        SimpleEditDialogFragment dialogFragment = new SimpleEditDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.INTENT_DIALOG_TITLE, title);
        bundle.putString(Constants.INTENT_DIALOG_MESSAGE, content);
        dialogFragment.setArguments(bundle);
        dialogFragment.setOnDialogClick(content1 -> {
            if (TextUtils.isEmpty(merchantId)) {
                //如果为编辑发票抬头
                mInvoiceTitle = content1;
            } else {
                //如果为编辑订单附言
                if (!TextUtils.isEmpty(content1)) {
                    //如果附言不为空，则添加
                    memoMap.put(merchantId, content1);
                } else {
                    //如果附言为空，则查看Map中是否存在该K值，如果存在则删除
                    if (memoMap.containsKey(merchantId)) {
                        memoMap.remove(merchantId);
                    }
                }
            }
        });
        dialogFragment.show(getSupportFragmentManager(), "dialog_edit");
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
