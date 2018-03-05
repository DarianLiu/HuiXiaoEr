package com.geek.huixiaoer.mvp.supermarket.ui.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.widget.AdaptiveViewGroup;
import com.geek.huixiaoer.storage.entity.shop.SpecificationBean;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品规格选择页面
 * Created by 刘力 on 2018/3/3.
 */

public class ProductSelectFragment extends DialogFragment {

    @BindView(R.id.iv_cancel)
    ImageView ivCancel;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvStock)
    TextView tvStock;
    @BindView(R.id.imgGoods)
    ImageView imgGoods;
    @BindView(R.id.viewGroup)
    AdaptiveViewGroup viewGroup;
    @BindView(R.id.tv_reduce)
    TextView tvReduce;
    @BindView(R.id.tvQuantity)
    TextView tvQuantity;
    @BindView(R.id.tv_plus)
    TextView tvPlus;
    @BindView(R.id.btnBuy)
    Button btnBuy;

    @BindString(R.string.product_price_unit)
    String product_price_unit;
    @BindString(R.string.product_inventory)
    String product_inventory;

    private int quantity = 1;//产品数量
    private int chooseID = 0;//选中项的ID

    private DialogOnClickListener dialogOnClickListener;

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM; // 显示在底部
        params.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度填充满屏
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        // 这里用透明颜色替换掉系统自带背景
        int color = ContextCompat.getColor(getActivity(), android.R.color.transparent);
        window.setBackgroundDrawable(new ColorDrawable(color));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_product_select, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<SpecificationBean> dataList = bundle.getParcelableArrayList("specification");
            initView(dataList);
            initViewGroup(dataList);
        }

    }

    /**
     * 初始化控件
     */
    private void initView(List<SpecificationBean> dataList) {
        tvQuantity.setText(String.valueOf(quantity));

        tvPlus.setOnClickListener(v -> {
            //判断（自减限制数不小于0）
            if (quantity > 0 && quantity < dataList.get(chooseID).getStock())  //先减，再判断
            {
                quantity++;
                tvQuantity.setText(String.valueOf(quantity));
            } else {
                ArmsUtils.snackbarText("库存不足");
            }
        });

        tvReduce.setOnClickListener(v -> {
            if (quantity > 1) {
                //判断（大于1的才能往下减）
                quantity--;
                tvQuantity.setText(String.valueOf(quantity));
            }
        });

        //取消按钮点击事件
        ivCancel.setOnClickListener(v -> dismiss());

        //结算按钮点击事件
        btnBuy.setOnClickListener(v -> {
                    if (dialogOnClickListener != null) {
                        dialogOnClickListener.onAddCartClick(dataList.get(chooseID).getId(), quantity);
                    }
                    dismiss();
                }
        );
    }

    /**
     * 初始商品化规格按钮组
     **/
    public void initViewGroup(List<SpecificationBean> dataList) {
        setStockAndPrice(dataList.get(chooseID).getImage(), dataList.get(chooseID).getStock(),
                String.valueOf(dataList.get(chooseID).getPrice()));

        List<String> specifications = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getSpecifications().size() != 0) {
                specifications.add(dataList.get(i).getSpecification());
            }
        }

        if (specifications.size() != 0) {
            //添加商品规格按钮组
            for (int i = 0; i < specifications.size(); i++) {
                final RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(getActivity()).
                        inflate(R.layout.item_goods_specification, viewGroup, false);
                TextView tv2 = relativeLayout.findViewById(R.id.tv);
                tv2.setText(specifications.get(i));
                relativeLayout.setTag(i);
                viewGroup.addView(relativeLayout);
            }
            //设置默认选中第一项
            viewGroup.chooseItemStyle(chooseID);

            RelativeLayout reLayout = (RelativeLayout) viewGroup.getChildAt(chooseID);
            TextView textView = reLayout.findViewById(R.id.tv);
            textView.setTextColor(getResources().getColor(R.color.color_text_caption));

            //点击事件监听
            viewGroup.setGroupClickListener(item -> {
                clearItemTextColor();
                chooseID = item;
                viewGroup.chooseItemStyle(chooseID);
                //设置选中后的item的字体颜色
                RelativeLayout reLayout1 = (RelativeLayout) viewGroup.getChildAt(item);
                TextView textView1 = reLayout1.findViewById(R.id.tv);
                textView1.setTextColor(getResources().getColor(R.color.color_text_caption));
                setStockAndPrice(dataList.get(chooseID).getImage(), dataList.get(chooseID).getStock(),
                        String.valueOf(dataList.get(chooseID).getPrice()));
            });
        }
    }

    /**
     * 设置商品的库存和价格
     **/
    private void setStockAndPrice(String imageUrl, int stock, String price) {
        GlideArms.with(ProductSelectFragment.this).load(imageUrl).centerCrop().into(imgGoods);
        tvStock.setText(String.format(product_inventory, stock));
        tvPrice.setText(String.format("%s%s", product_price_unit, price));
    }

    /**
     * 清除所有item的字体颜色
     **/
    private void clearItemTextColor() {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            RelativeLayout relativeLayoutAll = (RelativeLayout) viewGroup.getChildAt(i);
            TextView textViewAll = relativeLayoutAll.findViewById(R.id.tv);
            textViewAll.setTextColor(getResources().getColor(R.color.color_text_body));
        }
    }

    public interface DialogOnClickListener {
        void onAddCartClick(String productId, int quantity);
    }

    public void setOnClickListener(DialogOnClickListener dialogOnClickListener) {
        this.dialogOnClickListener = dialogOnClickListener;
    }
}
