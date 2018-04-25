package com.geek.huixiaoer.mvp.recycle.ui.adpater;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.jess.arms.base.BaseHolder;
import com.geek.huixiaoer.storage.entity.recycle.BlackBean;

import butterknife.BindView;

/**
 * 黑名单
 * Created by Administrator on 2018/4/25.
 */

public class BlackItemHolder extends BaseHolder<BlackBean> {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_reason)
    TextView tvReason;

    public BlackItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(BlackBean data, int position) {
        tvName.setText(TextUtils.isEmpty(data.getNickname()) ? data.getName() : data.getNickname());
        tvReason.setText(data.getBlackReason());
    }
}
