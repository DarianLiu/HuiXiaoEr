package com.geek.huixiaoer.storage.event;

import com.geek.huixiaoer.storage.entity.shop.ReceiverBean;

/**
 * 收货地址Event
 * Created by Administrator on 2018/3/24.
 */

public class ReceiverEvent {
    private int eventType;//0：保存；1：设为默认；2：更新收货地址；3：删除
    private int selectedPos;
    private boolean isChange;//判断设置为默认
    private ReceiverBean receiverBean;

    public ReceiverEvent(int eventType, int selectedPos, boolean isChange) {
        this.eventType = eventType;
        this.selectedPos = selectedPos;
        this.isChange = isChange;
    }

    public ReceiverEvent(int eventType, int selectedPos, boolean isChange, ReceiverBean receiverBean) {
        this.eventType = eventType;
        this.selectedPos = selectedPos;
        this.isChange = isChange;
        this.receiverBean = receiverBean;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public int getEventType() {
        return eventType;
    }

    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
    }

    public int getSelectedPos() {
        return selectedPos;
    }

    public void setChange(boolean change) {
        isChange = change;
    }

    public boolean isChange() {
        return isChange;
    }

    public void setReceiverBean(ReceiverBean receiverBean) {
        this.receiverBean = receiverBean;
    }

    public ReceiverBean getReceiverBean() {
        return receiverBean;
    }
}
