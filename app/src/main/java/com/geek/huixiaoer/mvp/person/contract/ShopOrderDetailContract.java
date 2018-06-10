package com.geek.huixiaoer.mvp.person.contract;

import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.shop.OrderBean;
import com.geek.huixiaoer.storage.entity.shop.OrderCreateResultBean;
import com.geek.huixiaoer.storage.entity.shop.OrderDetailBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import io.reactivex.Observable;


public interface ShopOrderDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void updateView(OrderDetailBean orderDetail);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<OrderDetailBean>> orderDetail(String token, String sn);


        Observable<BaseResponse<OrderBean>> shopOrderCancel(String token, String sn,String outTradeNo);

        Observable<BaseResponse<OrderCreateResultBean>> paymentSubmitSn(String token, String paymentPluginId, String sn, String amount);
    }
}
