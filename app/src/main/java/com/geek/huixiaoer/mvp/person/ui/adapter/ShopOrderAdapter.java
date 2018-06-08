package com.geek.huixiaoer.mvp.person.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.utils.DateUtil;
import com.geek.huixiaoer.storage.entity.shop.OrderBean;
import com.geek.huixiaoer.storage.entity.shop.OrderItemBean;
import com.jess.arms.http.imageloader.glide.GlideArms;

import java.util.List;

/**
 * 创建订单列表适配器
 * Created by Administrator on 2018/3/5.
 */

public class ShopOrderAdapter extends BaseExpandableListAdapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<OrderBean> modelList;

    public ShopOrderAdapter(Context context, List<OrderBean> list) {
        mContext = context;
        modelList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return modelList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return modelList.get(groupPosition).getItems().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return modelList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return modelList.get(groupPosition).getItems().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            groupViewHolder = new GroupViewHolder();
            convertView = mInflater.inflate(R.layout.item_shop_merchant, null);
            groupViewHolder.tvShopName = convertView.findViewById(R.id.tv_shop_name);
            groupViewHolder.ivShop = convertView.findViewById(R.id.iv_shop);
            groupViewHolder.tvOrderStatus = convertView.findViewById(R.id.tv_order_status);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.tvOrderStatus.setVisibility(View.VISIBLE);

        OrderBean orderBean = modelList.get(groupPosition);
        if (orderBean.getStatus().equals("pendingPayment")) {
            groupViewHolder.tvOrderStatus.setText("待付款");
            groupViewHolder.tvOrderStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else if (orderBean.getStatus().equals("completed")) {
            groupViewHolder.tvOrderStatus.setText("已完成");
        } else if (orderBean.getStatus().equals("failed")) {
            groupViewHolder.tvOrderStatus.setText("已失效");
        } else if (orderBean.getStatus().equals("canceled")) {
            groupViewHolder.tvOrderStatus.setText("已取消");
        } else {
            groupViewHolder.tvOrderStatus.setText("已付款");
        }
//            if (orderBean.getStatus().equals("pendingReview")) {
////                                model.setOrderStatus("待审核");
//            groupViewHolder.tvOrderStatus.setText("待审核");
//        } else if (orderBean.getStatus().equals("pendingShipment")) {
//            groupViewHolder.tvOrderStatus.setText("待发货");
//        } else if (orderBean.getStatus().equals("shipped")) {
//            groupViewHolder.tvOrderStatus.setText("待收货");
//        }
// else if (orderBean.getStatus().equals("denied")) {
//            groupViewHolder.tvOrderStatus.setText("已拒绝");
//        } else if (orderBean.getStatus().equals("received"))


        groupViewHolder.tvShopName.setText(modelList.get(groupPosition).getMerchantName());
        GlideArms.with(mContext).load(modelList.get(groupPosition).getMerchantHeadURL()).circleCrop()
                .error(R.drawable.icon_user_head)
                .into(groupViewHolder.ivShop)
        ;
        convertView.setPadding(0, 20, 0, 0);//设置列表间距
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            holder = new ChildViewHolder();
            convertView = mInflater.inflate(R.layout.item_shop_order, null);
            holder.ivProduct = convertView.findViewById(R.id.iv_product);
            holder.tvProductName = convertView.findViewById(R.id.tv_product_name);
            holder.tvProductPrice = convertView.findViewById(R.id.tv_product_price);
            holder.tvQuantity = convertView.findViewById(R.id.tvQuantity);
            holder.tvCreateData = convertView.findViewById(R.id.tv_create_data);
            holder.tvTotalAmount = convertView.findViewById(R.id.tv_total_amount);
            holder.tvMemo = convertView.findViewById(R.id.tv_memo);
            holder.rlFootView = convertView.findViewById(R.id.rl_footView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }

        OrderItemBean orderItemBean = modelList.get(groupPosition).getItems().get(childPosition);

        GlideArms.with(mContext).load(orderItemBean.getThumbnail()).centerCrop().into(holder.ivProduct);
        if (orderItemBean.getProduct_specifications() == null || orderItemBean.getProduct_specifications().size() == 0) {
            holder.tvProductName.setText(orderItemBean.getName());
        } else {
            holder.tvProductName.setText(orderItemBean.getName() + "（" + orderItemBean.getSpecifications() + "）");
        }
        holder.tvProductPrice.setText(String.format("￥%s", orderItemBean.getPrice()));
        holder.tvQuantity.setText(String.format("x %s", String.valueOf(modelList.get(groupPosition)
                .getItems().get(childPosition).getQuantity())));

        if (childPosition == (getChildrenCount(groupPosition) - 1)) {
            holder.rlFootView.setVisibility(View.VISIBLE);
            holder.tvCreateData.setText(DateUtil.getDateTimeToString(modelList.get(groupPosition)
                    .getCreateDate()));
            holder.tvTotalAmount.setText("共" + modelList.get(groupPosition).getItems().size()
                    + "件商品，合计" + modelList.get(groupPosition).getAmount() + "元");

            if (TextUtils.isEmpty(modelList.get(groupPosition).getMemo())) {
                holder.tvMemo.setVisibility(View.GONE);
            } else {
                holder.tvMemo.setText(modelList.get(groupPosition).getMemo());
            }
        } else {
            holder.rlFootView.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class GroupViewHolder {
        TextView tvShopName;//地址
        ImageView ivShop;
        TextView tvOrderStatus;
    }

    private class ChildViewHolder {

        ImageView ivProduct;
        TextView tvProductName;//商品名
        TextView tvProductPrice; // 售价
        TextView tvQuantity;//数量
        TextView tvCreateData;
        TextView tvTotalAmount;
        TextView tvMemo;
        RelativeLayout rlFootView;
    }

}
