package com.geek.huixiaoer.mvp.housewifery.contract;

import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.housewifery.CreateServiceOrderBean;
import com.geek.huixiaoer.storage.entity.shop.OrderCreateResultBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import io.reactivex.Observable;


public interface HelpOrderConfirmContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void updateView();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
//        harmfulconsignee:收货人
//        harmfuladdress:收货地址
//        harmfulzipCode:邮编
//        harmfulmobile:电话
//        harmfulgoodsId:货品id
//        harmfulamount:金额
//        harmfulmemo:附言
        //
        Observable<BaseResponse<CreateServiceOrderBean>> createServiceOrder(String consignee, String address, String zipCode, String mobile, String goodsId, String amount, String memo );
        Observable<BaseResponse<OrderCreateResultBean>> paymentSubmitNo(
                String token, String paymentPluginId, String outTradeNo, String amount);
    }
}
