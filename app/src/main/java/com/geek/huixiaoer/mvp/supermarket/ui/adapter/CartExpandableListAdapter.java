//package com.geek.huixiaoer.mvp.supermarket.ui.adapter;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
//import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.target.BitmapImageViewTarget;
//import com.bumptech.glide.request.target.SimpleTarget;
//
//public class CartExpandableListAdapter extends BaseExpandableListAdapter {
//
//    private LayoutInflater mInflater;
//    private Context mContext;
//    private List<OrderInfo> modelList;
//    private CartEditControl cartEditControl;
//
//    public CartExpandableListAdapter(Context context, CartEditControl cartEditControl, List<OrderInfo> list) {
//        mContext = context;
//        modelList = list;
//        this.cartEditControl = cartEditControl;
//        mInflater = LayoutInflater.from(context);
//    }
//
//    @Override
//    public int getGroupCount() {
//        return modelList.size();
//    }
//
//    @Override
//    public int getChildrenCount(int groupPosition) {
//        return modelList.get(groupPosition).getProductInfoList().size();
//    }
//
//    @Override
//    public Object getGroup(int groupPosition) {
//        return modelList.get(groupPosition);
//    }
//
//    @Override
//    public Object getChild(int groupPosition, int childPosition) {
//        return modelList.get(groupPosition).getProductInfoList().get(childPosition);
//    }
//
//    @Override
//    public long getGroupId(int groupPosition) {
//        return groupPosition;
//    }
//
//    @Override
//    public long getChildId(int groupPosition, int childPosition) {
//        return childPosition;
//    }
//
//    @Override
//    public boolean hasStableIds() {
//        return false;
//    }
//
//    @Override
//    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//        GroupViewHolder groupViewHolder = null;
//        if (convertView == null) {
//            groupViewHolder = new GroupViewHolder();
//            convertView = mInflater.inflate(R.layout.listitem_ordersub_shop, null);
//            groupViewHolder.tvMerchantName = (TextView) convertView.findViewById(R.id.tvShopName);
//            groupViewHolder.shopImg = (ImageView) convertView.findViewById(R.id.shopImg);
//            groupViewHolder.status = (TextView) convertView.findViewById(R.id.tvOrderStatus);
//            convertView.setTag(groupViewHolder);
//        } else {
//            groupViewHolder = (GroupViewHolder) convertView.getTag();
//        }
//        groupViewHolder.status.setVisibility(View.GONE);
//        groupViewHolder.tvMerchantName.setText(modelList.get(groupPosition).getMerchantName());
//        final GroupViewHolder finalGroupViewHolder = groupViewHolder;
//        Glide.with(mContext).load(modelList.get(groupPosition).getMerchantHeadImg()).asBitmap().
//                centerCrop().into(new BitmapImageViewTarget(finalGroupViewHolder.shopImg) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                RoundedBitmapDrawable circularBitmapDrawable =
//                        RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
//                circularBitmapDrawable.setCircular(true);
//                finalGroupViewHolder.shopImg.setImageDrawable(circularBitmapDrawable);
//            }
//        });
//        convertView.setPadding(0, 20, 0, 0);//设置列表间距
//        return convertView;
//    }
//
//    @Override
//    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        final ChildViewHolder holder;
//        if (convertView == null) {
//            holder = new ChildViewHolder();
//            convertView = mInflater.inflate(R.layout.listview_item_shopcart, null);
//            holder.imageView = (XCRoundRectImageView) convertView.findViewById(R.id.img);
//            holder.mTvGoodsName = (TextView) convertView.findViewById(R.id.textName);
//            holder.imgDelete = (ImageView) convertView.findViewById(R.id.imgDelete);
//            holder.mTvGoodsPrice = (TextView) convertView.findViewById(R.id.textPrice);
//            holder.tvQuantity = (TextView) convertView.findViewById(R.id.tvQuantity);
//            holder.plusImg = (ImageView) convertView.findViewById(R.id.plusImg);
//            holder.reduceImg = (ImageView) convertView.findViewById(R.id.reduceImg);
//            convertView.setTag(holder);
//        } else {
//            holder = (ChildViewHolder) convertView.getTag();
//        }
//
//        ProductInfo productInfo = modelList.get(groupPosition).getProductInfoList().get(childPosition);
//        Glide.with(mContext).load(productInfo.getGoods_image()).asBitmap().into(
//                new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        holder.imageView.setImageBitmap(resource);
//                    }
//                });
////        Glide.with(mContext).load(productInfo.getGoods_image()).diskCacheStrategy(DiskCacheStrategy.ALL)
////                .into(holder.imageView);
//        if (productInfo.getSpecifications() == null) {
//            holder.mTvGoodsName.setText(productInfo.getGoods_name());
//        } else {
//            holder.mTvGoodsName.setText(productInfo.getGoods_name() + "("
//                    + productInfo.getSpecifications() + ")");
//        }
////        holder.mTvCaption.setText(productInfo.getGoods_caption());
//        holder.mTvGoodsPrice.setText("￥" + productInfo.getPrice());
//        holder.tvQuantity.setText(String.valueOf(productInfo.getQuantity()));
//        holder.plusImg.setOnClickListener(new MyListener(groupPosition, childPosition));
//        holder.reduceImg.setOnClickListener(new MyListener(groupPosition, childPosition));
//        holder.imgDelete.setOnClickListener(new MyListener(groupPosition, childPosition));
//        return convertView;
//    }
//
//    @Override
//    public boolean isChildSelectable(int groupPosition, int childPosition) {
//        return true;
//    }
//
//    private class MyListener implements View.OnClickListener {
//
//        int groupPosition;
//        int childPosition;
//
//        public MyListener(int groupPosition, int childPosition) {
//            this.groupPosition = groupPosition;
//            this.childPosition = childPosition;
//        }
//
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.plusImg:
//                    cartEditControl.plus(groupPosition, childPosition);
//                    break;
//                case R.id.reduceImg:
//                    cartEditControl.reduce(groupPosition, childPosition);
//                    break;
//                case R.id.imgDelete:
//                    cartEditControl.delete(groupPosition, childPosition);
//                    break;
//                default:
//                    break;
//            }
//        }
//
//    }
//
//    //加减方法内部接口
//    public interface CartEditControl {
//        public void plus(int groupPosition, int childPosition);
//
//        public void reduce(int groupPosition, int childPosition);
//
//        public void delete(int groupPosition, int childPosition);
//    }
//
//   private class GroupViewHolder {
//        TextView tvMerchantName;//地址
//        ImageView shopImg;
//        TextView status;
//    }
//
//    private class ChildViewHolder {
////        private XCRoundRectImageView imageView;
//        private TextView mTvGoodsName;//商品名
//        private TextView mTvGoodsPrice; // 售价
//        private TextView mTvCaption; // 介绍
//        private TextView tvQuantity;//数量
//        private ImageView reduceImg;
//        private ImageView plusImg;
//        private ImageView imgDelete;
//
//    }
//}