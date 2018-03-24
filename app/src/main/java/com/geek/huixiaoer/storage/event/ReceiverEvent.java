package com.geek.huixiaoer.storage.event;

/**
 * 收货地址Event
 * Created by Administrator on 2018/3/24.
 */

public class ReceiverEvent {
    private int eventType;//0：保存；1：更新；2：删除
    private int selectedPos;
    private boolean isChange;

    public ReceiverEvent(int eventType,int selectedPos, boolean isChange) {
        this.eventType = eventType;
        this.selectedPos = selectedPos;
        this.isChange = isChange;
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
}
