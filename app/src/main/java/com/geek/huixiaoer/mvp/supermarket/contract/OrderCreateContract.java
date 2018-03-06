package com.geek.huixiaoer.mvp.supermarket.contract;

import com.geek.huixiaoer.mvp.supermarket.ui.activity.CartEditResultBean;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.shop.OrderCheckResultBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import io.reactivex.Observable;


public interface OrderCreateContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void endRefresh();

        void updateView(OrderCheckResultBean resultBean);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<OrderCheckResultBean>> orderCheckout(String token);

        Observable<BaseResponse<CartEditResultBean>> orderCalculate(
                String token, String receiverId, String paymentMethodId, String shippingMethodId,
                String code, String invoiceTitle, String useBalance, String memo);

        Observable<BaseResponse<CartEditResultBean>> paymentSubmitNo(
                String token, String paymentPluginId, String outTradeNo, String amount);

        Observable<BaseResponse<CartEditResultBean>> orderCreate(
                String token, String receiverId, String code, String invoiceTitle, String useBalance
                , String memo);
    }
}
