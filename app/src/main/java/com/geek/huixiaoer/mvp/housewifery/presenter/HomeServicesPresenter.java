package com.geek.huixiaoer.mvp.housewifery.presenter;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.mvp.common.ui.activity.LoginActivity;
import com.geek.huixiaoer.mvp.housewifery.contract.HomeServicesContract;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.entity.BannerBean;
import com.geek.huixiaoer.storage.entity.MessageBean;
import com.geek.huixiaoer.storage.entity.UserBean;
import com.geek.huixiaoer.storage.entity.housewifery.ServiceBean;
import com.geek.huixiaoer.storage.entity.shop.GoodsBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;

import org.simple.eventbus.EventBus;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import timber.log.Timber;

import static com.geek.huixiaoer.common.config.EventBusTags.ISLogin;


@ActivityScope
public class HomeServicesPresenter extends BasePresenter<HomeServicesContract.Model, HomeServicesContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    HomeServicesPresenter(HomeServicesContract.Model model, HomeServicesContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取轮播图
     */
    public void getBanner() {
        mModel.banner(7).retryWhen(new RetryWithDelay(2, 1))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mApplication))
                .subscribeWith(new ErrorHandleSubscriber<BaseArrayData<BannerBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseArrayData<BannerBean> bannerBeanBaseArrayData) {
                        mRootView.updateBanner(bannerBeanBaseArrayData.getPageData());
                    }
                });
    }

    /**
     * 自动获得用户信息
     */
    public void autoGetUserInfo() {
        mModel.autoGetUserInfo().retryWhen(new RetryWithDelay(0, 30))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<UserBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull UserBean userBean) {
                        DataHelper.setStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN, userBean.getToken());
                        DataHelper.saveDeviceData(mAppManager.getTopActivity(), Constants.SP_USER_INFO, userBean);
                        EventBus.getDefault().post(true,ISLogin);
                        /*设置当前用户信息， @param userInfo 当前用户信息*/
                        RongIM.getInstance().setCurrentUserInfo(new UserInfo(userBean.getRyId(), userBean.getUserInfo().getNickname(), null));
                         /* 设置消息体内是否携带用户信息*/
                        RongIM.getInstance().setMessageAttachedUserInfo(true);
                        RongIM.connect(userBean.getRyToken()
                                , new RongIMClient.ConnectCallback() {
                                    @Override
                                    public void onTokenIncorrect() {
                                        Timber.d("=====融云TokenIncorrect");
                                    }

                                    @Override
                                    public void onSuccess(String s) {
                                        Timber.d("=====融云Success：" + s);
                                    }

                                    @Override
                                    public void onError(RongIMClient.ErrorCode errorCode) {
                                        Timber.d("=====融云errorCode：" + errorCode);
                                    }
                                });
                    }
                });
    }

    /**
     * 家政服务项目列表
     */
    public void homeServiceList() {
        mModel.homeServiceList(1, 50, "", "", "").retryWhen(new RetryWithDelay(3, 2))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.endRefresh();//隐藏下拉刷新的进度条
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<BaseArrayData<GoodsBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseArrayData<GoodsBean> arrayData) {
                        mRootView.updateView(arrayData.getPageData());
                    }
                });
    }

    private String ryToken;

    public static String ryUserId = "";

    /**
     * 查询闲置客服
     */
    public void findService(String token, int serviceId, String serviceName) {
        mModel.findService(token, String.valueOf(serviceId)).retryWhen(new RetryWithDelay(3, 2))
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<ServiceBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull ServiceBean serviceBean) {
                        ryToken = serviceBean.getId();
                        ryUserId = ryToken;
                        setServiceB(serviceId, serviceName);
                    }
                });
    }

    /**
     * 设置客服忙碌状态
     */
    public void setServiceB(int serviceId, String serviceName) {
        Log.e("=======", "===== setServiceB ryToken： " + ryToken);
        mModel.setServiceB(ryToken).retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<ServiceBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull ServiceBean serviceBean) {
                        DataHelper.setStringSF(mApplication, Constants.CASH_SERVICE_ID, String.valueOf(serviceId));
                        RongIM.getInstance().startPrivateChat(mAppManager.getTopActivity(), ryToken, serviceName);
                    }
                });
    }

    /**
     * 设置客服空闲状态
     */
    public void setServiceF() {
//        Log.e("=======", "=====  setServiceF ryToken： " + ryToken);
        if (TextUtils.isEmpty(ryToken)) {
            mRootView.killMyself();
//            mRootView.launchActivity(new Intent(mAppManager.getTopActivity(), LoginActivity.class));
        }else {
            mModel.setServiceF(ryToken).retryWhen(new RetryWithDelay(3, 2))
                    .compose(RxUtil.applySchedulers(mRootView))
                    .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                    .subscribeWith(new ErrorHandleSubscriber<ServiceBean>(mErrorHandler) {
                        @Override
                        public void onNext(@NonNull ServiceBean serviceBean) {
                            mRootView.killMyself();
                        }

                        @Override
                        public void onComplete() {
                            super.onComplete();
                            mRootView.killMyself();
                        }
                    });
        }
    }

    /**
     * 消息列表
     *
     * @param pageSize 每页数量
     */
    public void messageList(int pageSize) {
//        String token = DataHelper.getStringSF(mApplication, Constants.SP_TOKEN);
        mModel.messageList(1, pageSize, 3)
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
