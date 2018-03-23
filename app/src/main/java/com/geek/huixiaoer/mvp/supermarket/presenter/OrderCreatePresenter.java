package com.geek.huixiaoer.mvp.supermarket.presenter;

import android.text.TextUtils;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.common.utils.Constants;
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

import java.util.Map;

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
public class OrderCreatePresenter extends BasePresenter<OrderCreateContract.Model, OrderCreateContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private String amount;

    @Inject
    OrderCreatePresenter(OrderCreateContract.Model model, OrderCreateContract.View rootView
            , RxErrorHandler handler, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
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
                .retryWhen(new RetryWithDelay(3, 2))
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
            EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
            PayTask aLiPayTask = new PayTask(mAppManager.getTopActivity());
            Map<String, String> resultMap = aLiPayTask.payV2(orderStr, true);
            emitter.onNext(resultMap);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribeWith(new ErrorHandleSubscriber<Map<String, String>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull Map<String, String> resultMap) {
                        if (TextUtils.equals(resultMap.get("resultStatus"), "9000")) {
                            ArmsUtils.makeText(mAppManager.getTopActivity(), resultMap.get("memo"));
                        } else {
                            ArmsUtils.makeText(mAppManager.getTopActivity(), resultMap.get("memo"));
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.amount = null;
    }

}
