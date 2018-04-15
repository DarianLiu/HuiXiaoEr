package com.geek.huixiaoer.mvp.person.ui.adapter;

import android.view.View;

import com.geek.huixiaoer.R;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

/**
 * 消息列表
 * Created by Administrator on 2018/4/15.
 */

public class MessageAdapter<String> extends DefaultAdapter<String> {

    public MessageAdapter(List<String> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<String> getHolder(View v, int viewType) {
        return new MessageItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_message;
    }
}

