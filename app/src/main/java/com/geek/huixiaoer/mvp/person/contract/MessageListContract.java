package com.geek.huixiaoer.mvp.person.contract;

import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.BannerBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import io.reactivex.Observable;


public interface MessageListContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void updateList(List<String> datas);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 消息列表
         *
         * @param token      登录人token
         * @param pageNumber 当前页数
         * @param pageSize   每页显示数量
         */
        Observable<BaseResponse<BaseArrayData<BannerBean>>> messageList(String token, int pageNumber, int pageSize);
    }
}
