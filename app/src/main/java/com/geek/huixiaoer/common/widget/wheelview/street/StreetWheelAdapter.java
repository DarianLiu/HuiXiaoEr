package com.geek.huixiaoer.common.widget.wheelview.street;

import android.content.Context;

import com.geek.huixiaoer.common.widget.wheelview.base.BaseWheelAdapter;
import com.geek.huixiaoer.storage.entity.address.ProvinceEntity;
import com.geek.huixiaoer.storage.entity.street.StreetBean;

import java.util.List;

public class StreetWheelAdapter extends BaseWheelAdapter<StreetBean> {
	public StreetWheelAdapter(Context context, List<StreetBean> list) {
		super(context,list);
	}

	@Override
	protected CharSequence getItemText(int index) {
		StreetBean data = getItemData(index);
		if(data != null){
			return data.getName();
		}
		return null;
	}
}
