package com.geek.huixiaoer.mvp.supermarket.ui.adapter;

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
import com.geek.huixiaoer.storage.entity.shop.MerchantBean;
import com.geek.huixiaoer.storage.entity.shop.ProductBean;
import com.jess.arms.http.imageloader.glide.GlideArms;

import java.util.List;

/**
 * 创建订单列表适配器
 * Created by Administrator on 2018/3/5.
 */

public class OrderCreateAdapter extends BaseExpandableListAdapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<MerchantBean> modelList;

    private MemoOnClickListener memoOnClickListener;

    public OrderCreateAdapter(Context context, List<MerchantBean> list) {
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
        groupViewHolder.tvOrderStatus.setVisibility(View.GONE);
        groupViewHolder.tvShopName.setText(modelList.get(groupPosition).getName());
        GlideArms.with(mContext).load(modelList.get(groupPosition).getHeadURL()).circleCrop()
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

        ProductBean productInfo = modelList.get(groupPosition).getItems().get(childPosition).getProduct();

        GlideArms.with(mContext).load(productInfo.getGoods_image()).centerCrop().into(holder.ivProduct);
        if (productInfo.getSpecifications() == null || productInfo.getSpecifications().size() == 0) {
            holder.tvProductName.setText(productInfo.getGoods_name());
        } else {
            holder.tvProductName.setText(productInfo.getGoods_name() + "（"
                    + productInfo.getSpecification() + "）");
        }
        holder.tvProductPrice.setText(String.format("￥%s", productInfo.getPrice()));
        holder.tvQuantity.setText(String.format("x %s", String.valueOf(modelList.get(groupPosition)
                .getItems().get(childPosition).getQuantity())));

        if (childPosition == (getChildrenCount(groupPosition) - 1)) {
            holder.rlFootView.setVisibility(View.VISIBLE);
            holder.tvCreateData.setText(DateUtil.getDateTimeToString(modelList.get(groupPosition)
                    .getItems().get(childPosition).getCreateDate()));
            int size = getChildrenCount(groupPosition);
            int totalQuantity = 0;
            double totalPrice = 0;
            for (int i = 0; i < size; i++) {
                totalQuantity = totalQuantity + modelList.get(groupPosition).getItems()
                        .get(childPosition).getQuantity();
                totalPrice = totalPrice + (modelList.get(groupPosition).getItems().get(i)
                        .getProduct().getPrice() * modelList.get(groupPosition).getItems()
                        .get(childPosition).getQuantity());
            }
            holder.tvTotalAmount.setText("共" + totalQuantity + "件商品，合计" + totalPrice + "元");
            holder.tvMemo.setOnClickListener(v -> {
                if (memoOnClickListener != null) {
                    memoOnClickListener.editMemo(groupPosition, childPosition,
                            modelList.get(groupPosition).getMemo(), modelList.get(groupPosition).getId());
                }
            });
            if (TextUtils.isEmpty(modelList.get(groupPosition).getMemo())) {
                holder.tvMemo.setText(R.string.hint_order_memo);
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

    public void setMemoOnClickListener(MemoOnClickListener memoOnClickListener) {
        this.memoOnClickListener = memoOnClickListener;
    }

    public interface MemoOnClickListener {
        void editMemo(int groupPosition, int childPosition, String memo, String merchantId);
    }
}
