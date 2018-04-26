package com.geek.huixiaoer.mvp.recycle.ui.adpater;

import android.view.View;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.storage.entity.recycle.RankBean;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

/**
 * 黑名单
 * Created by Administrator on 2018/4/25.
 */

public class RankAdapter extends DefaultAdapter<RankBean> {

    public RankAdapter(List<RankBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<RankBean> getHolder(View v, int viewType) {
        return new RankItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_rank_member;
    }
}
