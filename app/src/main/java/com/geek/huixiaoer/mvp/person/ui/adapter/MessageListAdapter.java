package com.geek.huixiaoer.mvp.person.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geek.huixiaoer.storage.entity.MessageBean;

import com.geek.huixiaoer.R;

import java.util.List;

/**
 * 消息列表
 * Created by Administrator on 2018/4/15.
 */

public class MessageListAdapter<MessageBean> extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    private List<com.geek.huixiaoer.storage.entity.MessageBean> messageList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_message;

        public ViewHolder(View view) {
            super(view);
            tv_message = (TextView) view.findViewById(R.id.tv_message);
        }
    }

    public MessageListAdapter(List<com.geek.huixiaoer.storage.entity.MessageBean> messageList) {
        this.messageList = messageList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =   LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message2,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        com.geek.huixiaoer.storage.entity.MessageBean bean = messageList.get(position);
        holder.tv_message.setText(bean.getTitle());
    }

    @Override
    public int getItemCount() {
        return messageList==null?0:messageList.size();
    }
}

