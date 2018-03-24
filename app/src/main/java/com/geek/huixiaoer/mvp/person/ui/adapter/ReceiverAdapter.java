package com.geek.huixiaoer.mvp.person.ui.adapter;

import android.view.View;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.storage.entity.shop.ReceiverBean;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

/**
 * 收货地址Adapter
 * Created by Administrator on 2018/3/24.
 */

public class ReceiverAdapter extends DefaultAdapter<ReceiverBean> {

    public ReceiverAdapter(List<ReceiverBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<ReceiverBean> getHolder(View v, int viewType) {
        return new ReceiverItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_receiver;
    }
}
