package com.geek.huixiaoer.mvp.recycle.ui.adpater;

import android.view.View;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.widget.recyclerview.OnItemClickListener;
import com.geek.huixiaoer.storage.entity.recycle.ArticleBean;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

/**
 * 享环保论坛Adapter
 * Created by Administrator on 2018/5/13.
 */
public class ForumAdapter extends DefaultAdapter<ArticleBean> {

    public ForumAdapter(List<ArticleBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<ArticleBean> getHolder(View v, int viewType) {
        return new ForumItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_forumn;
    }
}
