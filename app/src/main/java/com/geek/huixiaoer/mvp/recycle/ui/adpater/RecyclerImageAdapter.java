//package com.geek.huixiaoer.mvp.recycle.ui.adpater;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import com.geek.huixiaoer.R;
//import com.jess.arms.http.imageloader.glide.GlideArms;
//
//import java.util.List;
//
////适配器
//public class RecyclerImageAdapter extends RecyclerView.Adapter<RecyclerImageAdapter.ViewHolderImage> {
//
//    private int itemWidth;
//    private List<String> imageList;
//
//    public RecyclerImageAdapter(List<String> imageList, int itemWidth) {
//        this.imageList = imageList;
//        this.itemWidth = itemWidth;
//    }
//
//
//    @Override
//    public ViewHolderImage onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
//        view.setLayoutParams(new ViewGroup.LayoutParams(itemWidth,itemWidth));
//        return new ViewHolderImage(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolderImage holder, final int position) {
//        GlideArms.with(holder.image.getContext()).load(imageList.get(position))
//                .error(R.mipmap.ic_launcher)
//                .centerCrop()
//                .into(holder.image);
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return imageList.size();
//    }
//
//    class ViewHolderImage extends RecyclerView.ViewHolder {
//
//        ImageView image;
//
//        private ViewHolderImage(View itemView) {
//            super(itemView);
//            image = itemView.findViewById(R.id.iv_image);
//        }
//    }
//}