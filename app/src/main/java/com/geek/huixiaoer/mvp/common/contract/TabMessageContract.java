package com.geek.huixiaoer.mvp.common.contract;

import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.MessageBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import io.reactivex.Observable;


public interface TabMessageContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setMessageList(BaseArrayData<MessageBean> messageList);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 消息列表
         *
         * @param pageNumber 当前页数
         * @param pageSize   每页显示数量
         */
        Observable<BaseResponse<BaseArrayData<MessageBean>>> messageList(int pageNumber, int pageSize, int messageType);
    }
}
