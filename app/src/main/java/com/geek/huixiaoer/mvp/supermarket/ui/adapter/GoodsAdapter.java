package com.geek.huixiaoer.mvp.supermarket.ui.adapter;

import android.view.View;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.storage.entity.GoodsBean;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;


/**
 * 瀑布流商品列表适配器
 * Created by Administrator on 2018/2/9.
 */
public class GoodsAdapter extends DefaultAdapter<GoodsBean> {

    public GoodsAdapter(List<GoodsBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<GoodsBean> getHolder(View v, int viewType) {
        return new GoodsItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_goods_waterfall;
    }
}
