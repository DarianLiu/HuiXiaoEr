//package com.geek.huixiaoer.mvp.recycle.ui.adpater;
//
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.geek.huixiaoer.R;
//import com.geek.huixiaoer.common.utils.AndroidUtil;
//import com.geek.huixiaoer.common.utils.DateUtil;
//import com.geek.huixiaoer.common.widget.recyclerview.GridSpacingItemDecoration;
//import com.geek.huixiaoer.storage.entity.article.ArticleBean;
//import com.jess.arms.base.BaseHolder;
//import com.jess.arms.http.imageloader.glide.GlideArms;
//import com.jess.arms.utils.DeviceUtils;
//
//import butterknife.BindColor;
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import timber.log.Timber;
//
///**
// * 垃圾回收ItemHolder
// * Created by Administrator on 2018/2/12.
// */
//public class RecycleItemHolder extends BaseHolder<ArticleBean> {
//
//    @BindView(R.id.iv_user_head)
//    ImageView ivUserHead;
//    @BindView(R.id.tv_user_name_date)
//    TextView tvUserNameDate;
//    @BindView(R.id.tv_content)
//    TextView tvContent;
//    @BindView(R.id.rv_image)
//    RecyclerView rvImage;
//    @BindColor(R.color.color_text_caption)
//    int colorName;
//    @BindColor(R.color.color_text_body)
//    int colorDate;
//
//    public RecycleItemHolder(View itemView) {
//        super(itemView);
//        ButterKnife.bind(this, itemView);
//    }
//
//    @Override
//    public void setData(ArticleBean data, int position) {
//        GlideArms.with(itemView.getContext()).load(data.getMember_headUrl())
//                .error(R.mipmap.ic_launcher_round)
//                .circleCrop().into(ivUserHead);
//        String name = data.getMember_nickname() == null ? data.getMember_username() : data.getMember_nickname();
//        String date = DateUtil.getTime(String.valueOf(data.getCreateDate()), DateUtil.getCurrentDate());
//        AndroidUtil.setTextSizeColor(tvUserNameDate, new String[]{name + "\n", date},
//                new int[]{colorName, colorDate},
//                new int[]{13, 10});
//        tvContent.setText(data.getContent());
//
//        Timber.d("--------------------item====" + "parent_position=" + position);
//        //Grid样式的RecyclerView的宽度(减去padding和margin的距离)
//        float rvImageWidth = DeviceUtils.getScreenWidth(itemView.getContext()) -
//                DeviceUtils.dpToPixel(itemView.getContext(), 15 + 40 + 8 + 15);
//        if (data.getImages() != null && data.getImages().size() != 0) {
//            rvImage.setVisibility(View.VISIBLE);
//            int imageSize = data.getImages().size();
//            int spanCount;
//            int width;
//            if (imageSize == 1) {
//                spanCount = 1;
//                width = (int) rvImageWidth / 2;
//            } else {
//                if (imageSize == 2 || imageSize == 4) {
//                    spanCount = 2;
//                    width = (int) (rvImageWidth - 15) / 2;
//                } else {
//                    spanCount = 3;
//                    width = (int) (rvImageWidth - 30) / 3;
//                }
//            }
//            if (rvImage.getAdapter() == null) {
//                RecyclerView.LayoutManager manager = new GridLayoutManager(itemView.getContext(), spanCount);
//                manager.setAutoMeasureEnabled(true);
//                rvImage.setLayoutManager(manager);
//                rvImage.addItemDecoration(new GridSpacingItemDecoration(spanCount, 15, false));
//                rvImage.setAdapter(new RecycleImageAdapter(data.getImages(), width));
//            }
//        } else {
//            rvImage.setVisibility(View.GONE);
//        }
//    }
//}
