package com.geek.huixiaoer.mvp.housewifery.ui.adapter;

import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.storage.entity.shop.GoodsBean;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.DeviceUtils;

import java.math.BigDecimal;

import butterknife.BindView;

/**
 * 商品ViewHolder
 * Created by Administrator on 2018/2/9.
 */

public class ServiceItemHolder extends BaseHolder<GoodsBean> {

    @BindView(R.id.image)
    ImageView goodsImg;
    @BindView(R.id.tv_name)
    TextView nameTV;

    public ServiceItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(GoodsBean data, int position) {
        float itemWidth = (DeviceUtils.getScreenWidth(goodsImg.getContext()) - 45) / 2;
        ViewGroup.LayoutParams params = goodsImg.getLayoutParams();
        int height = (int) Double.parseDouble(data.getMediumImage().getHeight());
        int width = (int) Double.parseDouble(data.getMediumImage().getWidth());
//        设置图片的宽高
        params.width = width;
        params.height = height;
        goodsImg.setLayoutParams(params);

        GlideArms.with(goodsImg.getContext()).load(data.getMediumImage().getUrl()).override(300,300).into(goodsImg);
        nameTV.setText(data.getName());

    }
}
