package com.geek.huixiaoer.mvp.supermarket.ui.adapter;

import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.storage.entity.GoodsBean;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.DeviceUtils;

import java.math.BigDecimal;

import butterknife.BindView;

/**
 * 商品ViewHolder
 * Created by Administrator on 2018/2/9.
 */

public class GoodsItemHolder extends BaseHolder<GoodsBean> {

    @BindView(R.id.goodsImg)
    ImageView goodsImg;
    @BindView(R.id.nameTV)
    TextView nameTV;
    @BindView(R.id.captionTV)
    TextView captionTV;
    @BindView(R.id.priceTV)
    TextView priceTV;
    @BindView(R.id.marketPriceTV)
    TextView marketPriceTV;

    public GoodsItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(GoodsBean data, int position) {
        float itemWidth = (DeviceUtils.getScreenWidth(goodsImg.getContext()) - 45) / 2;
        ViewGroup.LayoutParams params = goodsImg.getLayoutParams();
        double ratio = (itemWidth * 1.0) / Double.parseDouble(data.getMediumImage().getWidth());
        int height = (int) (Double.parseDouble(data.getMediumImage().getHeight()) * ratio);
        //设置图片的宽高
        params.width = (int) itemWidth;
        params.height = height;
        goodsImg.setLayoutParams(params);

        GlideArms.with(goodsImg.getContext()).load(data.getMediumImage().getUrl()).into(goodsImg);
        nameTV.setText(data.getName());
        captionTV.setText(data.getCaption());
        String price = new BigDecimal(data.getPrice()).setScale(4, BigDecimal.ROUND_HALF_DOWN).toString();
        priceTV.setText(String.format("￥%s", price));
        String marketPrice = new BigDecimal(data.getMarketPrice()).setScale(4, BigDecimal.ROUND_HALF_DOWN).toString();
        marketPriceTV.setText(String.format("￥%s", marketPrice));
        marketPriceTV.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }
}
