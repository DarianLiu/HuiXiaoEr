package com.geek.huixiaoer.mvp.person.presenter;

import android.app.Application;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.common.utils.AndroidUtil;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.mvp.person.contract.ReceiverAddContract;
import com.geek.huixiaoer.storage.entity.shop.ReceiverBean;
import com.geek.huixiaoer.storage.event.ReceiverEvent;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;

import org.simple.eventbus.EventBus;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class ReceiverAddPresenter extends BasePresenter<ReceiverAddContract.Model, ReceiverAddContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;

    @Inject
    public ReceiverAddPresenter(ReceiverAddContract.Model model, ReceiverAddContract.View rootView) {
        super(model, rootView);
    }

    public void getCityData() {
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            String result = AndroidUtil.readAssert(mApplication, "area.txt");
            emitter.onNext(result);
            emitter.onComplete();
        }).subscribeOn(Schedulers.newThread())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示进度条
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(mRootView::hideLoading)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribeWith(new ErrorHandleSubscriber<String>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull String result) {
                        mRootView.setCityWheel(result);
                    }
                });
    }

    /**
     * 收货地址保存
     *
     * @param consignee 收货人姓名
     * @param areaName  地区名称
     * @param address   详细地址
     * @param zipCode   邮政编码
     * @param phone     手机号码
     * @param isDefault 是否默认
     * @param areaId    地区ID
     */
    public void receiverSave(String consignee, String areaName, String address,
                             String zipCode, String phone, boolean isDefault, String areaId) {
        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
        mModel.receiverSave(token, consignee, areaName, address, zipCode, phone, isDefault, areaId)
                .retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<ReceiverBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull ReceiverBean receiverBean) {
                        EventBus.getDefault().post(new ReceiverEvent(2, -1, isDefault, receiverBean));
                        mRootView.killMyself();
                    }
                });
    }

    /**
     * 收货地址更新
     *
     * @param position  列表选中项
     * @param consignee 收货人姓名
     * @param areaName  地区名称
     * @param address   详细地址
     * @param zipCode   邮政编码
     * @param phone     手机号码
     * @param isDefault 是否默认
     * @param areaId    地区ID
     * @param id        收货地址ID
     * @param oId       收货地址ID
     */
    public void receiverUpdate(int position, String consignee, String areaName, String address, String zipCode,
                               String phone, boolean isDefault, String areaId, String id, String oId) {
        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
        mModel.receiverUpdate(token, consignee, areaName, address, zipCode, phone, isDefault, areaId, id, oId)
                .retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<ReceiverBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull ReceiverBean receiverBean) {
                        EventBus.getDefault().post(new ReceiverEvent(2, position, isDefault, receiverBean));
                        mRootView.killMyself();
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
