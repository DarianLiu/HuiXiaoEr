package com.geek.huixiaoer.mvp.recycle.ui.adpater;

import android.view.View;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.storage.entity.recycle.BlackBean;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

/**
 * 黑名单
 * Created by Administrator on 2018/4/25.
 */

public class BlackAdapter extends DefaultAdapter<BlackBean> {

    public BlackAdapter(List<BlackBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<BlackBean> getHolder(View v, int viewType) {
        return new BlackItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_black_member;
    }
}
