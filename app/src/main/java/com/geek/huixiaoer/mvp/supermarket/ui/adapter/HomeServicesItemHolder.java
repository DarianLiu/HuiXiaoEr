package com.geek.huixiaoer.mvp.supermarket.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.storage.entity.housewifery.HomeServiceBean;
import com.jess.arms.base.BaseHolder;

import butterknife.BindView;

/**
 * 家政服务项目ItemHolder
 * Created by Administrator on 2018/3/7.
 */

public class HomeServicesItemHolder extends BaseHolder<HomeServiceBean> {

    @BindView(R.id.tv_service_name)
    TextView tvServiceName;

    public HomeServicesItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(HomeServiceBean data, int position) {
        String remark = TextUtils.isEmpty(data.getRemark()) ? "" : data.getRemark();
        tvServiceName.setText(String.format("%s%s", data.getName(), remark));
    }
}
