package com.geek.huixiaoer.mvp.person.ui.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.mvp.person.ui.activity.MyOrderActivity;
import com.geek.huixiaoer.storage.entity.shop.ReceiverBean;
import com.geek.huixiaoer.storage.event.ReceiverEvent;
import com.jess.arms.base.BaseHolder;

import org.simple.eventbus.EventBus;

import butterknife.BindView;

/**
 * 收货地址ItemHolder
 * Created by Administrator on 2018/3/24.
 */

public class ReceiverItemHolder extends BaseHolder<ReceiverBean> {

    @BindView(R.id.tv_receive_user)
    TextView tvReceiveUser;
    @BindView(R.id.tv_receive_phone)
    TextView tvReceivePhone;
    @BindView(R.id.tv_receive_address)
    TextView tvReceiveAddress;
    @BindView(R.id.rb_default)
    public RadioButton rbDefault;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.tv_delete)
    TextView tvDelete;

    public ReceiverItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(ReceiverBean data, int position) {
        tvReceiveUser.setText(data.getConsignee());
        tvReceivePhone.setText(data.getPhone());
        tvReceiveAddress.setText(String.format("%s%s", data.getAreaName(), data.getAddress()));

        if (data.isIsDefault()) {
            rbDefault.setChecked(data.isIsDefault());
            EventBus.getDefault().post(new ReceiverEvent(1, position, false));
        }


        rbDefault.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                EventBus.getDefault().post(new ReceiverEvent(1, position, true));
        });

        tvEdit.setOnClickListener(v -> {
            Intent intent = new Intent(tvEdit.getContext(), MyOrderActivity.class);
            tvEdit.getContext().startActivity(intent);
        });

        tvDelete.setOnClickListener(v -> EventBus.getDefault().post(new ReceiverEvent(2, position, false)));
    }
}
