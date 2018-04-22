package com.geek.huixiaoer.mvp.recycle.ui.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.utils.AndroidUtil;
import com.geek.huixiaoer.common.utils.DateUtil;
import com.geek.huixiaoer.common.widget.recyclerview.OnItemClickListener;
import com.geek.huixiaoer.storage.entity.recycle.ArticleBean;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;

import java.util.List;

/**
 * 垃圾回收列表适配器
 * Created by Administrator on 2018/2/12.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ArticleBean> infos;
    private Context mContext;
    private final int TYPE_ONE = 0;
    private final int TYPE_TWO = 1;
    private final int TYPE_THREE = 2;
    private OnItemClickListener<ArticleBean> onItemClickListener;

    public RecycleAdapter(List<ArticleBean> infos, Context context) {
        this.infos = infos;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        int imageSize = infos.get(position).getImages().size();
        if (imageSize == 1) {
            return TYPE_ONE;
        } else if (imageSize == 2 || imageSize == 4) {
            return TYPE_TWO;
        } else {
            return TYPE_THREE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_ONE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_recycle_one, parent, false);
            return new OneHolder(view);
        } else if (viewType == TYPE_TWO) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_recycle_two, parent, false);
            return new TwoHolder(view);
        } else if (viewType == TYPE_THREE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_recycle_three, parent, false);
            return new ThreeHolder(view);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ArticleBean data = infos.get(position);
        //GridView的宽度
        float gridWidth = DeviceUtils.getScreenWidth(mContext) -
                DeviceUtils.dpToPixel(mContext, 8 + 47 + 20 + 8);

        if (holder instanceof OneHolder) {
            OneHolder oneHolder = (OneHolder) holder;
            GlideArms.with(mContext).load(data.getMember_headUrl())
                    .error(R.drawable.icon_head_default)
                    .placeholder(R.drawable.icon_head_default)
                    .circleCrop().into(oneHolder.ivUserHead);
            String name = data.getMember_nickname() == null ? data.getMember_username() : data.getMember_nickname();
            String date = DateUtil.getTime(String.valueOf(data.getCreateDate()), DateUtil.getCurrentDate());
            AndroidUtil.setTextSizeColor(oneHolder.tvUserNameDate, new String[]{name + "\n", date},
                    new int[]{ArmsUtils.getColor(mContext, R.color.color_text_caption),
                            ArmsUtils.getColor(mContext, R.color.color_text_body)},
                    new int[]{13, 10});
            oneHolder.tvContent.setText(data.getContent());
            GlideArms.with(mContext).load(data.getImages().get(0))
//                    .error(R.drawable.icon_head_default)
//                    .placeholder(R.drawable.icon_head_default)
                    .centerCrop().into(oneHolder.imageRecycle);
        } else if (holder instanceof TwoHolder) {
            TwoHolder twoHolder = (TwoHolder) holder;
            GlideArms.with(mContext).load(data.getMember_headUrl())
                    .error(R.drawable.icon_head_default)
                    .placeholder(R.drawable.icon_head_default)
                    .circleCrop().into(twoHolder.ivUserHead);
            String name = data.getMember_nickname() == null ? data.getMember_username() : data.getMember_nickname();
            String date = DateUtil.getTime(String.valueOf(data.getCreateDate()), DateUtil.getCurrentDate());
            AndroidUtil.setTextSizeColor(twoHolder.tvUserNameDate, new String[]{name + "\n", date},
                    new int[]{ArmsUtils.getColor(mContext, R.color.color_text_caption),
                            ArmsUtils.getColor(mContext, R.color.color_text_body)},
                    new int[]{13, 10});
            twoHolder.tvContent.setText(data.getContent());
            int itemWidth = (int) (gridWidth - DeviceUtils.dpToPixel(mContext, 5)) / 3;
            twoHolder.gridView.setAdapter(new GridImageAdapter(mContext, data.getImages(), itemWidth));
        } else if (holder instanceof ThreeHolder) {
            ThreeHolder threeHolder = (ThreeHolder) holder;
            GlideArms.with(mContext).load(data.getMember_headUrl())
                    .error(R.drawable.icon_head_default)
                    .placeholder(R.drawable.icon_head_default)
                    .circleCrop().into(threeHolder.ivUserHead);
            String name = data.getMember_nickname() == null ? data.getMember_username() : data.getMember_nickname();
            String date = DateUtil.getTime(String.valueOf(data.getCreateDate()), DateUtil.getCurrentDate());
            AndroidUtil.setTextSizeColor(threeHolder.tvUserNameDate, new String[]{name + "\n", date},
                    new int[]{ArmsUtils.getColor(mContext, R.color.color_text_caption),
                            ArmsUtils.getColor(mContext, R.color.color_text_body)},
                    new int[]{13, 10});
            threeHolder.tvContent.setText(data.getContent());
            int itemWidth = (int) (gridWidth - DeviceUtils.dpToPixel(mContext, 10)) / 3;
            threeHolder.gridView.setAdapter(new GridImageAdapter(mContext, data.getImages(), itemWidth));
        }
//
//        GlideArms.with(mContext).load(data.getMember_headUrl())
//                .error(R.mipmap.ic_launcher_round)
//                .circleCrop().into(holder.ivUserHead);
//        String name = data.getMember_nickname() == null ? data.getMember_username() : data.getMember_nickname();
//        String date = DateUtil.getTime(String.valueOf(data.getCreateDate()), DateUtil.getCurrentDate());
//        AndroidUtil.setTextSizeColor(holder.tvUserNameDate, new String[]{name + "\n", date},
//                new int[]{ArmsUtils.getColor(mContext, R.color.color_text_caption),
//                        ArmsUtils.getColor(mContext, R.color.color_text_body)},
//                new int[]{13, 10});
//        holder.tvContent.setText(data.getContent());
//
//        Timber.d("--------------------item====" + "parent_position=" + position);
//        //Grid样式的RecyclerView的宽度(减去padding和margin的距离)
//        float rvImageWidth = DeviceUtils.getScreenWidth(mContext) -
//                DeviceUtils.dpToPixel(mContext, 8 + 47 + 8 + 8);
//        if (data.getImages() != null && data.getImages().size() != 0) {
//            holder.rvImage.setVisibility(View.VISIBLE);
//            int imageSize = data.getImages().size();
//            int spanCount;
//            int width;
//            if (imageSize == 1) {
//                spanCount = 1;
//                width = (int) rvImageWidth / 2;
//            } else {
//                if (imageSize == 2 || imageSize == 4) {
//                    spanCount = 2;
//                    width = (int) (rvImageWidth - 5) / 2;
//                } else {
//                    spanCount = 3;
//                    width = (int) (rvImageWidth - 10) / 3;
//                }
//            }
////            RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(spanCount,
////                    StaggeredGridLayoutManager.VERTICAL);
////            holder.rvImage.setHasFixedSize(true);
////            holder.rvImage.setLayoutManager(manager);
////            holder.rvImage.addItemDecoration(new GridSpacingItemDecoration(spanCount, 15, false));
////            holder.rvImage.setAdapter(new RecyclerImageAdapter(data.getImages(), width));
//        } else {
//            holder.rvImage.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemCount() {
        if (infos == null) {
            return 0;
        }
        return infos.size();
    }

    public void setOnItemClickListener(OnItemClickListener<ArticleBean> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private class OneHolder extends RecyclerView.ViewHolder {

        ImageView ivUserHead;
        TextView tvUserNameDate;
        TextView tvContent;
        ImageView imageRecycle;

        OneHolder(View itemView) {
            super(itemView);
            if (onItemClickListener != null) {
                itemView.setOnClickListener(v -> {
                    onItemClickListener.OnItemClick(getAdapterPosition(),infos.get(getAdapterPosition()));
                });
            }

            ivUserHead = itemView.findViewById(R.id.iv_user_head);
            tvUserNameDate = itemView.findViewById(R.id.tv_user_name_date);
            tvContent = itemView.findViewById(R.id.tv_content);
            imageRecycle = itemView.findViewById(R.id.image_recycle);
        }
    }


    private class TwoHolder extends RecyclerView.ViewHolder {

        ImageView ivUserHead;
        TextView tvUserNameDate;
        TextView tvContent;
        GridView gridView;
//        RecyclerView rvImage;

        TwoHolder(View itemView) {
            super(itemView);
            if (onItemClickListener != null) {
                itemView.setOnClickListener(v -> {
                    onItemClickListener.OnItemClick(getAdapterPosition(),infos.get(getAdapterPosition()));
                });
            }
            ivUserHead = itemView.findViewById(R.id.iv_user_head);
            tvUserNameDate = itemView.findViewById(R.id.tv_user_name_date);
            tvContent = itemView.findViewById(R.id.tv_content);
            gridView = itemView.findViewById(R.id.grid_image);
//            rvImage = itemView.findViewById(R.id.rv_image);
        }
    }

    private class ThreeHolder extends RecyclerView.ViewHolder {

        ImageView ivUserHead;
        TextView tvUserNameDate;
        TextView tvContent;
        GridView gridView;
//        RecyclerView rvImage;

        ThreeHolder(View itemView) {
            super(itemView);
            if (onItemClickListener != null) {
                itemView.setOnClickListener(v -> {
                    onItemClickListener.OnItemClick(getAdapterPosition(),infos.get(getAdapterPosition()));
                });
            }
            ivUserHead = itemView.findViewById(R.id.iv_user_head);
            tvUserNameDate = itemView.findViewById(R.id.tv_user_name_date);
            tvContent = itemView.findViewById(R.id.tv_content);
            gridView = itemView.findViewById(R.id.grid_image);
//            rvImage = itemView.findViewById(R.id.rv_image);
        }
    }
}

