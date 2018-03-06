//package com.geek.huixiaoer.mvp.supermarket.ui.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.geek.huixiaoer.R;
//import com.geek.huixiaoer.common.utils.Constants;
//import com.geek.huixiaoer.common.widget.AdaptiveViewGroup;
//import com.geek.huixiaoer.common.widget.dialog.CircleProgressDialog;
//import com.geek.huixiaoer.mvp.supermarket.contract.ProductSelectContract;
//import com.geek.huixiaoer.mvp.supermarket.di.component.DaggerProductSelectComponent;
//import com.geek.huixiaoer.mvp.supermarket.di.module.ProductSelectModule;
//import com.geek.huixiaoer.mvp.supermarket.presenter.ProductSelectPresenter;
//import com.geek.huixiaoer.storage.entity.shop.SpecificationBean;
//import com.jess.arms.base.BaseActivity;
//import com.jess.arms.di.component.AppComponent;
//import com.jess.arms.http.imageloader.glide.GlideArms;
//import com.jess.arms.utils.ArmsUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindString;
//import butterknife.BindView;
//
//import static com.jess.arms.utils.Preconditions.checkNotNull;
//
///**
// * 产品规格选择
// */
//public class ProductSelectActivity extends BaseActivity<ProductSelectPresenter> implements ProductSelectContract.View {
//
//    @BindView(R.id.iv_cancel)
//    ImageView ivCancel;
//    @BindView(R.id.tvPrice)
//    TextView tvPrice;
//    @BindView(R.id.tvStock)
//    TextView tvStock;
//    @BindView(R.id.imgGoods)
//    ImageView imgGoods;
//    @BindView(R.id.viewGroup)
//    AdaptiveViewGroup viewGroup;
//    @BindView(R.id.tv_reduce)
//    TextView tvReduce;
//    @BindView(R.id.tvQuantity)
//    TextView tvQuantity;
//    @BindView(R.id.tv_plus)
//    TextView tvPlus;
//    @BindView(R.id.btnBuy)
//    Button btnBuy;
//
//    @BindString(R.string.product_price)
//    String product_price;
//    @BindString(R.string.product_inventory)
//    String product_inventory;
//
//    private CircleProgressDialog loadingDialog;
//
//    private int quantity = 1;//产品数量
//    private int chooseID = 0;//选中项的ID
//
//    @Override
//    public void setupActivityComponent(@NonNull AppComponent appComponent) {
//        DaggerProductSelectComponent //如找不到该类,请编译一下项目
//                .builder()
//                .appComponent(appComponent)
//                .productSelectModule(new ProductSelectModule(this))
//                .build()
//                .inject(this);
//    }
//
//    @Override
//    public int initView(@Nullable Bundle savedInstanceState) {
//        return 0; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
//    }
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_product_select);
//    }
//
//    @Override
//    public void initData(@Nullable Bundle savedInstanceState) {
//        String goods_sn = getIntent().getStringExtra(Constants.INTENT_GOODS_SN);
//
//        tvQuantity.setText(String.valueOf(quantity));
//        mPresenter.goodsSpecification(goods_sn);
//
//        //activity 居于底部
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.gravity = Gravity.BOTTOM;
//        getWindow().setAttributes(lp);
//    }
//
//    @Override
//    public void updateView(List<SpecificationBean> dataList) {
//        initView(dataList);
//        setStockAndPrice(dataList.get(chooseID).getImage(), dataList.get(chooseID).getStock(),
//                dataList.get(chooseID).getPrice());
//        initViewGroup(dataList);
//    }
//
//    /**
//     * 初始化控件
//     */
//    private void initView(List<SpecificationBean> dataList) {
//
//        tvPlus.setOnClickListener(v -> {
//            //判断（自减限制数不小于0）
//            if (quantity > 0 && quantity < dataList.get(chooseID).getStock())  //先减，再判断
//            {
//                quantity++;
//                tvQuantity.setText(String.valueOf(quantity));
//            } else {
//                showMessage("库存不足");
//            }
//        });
//
//        tvReduce.setOnClickListener(v -> {
//            if (quantity > 1) {
//                //判断（大于1的才能往下减）
//                quantity--;
//                tvQuantity.setText(String.valueOf(quantity));
//            }
//        });
//
//        //取消按钮点击事件
//        ivCancel.setOnClickListener(v -> finish());
//
//        //结算按钮点击事件
//        btnBuy.setOnClickListener(v -> mPresenter.addCart(dataList.get(chooseID).getId(), quantity));
//    }
//
//    /**
//     * 设置商品的库存和价格
//     **/
//    private void setStockAndPrice(String imageUrl, int stock, String price) {
//        GlideArms.with(ProductSelectActivity.this).load(imageUrl).centerCrop().into(imgGoods);
//        tvStock.setText(String.format(product_inventory, stock));
//        tvPrice.setText(String.format("%s%s", product_price, price));
//    }
//
//    /**
//     * 初始商品化规格按钮组
//     **/
//    public void initViewGroup(List<SpecificationBean> dataList) {
//        List<String> specifications = new ArrayList<>();
//        for (int i = 0; i < dataList.size(); i++) {
//            if (dataList.get(i).getSpecifications().size() != 0) {
//                specifications.add(dataList.get(i).getSpecification());
//            }
//        }
//
//        if (specifications.size() != 0) {
//            //添加商品规格按钮组
//            for (int i = 0; i < specifications.size(); i++) {
//                final RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(this).
//                        inflate(R.layout.item_goods_specification, viewGroup, false);
//                TextView tv2 = relativeLayout.findViewById(R.id.tv);
//                tv2.setText(specifications.get(i));
//                relativeLayout.setTag(i);
//                viewGroup.addView(relativeLayout);
//            }
//            //设置默认选中第一项
//            viewGroup.chooseItemStyle(chooseID);
//
//            RelativeLayout reLayout = (RelativeLayout) viewGroup.getChildAt(chooseID);
//            TextView textView = reLayout.findViewById(R.id.tv);
//            textView.setTextColor(getResources().getColor(R.color.color_text_caption));
//
//            //点击事件监听
//            viewGroup.setGroupClickListener(item -> {
//                clearItemTextColor();
//                chooseID = item;
//                viewGroup.chooseItemStyle(chooseID);
//                //设置选中后的item的字体颜色
//                RelativeLayout reLayout1 = (RelativeLayout) viewGroup.getChildAt(item);
//                TextView textView1 = reLayout1.findViewById(R.id.tv);
//                textView1.setTextColor(getResources().getColor(R.color.color_text_caption));
//                setStockAndPrice(dataList.get(chooseID).getImage(), dataList.get(chooseID).getStock(),
//                        dataList.get(chooseID).getPrice());
//            });
//        }
//    }
//
//    /**
//     * 清除所有item的字体颜色
//     **/
//    private void clearItemTextColor() {
//        for (int i = 0; i < viewGroup.getChildCount(); i++) {
//            RelativeLayout relativeLayoutAll = (RelativeLayout) viewGroup.getChildAt(i);
//            TextView textViewAll = relativeLayoutAll.findViewById(R.id.tv);
//            textViewAll.setTextColor(getResources().getColor(R.color.color_text_body));
//        }
//    }
//
//    @Override
//    public void showLoading() {
//        if (loadingDialog == null) {
//            loadingDialog = new CircleProgressDialog.Builder(this).create();
//        }
//        if (!loadingDialog.isShowing()) {
//            loadingDialog.show();
//        }
//    }
//
//    @Override
//    public void hideLoading() {
//        if (loadingDialog != null && loadingDialog.isShowing()) {
//            loadingDialog.dismiss();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (loadingDialog != null && loadingDialog.isShowing()) {
//            loadingDialog.dismiss();
//        }
//    }
//
//    @Override
//    public void showMessage(@NonNull String message) {
//        checkNotNull(message);
//        ArmsUtils.snackbarText(message);
//    }
//
//    @Override
//    public void launchActivity(@NonNull Intent intent) {
//        checkNotNull(intent);
//        ArmsUtils.startActivity(intent);
//    }
//
//    @Override
//    public void killMyself() {
//        finish();
//    }
//
//}
