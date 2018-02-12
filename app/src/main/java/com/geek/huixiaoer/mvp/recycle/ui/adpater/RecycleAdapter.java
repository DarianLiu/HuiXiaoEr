package com.geek.huixiaoer.mvp.recycle.ui.adpater;

import android.view.View;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.storage.entity.article.ArticleBean;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

/**
 * 垃圾回收列表适配器
 * Created by Administrator on 2018/2/12.
 */
public class RecycleAdapter extends DefaultAdapter<ArticleBean> {

    public RecycleAdapter(List<ArticleBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<ArticleBean> getHolder(View v, int viewType) {
        return new RecycleItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_recycle;
    }

}
