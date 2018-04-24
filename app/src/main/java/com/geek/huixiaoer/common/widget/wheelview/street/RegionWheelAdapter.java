package com.geek.huixiaoer.common.widget.wheelview.street;

import android.content.Context;

import com.geek.huixiaoer.common.widget.wheelview.base.BaseWheelAdapter;
import com.geek.huixiaoer.storage.entity.address.ProvinceEntity;
import com.geek.huixiaoer.storage.entity.street.RegionBean;

import java.util.List;

public class RegionWheelAdapter extends BaseWheelAdapter<RegionBean> {
	public RegionWheelAdapter(Context context, List<RegionBean> list) {
		super(context,list);
	}

	@Override
	protected CharSequence getItemText(int index) {
		RegionBean data = getItemData(index);
		if(data != null){
			return data.getName();
		}
		return null;
	}
}
