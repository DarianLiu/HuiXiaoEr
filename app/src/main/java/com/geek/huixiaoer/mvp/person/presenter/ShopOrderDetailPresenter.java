package com.geek.huixiaoer.mvp.person.presenter;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.mvp.common.ui.activity.LoginActivity;
import com.geek.huixiaoer.mvp.person.contract.ShopOrderDetailContract;
import com.geek.huixiaoer.storage.entity.shop.OrderBean;
import com.geek.huixiaoer.storage.entity.shop.OrderCreateResultBean;
import com.geek.huixiaoer.storage.entity.shop.OrderDetailBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
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
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class ShopOrderDetailPresenter extends BasePresenter<ShopOrderDetailContract.Model, ShopOrderDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ShopOrderDetailPresenter(ShopOrderDetailContract.Model model, ShopOrderDetailContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取普通订单的详细数据
     */
    public void orderDetail(String orderSn) {
        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
        if (TextUtils.isEmpty(token)) {
            mRootView.launchActivity(new Intent(mAppManager.getTopActivity(), LoginActivity.class));
        }
        mModel.orderDetail(token, orderSn).retryWhen(new RetryWithDelay(3, 2))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.endRefresh();//隐藏下拉刷新的进度条
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<OrderDetailBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull OrderDetailBean resultBean) {
                        mRootView.updateView(resultBean);
                    }
                });
    }

    /**
     * 支付宝支付
     * 支付方式(移动端默认为：alipayMobilePaymentPlugin)
     *
     * @param orderSn 交易流水号
     * @param amount  交易金额
     */
    public void paymentSubmitNo(String orderSn, String amount) {
        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
        mModel.paymentSubmitSn(token, "alipayMobilePaymentPlugin", orderSn, amount)
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
     * 支付宝支付
     * 支付方式(移动端默认为：alipayMobilePaymentPlugin)
     *
     * @param orderSn 订单号
     * @param orderSn 交易流水号
     */
    public void cancelOrder(String orderSn, String outTradeNo) {
        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
        mModel.shopOrderCancel(token, orderSn, outTradeNo)
                .retryWhen(new RetryWithDelay(0, 30))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<OrderBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull OrderBean resultBean) {
                        mRootView.showMessage("订单取消成功！");
                        mRootView.killMyself();
//                        toALiPay(resultBean.getOutTradeNo());
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
//            emitter.onComplete();
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new Consumer<Map<String, String>>() {
                    @Override
                    public void accept(Map<String, String> resultMap) throws Exception  {
                        String resultStatus = resultMap.get("resultStatus");
                        if (TextUtils.equals(resultStatus, "9000")) {
                            ArmsUtils.makeText(mApplication, "支付成功");
                            mRootView.killMyself();
                        } else if (resultStatus.equals("4000")) {
                            ArmsUtils.makeText(mApplication, "支付失败");
                            // 4000为支付失败，包括用户主动取消支付，或者系统返回的错误
                        } else if (resultStatus.equals("6001")) {
                            // 6001为取消支付，或者系统返回的错误
                            ArmsUtils.makeText(mApplication, "取消支付");
                        } else if (resultStatus.equals("8000")) {
                            // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            ArmsUtils.makeText(mApplication, "支付结果确认中");
                            mRootView.killMyself();
                        } else {
                            // 其他为系统返回的错误
                            ArmsUtils.makeText(mApplication, "支付错误");
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
