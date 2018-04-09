package com.geek.huixiaoer.mvp.common.contract;

import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.UserBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import io.reactivex.Observable;


public interface RegisterContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<UserBean>> register(String nickname, String card, String cityCode, String areaCode,
                                                    String address, String mobile, String enPassword, String dynamicCode,
                                                    boolean volunteer);
    }
}
