package com.geek.huixiaoer.mvp.supermarket.ui.adapter;

import android.view.View;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.storage.entity.housewifery.HomeServiceBean;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

/**
 * 家政服务项目Adapter
 * Created by Administrator on 2018/3/7.
 */

public class HomeServicesAdapter extends DefaultAdapter<HomeServiceBean> {

    public HomeServicesAdapter(List<HomeServiceBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<HomeServiceBean> getHolder(View v, int viewType) {
        return new HomeServicesItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_home_service;
    }
}
