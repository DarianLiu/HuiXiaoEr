package com.geek.huixiaoer.mvp.recycle.ui.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.geek.huixiaoer.R;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.DeviceUtils;

import java.util.List;

//适配器
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder>  {

    private List<String> result;
    private Context mContext;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    private final int TYPE_ADD = 0;//添加
    private final int TYPE_IMAGE = 1;//图片


    public GridAdapter(Context context, List<String> result) {
        this.result = result;
        this.mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_with_delete, null);
        int itemWidth = (int) (DeviceUtils.getScreenWidth(mContext) -
                DeviceUtils.dpToPixel(mContext, 60) - 30) / 4;
        view.setLayoutParams(new RelativeLayout.LayoutParams(itemWidth, itemWidth));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case TYPE_IMAGE:
                GlideArms.with(mContext).load(result.get(position)).centerCrop().into(holder.image);
                holder.image_close.setVisibility(View.VISIBLE);
                break;
            case TYPE_ADD:
                holder.image.setImageResource(R.drawable.icon_add_image);
                holder.image_close.setVisibility(View.GONE);
                holder.image.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onAddClick(position);
                }
            });
                break;
        }

//        holder.image_close.setOnClickListener(v -> {
//            if (mOnItemClickListener != null) {
//                mOnItemClickListener.onRemoveClick(position);
//            }
//        });
    }

//    public void addData(List<String> list){
//        result.addAll(list);
//        notifyDataSetChanged();
//    }

    @Override
    public int getItemCount() {
        int itemCount;
        if (result.size() == 0 || result.size() < 9) {
            itemCount = result.size() + 1;
        } else {
            itemCount = 9;
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (result.size() == 9) {
            return TYPE_IMAGE;
        } else {
            if (position < result.size()) {
                return TYPE_IMAGE;
            } else {
                return TYPE_ADD;
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image, image_close;

        private ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_location);
            image_close = itemView.findViewById(R.id.iv_delete);
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onAddClick(int position);

        void onRemoveClick(int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}