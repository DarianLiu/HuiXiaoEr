package com.geek.huixiaoer.mvp.recycle.ui.adpater;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.widget.recyclerview.OnItemClickListener;
import com.geek.huixiaoer.storage.entity.recycle.ArticleBean;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.http.imageloader.glide.GlideArms;

import butterknife.BindView;

/**
 * 享环保论坛ItemHolder
 * Created by Administrator on 2018/5/13.
 */

public class ForumItemHolder extends BaseHolder<ArticleBean> {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.tv_view_count)
    TextView tvViewCount;

    public ForumItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(ArticleBean data, int position) {
        tvTitle.setText(TextUtils.isEmpty(data.getTitle()) ? "" : data.getTitle());
        tvViewCount.setText("点击量：" + data.getHits());
        GlideArms.with(ivCover.getContext()).load(data.getImage().getUrl()).centerCrop().into(ivCover);
    }
}
