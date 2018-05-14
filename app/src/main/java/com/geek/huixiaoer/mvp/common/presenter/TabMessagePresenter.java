package com.geek.huixiaoer.mvp.common.presenter;

import android.app.Application;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.entity.MessageBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.common.contract.TabMessageContract;


@ActivityScope
public class TabMessagePresenter extends BasePresenter<TabMessageContract.Model, TabMessageContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public TabMessagePresenter(TabMessageContract.Model model, TabMessageContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 消息列表
     *
     * @param pageSize 每页数量
     */
    public void messageList(int pageSize) {
//        String token = DataHelper.getStringSF(mApplication, Constants.SP_TOKEN);
        mModel.messageList(1, pageSize, 0)
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<BaseArrayData<MessageBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseArrayData<MessageBean> bannerBeanBaseArrayData) {
                        mRootView.setMessageList(bannerBeanBaseArrayData);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
