package com.geek.huixiaoer.mvp.recycle.ui.adpater;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.storage.entity.recycle.RankBean;
import com.jess.arms.base.BaseHolder;

import butterknife.BindView;

/**
 * 黑名单
 * Created by Administrator on 2018/4/25.
 */

public class RankItemHolder extends BaseHolder<RankBean> {

    @BindView(R.id.ll_bg)
    LinearLayout llBg;
    @BindView(R.id.tv_rank)
    TextView tvRank;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.tv_point)
    TextView tvPoint;
    @BindView(R.id.view_line)
    View view_line;

    public RankItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(RankBean data, int position) {
        if (position == 0) {
            Drawable drawable = tvRank.getContext().getResources().getDrawable(
                    R.drawable.icon_member_rank_first);
            tvRank.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            tvRank.setCompoundDrawablePadding(0);
            llBg.setBackgroundResource(R.drawable.icon_member_rank_first_bg);
            tvName.setTextColor(tvName.getContext().getResources().getColor(R.color.color_text_white));
            tvWeight.setTextColor(tvWeight.getContext().getResources().getColor(R.color.color_text_white));
            tvPoint.setTextColor(tvPoint.getContext().getResources().getColor(R.color.color_text_white));
            view_line.setVisibility(View.GONE);
//            tvRank.setCompoundDrawablePadding(4);
        } else if (position == 1) {
            Drawable drawable = tvRank.getContext().getResources().getDrawable(
                    R.drawable.icon_member_rank_second);
            tvRank.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            tvRank.setCompoundDrawablePadding(0);
            llBg.setBackgroundResource(R.drawable.icon_member_rank_second_bg);
            tvName.setTextColor(tvName.getContext().getResources().getColor(R.color.color_text_white));
            tvWeight.setTextColor(tvWeight.getContext().getResources().getColor(R.color.color_text_white));
            tvPoint.setTextColor(tvPoint.getContext().getResources().getColor(R.color.color_text_white));
            view_line.setVisibility(View.GONE);
        } else if (position == 2) {
            Drawable drawable = tvRank.getContext().getResources().getDrawable(
                    R.drawable.icon_member_rank_third);
            tvRank.setCompoundDrawablePadding(0);
            tvRank.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            llBg.setBackgroundResource(R.drawable.icon_member_rank_third_bg);
            tvName.setTextColor(tvName.getContext().getResources().getColor(R.color.color_text_white));
            tvWeight.setTextColor(tvWeight.getContext().getResources().getColor(R.color.color_text_white));
            tvPoint.setTextColor(tvPoint.getContext().getResources().getColor(R.color.color_text_white));
            view_line.setVisibility(View.GONE);
        } else {
            tvRank.setText(String.valueOf(position + 1));
            view_line.setVisibility(View.VISIBLE);
            tvName.setTextColor(tvName.getContext().getResources().getColor(R.color.color_text_caption));
            tvWeight.setTextColor(tvWeight.getContext().getResources().getColor(R.color.color_text_caption));
            tvPoint.setTextColor(tvPoint.getContext().getResources().getColor(R.color.color_text_caption));
        }
        tvName.setText(TextUtils.isEmpty(data.getNickname()) ? data.getName() : data.getNickname());
        tvWeight.setText(data.getWeight());
        tvPoint.setText(data.getPoint());
    }

}
