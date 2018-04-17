package com.geek.huixiaoer.mvp.person.presenter;

import android.app.Application;
import android.support.v7.widget.RecyclerView;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.entity.BannerBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.person.contract.MessageListContract;
import com.jess.arms.utils.DataHelper;

import java.util.ArrayList;
import java.util.List;


@ActivityScope
public class MessageListPresenter extends BasePresenter<MessageListContract.Model, MessageListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MessageListPresenter(MessageListContract.Model model, MessageListContract.View rootView) {
        super(model, rootView);
    }

    private int pageNumber = 1;

    /**
     * 消息列表
     *
     * @param pageSize 每页数量
     */
    public void messageList(int pageSize) {
        String token = DataHelper.getStringSF(mApplication, Constants.SP_TOKEN);
        mModel.messageList(token, pageNumber, pageSize)
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<BaseArrayData<BannerBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseArrayData<BannerBean> bannerBeanBaseArrayData) {
                        mRootView.updateList(new ArrayList<>());
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