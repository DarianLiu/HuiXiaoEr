package com.geek.huixiaoer.common.widget.recyclerview;

/**
 * RecyclerView子项点击事件
 * Created by Administrator on 2018/4/22.
 */

public interface OnItemClickListener<T> {

    void OnItemClick(int position, T data);
}
