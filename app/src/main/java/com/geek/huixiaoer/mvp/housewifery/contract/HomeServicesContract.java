package com.geek.huixiaoer.mvp.housewifery.contract;

import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.BannerBean;
import com.geek.huixiaoer.storage.entity.MessageBean;
import com.geek.huixiaoer.storage.entity.housewifery.ServiceBean;
import com.geek.huixiaoer.storage.entity.shop.GoodsBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import io.reactivex.Observable;


public interface HomeServicesContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void endRefresh();

        void updateBanner(List<BannerBean> bannerBean);

        void updateView(List<GoodsBean> homeServices);

        void setServiceState(String serviceId,int serverId);

        void setMessageList(BaseArrayData<MessageBean> messageList);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 获取轮播图
         *
         * @param positonId 轮播图类型
         */
        Observable<BaseResponse<BaseArrayData<BannerBean>>> banner(int positonId);

        Observable<BaseResponse<BaseArrayData<GoodsBean>>> homeServiceList(int pageNumber, int pageSize, String startPrice, String endPrice, String orderType);

        Observable<BaseResponse<ServiceBean>> findService( String token,String serviceId);

        Observable<BaseResponse<ServiceBean>> setServiceB(String ryToken);

        Observable<BaseResponse<BaseArrayData<MessageBean>>> messageList(int pageNumber, int pageSize, int messageType);
    }
}
