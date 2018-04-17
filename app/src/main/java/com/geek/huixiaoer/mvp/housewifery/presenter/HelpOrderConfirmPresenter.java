package com.geek.huixiaoer.mvp.housewifery.presenter;

import android.app.Application;
import android.widget.Toast;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.storage.entity.UserBean;
import com.geek.huixiaoer.storage.entity.housewifery.CreateServiceOrderBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.housewifery.contract.HelpOrderConfirmContract;
import com.jess.arms.utils.DataHelper;

import org.simple.eventbus.EventBus;

import static com.geek.huixiaoer.common.config.EventBusTags.ISLogin;


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

    public void createServiceOrder(String consignee, String address, String zipCode, String mobile, String goodsId, String amount, String memo ) {
        mModel.createServiceOrder(consignee,address,zipCode,mobile,goodsId,amount,memo)
                .retryWhen(new RetryWithDelay(0, 30))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<CreateServiceOrderBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull CreateServiceOrderBean userBean) {
                        Toast.makeText(mAppManager.getTopActivity(),"创建订单成功,请进入支付...",Toast.LENGTH_SHORT).show();
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
