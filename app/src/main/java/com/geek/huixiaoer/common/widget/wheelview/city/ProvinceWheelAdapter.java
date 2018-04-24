package com.geek.huixiaoer.common.widget.wheelview.city;

import android.content.Context;

import com.geek.huixiaoer.common.widget.wheelview.base.BaseWheelAdapter;
import com.geek.huixiaoer.storage.entity.address.ProvinceEntity;

import java.util.List;

public class ProvinceWheelAdapter extends BaseWheelAdapter<ProvinceEntity> {
	public ProvinceWheelAdapter(Context context, List<ProvinceEntity> list) {
		super(context,list);
	}

	@Override
	protected CharSequence getItemText(int index) {
		ProvinceEntity data = getItemData(index);
		if(data != null){
			return data.Name;
		}
		return null;
	}
}
