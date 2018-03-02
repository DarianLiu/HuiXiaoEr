//package com.geek.huixiaoer.mvp.recycle.ui.adpater;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//
//import com.geek.huixiaoer.R;
//import com.jess.arms.base.BaseHolder;
//import com.jess.arms.base.DefaultAdapter;
//
//import java.util.List;
//
///**
// * 图片ItemHolder
// */
//public class RecycleImageAdapter extends DefaultAdapter<String> {
//
//    private int width;
//
//    public RecycleImageAdapter(List<String> infos, int width) {
//        super(infos);
//        this.width = width;
//    }
//
//    @Override
//    public BaseHolder<String> onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,width);
//        view.setLayoutParams(lp);
//        return getHolder(view, viewType);
//    }
//
//    @Override
//    public void onBindViewHolder(BaseHolder<String> holder, int position) {
//        super.onBindViewHolder(holder, position);
//    }
//
//    @Override
//    public BaseHolder<String> getHolder(View v, int viewType) {
//        return new RecycleImageItemHolder(v, width);
//    }
//
//    @Override
//    public int getLayoutId(int viewType) {
//        return R.layout.item_image;
//    }
//}