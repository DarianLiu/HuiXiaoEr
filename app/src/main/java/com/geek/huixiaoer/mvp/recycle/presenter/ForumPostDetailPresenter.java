package com.geek.huixiaoer.mvp.recycle.presenter;

import android.app.Application;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.storage.entity.recycle.ArticleBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.recycle.contract.ForumPostDetailContract;
import com.jess.arms.utils.DataHelper;


@ActivityScope
public class ForumPostDetailPresenter extends BasePresenter<ForumPostDetailContract.Model, ForumPostDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ForumPostDetailPresenter(ForumPostDetailContract.Model model, ForumPostDetailContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 文章详情
     *
     * @param articleId 文章ID
     */
    public void articleDetail(String articleId) {
        mModel.articleDetail(1, 10, "createDate", articleId).retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<ArticleBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull ArticleBean articleBean) {
                        mRootView.updateView(articleBean);
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
