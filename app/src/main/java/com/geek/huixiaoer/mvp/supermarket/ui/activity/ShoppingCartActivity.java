package com.geek.huixiaoer.mvp.supermarket.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.widget.CircleProgressDialog;
import com.geek.huixiaoer.mvp.supermarket.contract.ShoppingCartContract;
import com.geek.huixiaoer.mvp.supermarket.di.component.DaggerShoppingCartComponent;
import com.geek.huixiaoer.mvp.supermarket.di.module.ShoppingCartModule;
import com.geek.huixiaoer.mvp.supermarket.presenter.ShoppingCartPresenter;
import com.geek.huixiaoer.mvp.supermarket.ui.adapter.CartExpandableListAdapter;
import com.geek.huixiaoer.storage.entity.shop.CartBean;
import com.geek.huixiaoer.storage.entity.shop.CartGoodsBean;
import com.geek.huixiaoer.storage.entity.shop.MerchantBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class ShoppingCartActivity extends BaseActivity<ShoppingCartPresenter> implements ShoppingCartContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cartEListView)
    ExpandableListView cartEListView;
    @BindView(R.id.emptyView)
    TextView emptyView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.relayoutCart)
    RelativeLayout relayoutCart;
    @BindView(R.id.totalTv)
    TextView totalTv;
    @BindView(R.id.tvBuy)
    TextView tvBuy;

    @BindString(R.string.total_amount)
    String total_amount;
    @BindString(R.string.error_shopping_cart_null)
    String error_shopping_cart_null;

    private CircleProgressDialog loadingDialog;
    private List<MerchantBean> mCartList = new ArrayList<>();
    private CartExpandableListAdapter mAdapter;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerShoppingCartComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .shoppingCartModule(new ShoppingCartModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_shopping_cart; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_clear, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear:
                if (mCartList.size() == 0) {
                    showMessage(error_shopping_cart_null);
                } else {
                    mPresenter.cartClear();
                }
                break;
        }
        return true;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        tvToolbarTitle.setText(R.string.title_shopping_cart);

        intRefreshLayout();
        initCartExpendableListView();

        tvBuy.setOnClickListener(v -> {
            if (mCartList.size() == 0) {
                showMessage(error_shopping_cart_null);
            } else {
                Intent intent = new Intent(ShoppingCartActivity.this, OrderCreateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.INTENT_CART_LIST, (Serializable) mCartList);
                intent.putExtras(bundle);
                launchActivity(intent);
            }
        });
    }

    /**
     * 初始化刷新控件
     */
    private void intRefreshLayout() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(ShoppingCartActivity.this));
//        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setDisableContentWhenRefresh(true);//刷新的时候禁止列表的操作
//        refreshLayout.setDisableContentWhenLoading(true);//加载更多的时候禁止列表的操作
        refreshLayout.setOnRefreshListener(refreshlayout -> mPresenter.cartList(true));

        // 自动刷新
        refreshLayout.autoRefresh();
    }

    /**
     * 初始化购物车列表
     */
    private void initCartExpendableListView() {
        //隐藏expandableListView自带的图标
        cartEListView.setGroupIndicator(null);

        //expandableListView的组项点击事件监听,返回值为true则点击后group不收缩
        cartEListView.setOnGroupClickListener((parent, v, groupPosition, id) -> true);

        mAdapter = new CartExpandableListAdapter(ShoppingCartActivity.this,
                new CartExpandableListAdapter.CartEditControl() {
                    @Override
                    public void plus(int groupPosition, int childPosition) {
                        CartGoodsBean cartGoodsBean = mCartList.get(groupPosition).getItems().get(childPosition);
                        int quantity = cartGoodsBean.getQuantity();
                        //数量不小于0才能往上加
                        if (quantity > 0) {
                            quantity++;
                            mPresenter.cartEdit(groupPosition, childPosition, cartGoodsBean.getId(), quantity);
                        }
                    }

                    @Override
                    public void reduce(int groupPosition, int childPosition) {
                        CartGoodsBean cartGoodsBean = mCartList.get(groupPosition).getItems().get(childPosition);
                        int quantity = cartGoodsBean.getQuantity();
                        //大于1的才能往下减
                        if (quantity > 0) {
                            quantity--;
                            mPresenter.cartEdit(groupPosition, childPosition, cartGoodsBean.getId(), quantity);
                        }
                    }

                    @Override
                    public void delete(int groupPosition, int childPosition) {
                        CartGoodsBean cartGoodsBean = mCartList.get(groupPosition).getItems().get(childPosition);
                        mPresenter.cartDelete(groupPosition, childPosition, cartGoodsBean.getId());
                    }
                }, mCartList);

        cartEListView.setAdapter(mAdapter);

        cartEListView.setEmptyView(emptyView);
        //expandableListView的子项点击事件监听
        cartEListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> true);
    }

    @Override
    public void updateView(CartBean cartBean) {
        if (mCartList.size() != 0) {
            mCartList.clear();
        }
        mCartList.addAll(cartBean.getDatas());
        expandAllGroup(cartBean.getDatas());

        totalTv.setText(MessageFormat.format("{0}{1}", total_amount, cartBean.getEffectivePrice()));
    }

    @Override
    public void updateCartItem(int groupPosition, int childPosition, int quantity, double totalAmount) {
        mCartList.get(groupPosition).getItems().get(childPosition).setQuantity(quantity);
        mAdapter.notifyDataSetChanged();
        totalTv.setText(MessageFormat.format("{0}{1}", total_amount, totalAmount));
    }

    @Override
    public void removeCartItem(int groupPosition, int childPosition, double totalAmount) {
        mCartList.get(groupPosition).getItems().remove(childPosition);
        if (mCartList.get(groupPosition).getItems().size() == 0) {
            mCartList.remove(groupPosition);
        }
        mAdapter.notifyDataSetChanged();
        totalTv.setText(MessageFormat.format("{0}{1}", total_amount, totalAmount));
    }

    @Override
    public void clearCart() {
        mCartList.clear();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 展开所有组
     */
    private void expandAllGroup(List<MerchantBean> cartList) {
        //展开所有组的item
        for (int i = 0; i < cartList.size(); i++) {
            cartEListView.expandGroup(i);
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
        mCartList = null;
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
    public Activity getContext() {
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

}
