package com.geek.huixiaoer.mvp.person.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.utils.DateUtil;
import com.geek.huixiaoer.storage.entity.shop.OrderDetailBean;
import com.geek.huixiaoer.storage.entity.shop.OrderItemBean;
import com.jess.arms.http.imageloader.glide.GlideArms;

/**
 * Created by GYJK on 2016/11/3.
 */
public class OrderDetailAdapter extends BaseAdapter {

    private final int TYPE_ONE = 0;
    private final int TYPE_TWO = 1;
    private final int TYPE_THREE = 2;
    private final int TYPE_COUNT = 3;

    private int currentType;
    private Context context;
    private OrderDetailBean orderInfo;
    private LayoutInflater inflater;

    public OrderDetailAdapter(Context context, OrderDetailBean OrderInfo) {
        this.context = context.getApplicationContext();
        this.orderInfo = OrderInfo;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return orderInfo.getOrders().get(0).getItems().size() + 2;
    }

    @Override
    public Object getItem(int position) {
        return orderInfo;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderOne viewHolderOne = null;
        ViewHolderTwo viewHolderTwo = null;
        ViewHolderThree viewHolderThree = null;
        currentType = getItemViewType(position);
        if (convertView == null) {
            switch (currentType) {
                case TYPE_ONE:
                    viewHolderOne = new ViewHolderOne();
                    convertView = inflater.inflate(R.layout.item_order_recieve, null);
                    viewHolderOne.consigneeText = convertView.findViewById(R.id.consigneeText);
                    viewHolderOne.phoneText = convertView.findViewById(R.id.phoneText);
                    viewHolderOne.addressText = convertView.findViewById(R.id.addressText);
                    viewHolderOne.orderStatusText = convertView.findViewById(R.id.orderStatusText);
                    viewHolderOne.shopNameText = convertView.findViewById(R.id.shopNameText);
                    viewHolderOne.shopImg = convertView.findViewById(R.id.shopImg);
                    convertView.setTag(viewHolderOne);
                    break;
                case TYPE_TWO:
                    viewHolderTwo = new ViewHolderTwo();
                    convertView = inflater.inflate(R.layout.item_shop_order, null);
                    viewHolderTwo.ivProduct = convertView.findViewById(R.id.iv_product);
                    viewHolderTwo.tvProductName = convertView.findViewById(R.id.tv_product_name);
                    viewHolderTwo.tvProductPrice = convertView.findViewById(R.id.tv_product_price);
                    viewHolderTwo.tvQuantity = convertView.findViewById(R.id.tvQuantity);
                    viewHolderTwo.tvCreateData = convertView.findViewById(R.id.tv_create_data);
                    viewHolderTwo.tvTotalAmount = convertView.findViewById(R.id.tv_total_amount);
                    viewHolderTwo.tvMemo = convertView.findViewById(R.id.tv_memo);
                    viewHolderTwo.rlFootView = convertView.findViewById(R.id.rl_footView);
                    convertView.setTag(viewHolderTwo);
                    break;
                case TYPE_THREE:
                    viewHolderThree = new ViewHolderThree();
                    convertView = inflater.inflate(R.layout.item_order_info, null);
                    viewHolderThree.freightText = convertView.findViewById(R.id.freightText);
                    viewHolderThree.taxText = convertView.findViewById(R.id.taxText);
                    viewHolderThree.taxRelayout = convertView.findViewById(R.id.taxRelayout);
                    viewHolderThree.pointRelayout = convertView.findViewById(R.id.pointRelayout);
                    viewHolderThree.payText = convertView.findViewById(R.id.payText);
                    viewHolderThree.payAmountText = convertView.findViewById(R.id.payAmountText);
                    viewHolderThree.rewardPointText = convertView.findViewById(R.id.rewardPointText);
                    viewHolderThree.memoText = convertView.findViewById(R.id.memoText);
                    convertView.setTag(viewHolderThree);
                    break;
            }
        } else {
            switch (currentType) {
                case TYPE_ONE:
                    viewHolderOne = (ViewHolderOne) convertView.getTag();
                    break;
                case TYPE_TWO:
                    viewHolderTwo = (ViewHolderTwo) convertView.getTag();
                    break;
                case TYPE_THREE:
                    viewHolderThree = (ViewHolderThree) convertView.getTag();
                    break;
                default:
                    break;
            }
        }

        //填充数据
        if (currentType == TYPE_ONE) {
            viewHolderOne.consigneeText.setText(orderInfo.getConsignee());
            viewHolderOne.phoneText.setText(orderInfo.getPhone());
            viewHolderOne.addressText.setText(orderInfo.getAddress());
            viewHolderOne.shopNameText.setText(orderInfo.getOrders().get(0).getMerchantName());
//            Log.e("======商户名=====", orderInfo.getMerchantName());
            long orderCreateData = orderInfo.getOrders().get(0).getCreateDate();
            long nowData = System.currentTimeMillis();
            if (DateUtil.invalid(orderCreateData, nowData)) {
                viewHolderOne.orderStatusText.setText("已失效");
            }else {
                viewHolderOne.orderStatusText.setText(orderInfo.getOrderStatus());
            }

            GlideArms.with(context).load(orderInfo.getOrders().get(0).getMerchantHeadURL())
                    .circleCrop().error(R.drawable.icon_head_default)
                    .into(viewHolderOne.shopImg);
        } else if (currentType == TYPE_TWO) {
            OrderItemBean productInfo = orderInfo.getOrders().get(0).getItems().get(position - 1);
            GlideArms.with(context).load(productInfo.getThumbnail())
                    .into(viewHolderTwo.ivProduct);

            if (productInfo.getSpecifications() == null || TextUtils.isEmpty(productInfo.getSpecifications())) {
                viewHolderTwo.tvProductName.setText(productInfo.getName());
            } else {
                viewHolderTwo.tvProductName.setText(productInfo.getName() +
                        "(" + productInfo.getSpecifications() + ")");
            }
            double total = productInfo.getPrice() * productInfo.getQuantity();
            viewHolderTwo.tvCreateData.setText(DateUtil.getDateTimeToString(orderInfo.getOrders().get(0).getCreateDate()));
            viewHolderTwo.tvTotalAmount.setText("共" + productInfo.getQuantity() + "件商品，合计" + total + "元");
            viewHolderTwo.tvProductPrice.setText("￥" + productInfo.getPrice());
            viewHolderTwo.tvQuantity.setText("x" + productInfo.getQuantity());
            viewHolderTwo.tvMemo.setVisibility(View.GONE);
        } else if (currentType == TYPE_THREE) {
            viewHolderThree.taxText.setText("￥" + orderInfo.getTax());
//                Log.e("=======赠送积分=======", orderInfo.getRewardPoint());
            viewHolderThree.rewardPointText.setText("" + orderInfo.getRewardPoint());
            viewHolderThree.payAmountText.setText("￥" + orderInfo.getAmount());

            if (!TextUtils.isEmpty(orderInfo.getMemo())) {
                viewHolderThree.memoText.setVisibility(View.VISIBLE);
                viewHolderThree.memoText.setText(orderInfo.getMemo());
            } else {
                viewHolderThree.memoText.setVisibility(View.GONE);
            }

            viewHolderThree.freightText.setText("￥" + orderInfo.getFreight());
        }
        return convertView;
    }

    static class ViewHolderOne {
        public TextView consigneeText;
        public TextView phoneText;
        public TextView addressText;
        public TextView orderStatusText;
        public TextView shopNameText;
        public ImageView shopImg;
    }

    static class ViewHolderTwo {
        public ImageView ivProduct;
        public TextView tvProductName;//商品名
        public TextView tvProductPrice; // 售价
        public TextView tvQuantity;//数量
        public TextView tvCreateData;
        public TextView tvTotalAmount;
        public TextView tvMemo;
        public RelativeLayout rlFootView;
    }

    static class ViewHolderThree {
        public TextView freightText;//手续费
        public TextView rewardPointText;//赠送积分
        public RelativeLayout pointRelayout;
        public TextView taxText;//税金
        public RelativeLayout taxRelayout;
        public TextView payText;
        public TextView payAmountText;//实付金额/应收金额
        public TextView memoText;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_ONE;
        } else if (position == (orderInfo.getOrders().get(0).getItems().size() + 1)) {
            return TYPE_THREE;
        } else {
            return TYPE_TWO;
        }
    }

}
