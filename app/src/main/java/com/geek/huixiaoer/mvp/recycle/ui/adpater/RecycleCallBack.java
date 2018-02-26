package com.geek.huixiaoer.mvp.recycle.ui.adpater;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * RecyclerView拖拽排序和删除回调接口
 * Created by Administrator on 2018/2/26.
 */

public class RecycleCallBack extends ItemTouchHelper.Callback{

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return 0;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
