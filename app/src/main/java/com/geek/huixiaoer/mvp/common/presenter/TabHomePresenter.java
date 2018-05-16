package com.geek.huixiaoer.mvp.common.presenter;

import android.app.Application;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.entity.BannerBean;
import com.geek.huixiaoer.storage.entity.housewifery.ServiceBean;
import com.geek.huixiaoer.storage.entity.recycle.ArticleBean;
import com.geek.huixiaoer.storage.entity.shop.GoodsBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.rong.imkit.RongIM;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.common.contract.TabHomeContract;
import com.jess.arms.utils.DataHelper;


/**
 *
 */
@ActivityScope
public class TabHomePresenter extends BasePresenter<TabHomeContract.Model, TabHomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public TabHomePresenter(TabHomeContract.Model model, TabHomeContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取轮播图
     */
    public void getBanner() {
        mModel.banner(1).retryWhen(new RetryWithDelay(2, 1))
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
     * 环保热帖
     *
     * @param pageNumber 当前页数
     * @param pageSize   每页显示数量
     */
    public void hotspotList(int pageNumber, int pageSize) {
        mModel.hotspotList(pageNumber, pageSize).retryWhen(new RetryWithDelay(2, 1))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mApplication))
                .subscribeWith(new ErrorHandleSubscriber<BaseArrayData<ArticleBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseArrayData<ArticleBean> arrayData) {
                        mRootView.updateHotspot(arrayData.getPageData());
                    }
                });
    }

    /**
     * 折扣店和招牌菜爆款
     *
     * @param pageNumber 当前页数
     * @param pageSize   每页显示数量
     * @param tagId      分类（2:折扣店爆款 3:招牌菜爆款)
     */
    public void goodsExplosion(int pageNumber, int pageSize, int tagId) {
        mModel.goodsExplosion(pageNumber, pageSize, tagId).retryWhen(new RetryWithDelay(2, 1))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mApplication))
                .subscribeWith(new ErrorHandleSubscriber<BaseArrayData<GoodsBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseArrayData<GoodsBean> arrayData) {
                        if (tagId == 2) {
                            mRootView.updateGoodsExplosion(arrayData.getPageData());
                        } else  if (tagId == 3){
                            mRootView.updateDishExplosion(arrayData.getPageData());
                        }else {
                            mRootView.updateHelpYouExplosion(arrayData.getPageData());
                        }
                    }
                });
    }

    /**
     * 查询闲置客服
     */
    public void findService(String token, int serviceId) {
        mModel.findService(token,String.valueOf(serviceId)).retryWhen(new RetryWithDelay(3, 2))
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<ServiceBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull ServiceBean serviceBean) {
                        setServiceB(serviceBean.getId(), serviceId);
                    }
                });
    }

    /**
     * 设置客服忙碌状态
     */
    public void setServiceB(String ryToken, int serviceId) {
        mModel.setServiceB(ryToken).retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<ServiceBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull ServiceBean serviceBean) {
                        DataHelper.setStringSF(mApplication, Constants.CASH_SERVICE_ID,String.valueOf(serviceId));
                        RongIM.getInstance().startPrivateChat(mAppManager.getTopActivity(), ryToken, String.valueOf(serviceId));
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
