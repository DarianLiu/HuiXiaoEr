package com.geek.huixiaoer.mvp.housewifery.presenter;

import android.app.Application;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.storage.entity.housewifery.CreateServiceOrderBean;
import com.geek.huixiaoer.storage.entity.shop.OrderCreateResultBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.housewifery.contract.HelpOrderConfirmContract;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;


import java.util.Map;


@ActivityScope
public class HelpOrderConfirmPresenter extends BasePresenter<HelpOrderConfirmContract.Model, HelpOrderConfirmContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public HelpOrderConfirmPresenter(HelpOrderConfirmContract.Model model, HelpOrderConfirmContract.View rootView) {
        super(model, rootView);
    }

    public void createServiceOrder(String consignee, String address, String zipCode, String mobile, String goodsId, String amount, String memo,String id) {
        String token = DataHelper.getStringSF(mApplication, Constants.SP_TOKEN);
        mModel.createServiceOrder(token, consignee, address, zipCode, mobile, goodsId, amount, memo,id)
                .retryWhen(new RetryWithDelay(0, 30))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<CreateServiceOrderBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull CreateServiceOrderBean userBean) {
                        mRootView.showMessage( "创建订单成功,请进入支付...");
                        paymentSubmitNo(userBean.getOutTradeNo(), amount);
//                        mRootView.killMyself();
                    }
                });
    }

    /**
     * 支付宝支付
     * 支付方式(移动端默认为：alipayMobilePaymentPlugin)
     *
     * @param outTradeNo 交易流水号
     * @param amount     交易金额
     */
    public void paymentSubmitNo(String outTradeNo, String amount) {
        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
        mModel.paymentSubmitNo(token, "alipayMobilePaymentPlugin", outTradeNo, amount)
                .retryWhen(new RetryWithDelay(0, 30))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<OrderCreateResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull OrderCreateResultBean resultBean) {
                        toALiPay(resultBean.getOutTradeNo());
                    }
                });
    }

    /**
     * 唤起支付宝支付
     *
     * @param orderStr 支付宝支付所需字符串
     */
    private void toALiPay(String orderStr) {
        Observable.create((ObservableOnSubscribe<Map<String, String>>) emitter -> {
            PayTask aLiPayTask = new PayTask(mAppManager.getTopActivity());
            Map<String, String> resultMap = aLiPayTask.payV2(orderStr, true);
            emitter.onNext(resultMap);
            emitter.onComplete();
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribeWith(new ErrorHandleSubscriber<Map<String, String>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull Map<String, String> resultMap) {
                        if (TextUtils.equals(resultMap.get("resultStatus"), "9000")) {
                            ArmsUtils.makeText(mApplication, resultMap.get("memo"));
                        } else {
                            ArmsUtils.makeText(mApplication, resultMap.get("memo"));
                        }
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
