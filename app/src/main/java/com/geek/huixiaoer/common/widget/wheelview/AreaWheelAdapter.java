package com.geek.huixiaoer.common.widget.wheelview;

import android.content.Context;

import com.geek.huixiaoer.common.widget.wheelview.base.BaseWheelAdapter;
import com.geek.huixiaoer.storage.entity.address.AreaEntity;

import java.util.List;

public class AreaWheelAdapter extends BaseWheelAdapter<AreaEntity> {

    public AreaWheelAdapter(Context context, List<AreaEntity> list) {
        super(context,list);
    }

    @Override
    protected CharSequence getItemText(int index) {
        AreaEntity data = getItemData(index);
        if (data != null) {
            return data.Name;
        }
        return null;
    }
}
