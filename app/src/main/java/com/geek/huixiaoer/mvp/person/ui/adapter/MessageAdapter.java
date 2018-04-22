package com.geek.huixiaoer.mvp.person.ui.adapter;

import android.view.View;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.storage.entity.MessageBean;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

/**
 * 消息列表
 * Created by Administrator on 2018/4/15.
 */

public class MessageAdapter<MessageBean> extends DefaultAdapter<com.geek.huixiaoer.storage.entity.MessageBean> {

    public MessageAdapter(List<com.geek.huixiaoer.storage.entity.MessageBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<com.geek.huixiaoer.storage.entity.MessageBean> getHolder(View v, int viewType) {
        return new MessageItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_message;
    }
}

