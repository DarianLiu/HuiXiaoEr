package com.geek.huixiaoer.mvp.supermarket.presenter;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.mvp.common.ui.activity.MainActivity;
import com.geek.huixiaoer.mvp.supermarket.contract.OrderCreateContract;
import com.geek.huixiaoer.storage.entity.shop.OrderCalculateResultBean;
import com.geek.huixiaoer.storage.entity.shop.OrderCheckResultBean;
import com.geek.huixiaoer.storage.entity.shop.OrderCreateResultBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class OrderCreatePresenter extends BasePresenter<OrderCreateContract.Model, OrderCreateContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;
    private String amount;

    @Inject
    OrderCreatePresenter(OrderCreateContract.Model model, OrderCreateContract.View rootView
            , RxErrorHandler handler, AppManager appManager, Application application) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
        this.mApplication = application;
    }

    /**
     * 获取普通订单的详细数据
     */
    public void orderCheckout() {
        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
        mModel.orderCheckout(token).retryWhen(new RetryWithDelay(3, 2))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.endRefresh();//隐藏下拉刷新的进度条
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<OrderCheckResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull OrderCheckResultBean resultBean) {
                        amount = String.valueOf(resultBean.getAmount());
                        mRootView.updateView(resultBean);
                    }
                });
    }

    /**
     * 订单金额计算
     *
     * @param receiverId   收获地址ID
     * @param code         优惠码
     * @param invoiceTitle 发票抬头
     * @param useBalance   是否使用余额（0/1）
     * @param memo         附言(商户ID为Key，附言为Value的JsonArray字符串)
     */
    public void orderCalculate(String receiverId, String code, String invoiceTitle, String useBalance, String memo) {
        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
        mModel.orderCalculate(token, receiverId, "1", "1", code, invoiceTitle, useBalance, memo)
                .retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<OrderCalculateResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull OrderCalculateResultBean resultBean) {
                        amount = resultBean.getAmount();
                        mRootView.updateOrder(resultBean);
                    }
                })
        ;
    }

    /**
     * 创建订单
     *
     * @param receiverId   收获地址ID
     * @param code         优惠码
     * @param invoiceTitle 发票抬头
     * @param useBalance   是否使用余额（0/1）
     * @param memo         附言
     */
    public void orderCreate(String receiverId, String code, String invoiceTitle, String useBalance, String memo) {
        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
        mModel.orderCreate(token, receiverId, code, invoiceTitle, useBalance, memo)
                .retryWhen(new RetryWithDelay(0, 0))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<OrderCreateResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull OrderCreateResultBean resultBean) {
                        paymentSubmitNo(resultBean.getOutTradeNo(), amount);
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
        Observable.create(new ObservableOnSubscribe<Map<String, String>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Map<String, String>> emitter) throws Exception {
                PayTask aLiPayTask = new PayTask(mAppManager.getTopActivity());
                Map<String, String> resultMap = aLiPayTask.payV2(orderStr, true);
                emitter.onNext(resultMap);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY))
                .subscribe(new Consumer<Map<String, String>>() {
                    @Override
                    public void accept(Map<String, String> resultMap) throws Exception {
                        String resultStatus = resultMap.get("resultStatus");
                        if (TextUtils.equals(resultStatus, "9000")) {
                            ArmsUtils.makeText(mApplication, "支付成功");
                        } else if (resultStatus.equals("4000")) {
                            ArmsUtils.makeText(mApplication, "支付失败");
                            // 4000为支付失败，包括用户主动取消支付，或者系统返回的错误
                        } else if (resultStatus.equals("6001")) {
                            // 6001为取消支付，或者系统返回的错误
                            ArmsUtils.makeText(mApplication, "取消支付");
                        } else if (resultStatus.equals("8000")) {
                            // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            ArmsUtils.makeText(mApplication, "支付结果确认中");
                        } else {
                            // 其他为系统返回的错误
                            ArmsUtils.makeText(mApplication, "支付错误");
                        }
                        mRootView.launchActivity(new Intent(mAppManager.getTopActivity(), MainActivity.class));
                    }
                });
//                .subscribeWith(new ErrorHandleSubscriber<Map<String, String>>(mErrorHandler) {
//                    @Override
//                    public void onNext(@NonNull Map<String, String> resultMap) {
//
//                    }
//                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.amount = null;
        this.mApplication = null;
    }

}
