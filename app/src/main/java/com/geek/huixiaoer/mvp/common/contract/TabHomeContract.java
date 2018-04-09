package com.geek.huixiaoer.mvp.common.contract;

import com.geek.huixiaoer.api.APIs;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.BannerBean;
import com.geek.huixiaoer.storage.entity.recycle.ArticleBean;
import com.geek.huixiaoer.storage.entity.shop.GoodsBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;


public interface TabHomeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void updateBanner(List<BannerBean> bannerBean);

        void updateHotspot(List<ArticleBean> hotspotList);

        void updateGoodsExplosion(List<GoodsBean> goodsList);

        void updateDishExplosion(List<GoodsBean> dishList);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 环保热帖
         *
         * @param pageNumber 当前页数
         * @param pageSize   每页显示数量
         */
        Observable<BaseResponse<BaseArrayData<ArticleBean>>> hotspotList(int pageNumber, int pageSize);

        /**
         * 折扣店和招牌菜爆款
         *
         * @param pageNumber 当前页数
         * @param pageSize   每页显示数量
         * @param tagId      分类（2:折扣店爆款 3:招牌菜爆款)
         */
        Observable<BaseResponse<BaseArrayData<GoodsBean>>> goodsExplosion(int pageNumber, int pageSize, int tagId);
    }
}
