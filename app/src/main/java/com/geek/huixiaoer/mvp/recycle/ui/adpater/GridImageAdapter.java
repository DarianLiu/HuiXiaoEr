package com.geek.huixiaoer.mvp.recycle.ui.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.geek.huixiaoer.R;
import com.jess.arms.http.imageloader.glide.GlideArms;

import java.util.List;

/**
 * GridView 适配器
 */
public class GridImageAdapter extends BaseAdapter {

    private Context context;
    private List<String> imageList;
    private int itemWidth;

    public GridImageAdapter(Context context, List<String> imageList,int itemWidth) {
        this.context = context;
        this.imageList = imageList;
        this.itemWidth = itemWidth;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_image, null);
            convertView.setLayoutParams(new AbsListView.LayoutParams(itemWidth,itemWidth));
            viewHolder.img = convertView.findViewById(R.id.iv_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GlideArms.with(context).load(imageList.get(position))
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .into(viewHolder.img);
        return convertView;
    }

    private class ViewHolder {
        ImageView img;
    }
}
