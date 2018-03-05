package com.geek.huixiaoer.mvp.supermarket.contract;

import android.app.Activity;

import com.geek.huixiaoer.mvp.supermarket.ui.activity.CartEditResultBean;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.shop.CartBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import io.reactivex.Observable;


public interface ShoppingCartContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        Activity getContext();

        void endRefresh();

        void endLoadMore();

        void updateView(CartBean cartBean);

        void updateCartItem(int groupPosition, int childPosition, int quantity, double totalAmount);

        void removeCartItem(int groupPosition, int childPosition, double totalAmount);

        void clearCart();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<CartBean>> cartList(String token);

        Observable<BaseResponse<CartEditResultBean>> cartEdit(String token, String id, int quantity);

        Observable<BaseResponse<CartEditResultBean>> cartDelete(String token, String id);

        Observable<BaseResponse<CartEditResultBean>> cartClear(String token);
    }
}
