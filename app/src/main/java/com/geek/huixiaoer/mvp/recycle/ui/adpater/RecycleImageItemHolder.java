//package com.geek.huixiaoer.mvp.recycle.ui.adpater;
//
//import android.view.View;
//import android.widget.ImageView;
//
//import com.geek.huixiaoer.R;
//import com.jess.arms.base.BaseHolder;
//import com.jess.arms.http.imageloader.glide.GlideArms;
//
//import butterknife.BindView;
//import timber.log.Timber;
//
///**
// * 图片ItemHolder
// * Created by Administrator on 2018/2/12.
// */
//
//public class RecycleImageItemHolder extends BaseHolder<String> {
//
//    @BindView(R.id.iv_image)
//    ImageView ivImage;
//
//    public RecycleImageItemHolder(View itemView, int width) {
//        super(itemView);
////        itemView.setLayoutParams(new ViewGroup.LayoutParams(width, width));
//    }
//
//    @Override
//    public void setData(String data, int position) {
//        Timber.d("--------------------item====" + "position=" + position + "image_width："
//                + ivImage.getLayoutParams().width);
//        GlideArms.with(ivImage.getContext()).load(data).error(R.mipmap.ic_launcher)
//                .centerCrop().into(ivImage);
//    }
//}
