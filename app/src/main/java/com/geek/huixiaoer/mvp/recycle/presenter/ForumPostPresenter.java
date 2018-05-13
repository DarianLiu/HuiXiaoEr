package com.geek.huixiaoer.mvp.recycle.presenter;

import android.app.Application;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.common.utils.BitmapUtils;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.utils.StringUtils;
import com.geek.huixiaoer.storage.entity.recycle.ArticleBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import timber.log.Timber;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.recycle.contract.ForumPostContract;
import com.jess.arms.utils.DataHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


@ActivityScope
public class ForumPostPresenter extends BasePresenter<ForumPostContract.Model, ForumPostContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ForumPostPresenter(ForumPostContract.Model model, ForumPostContract.View rootView) {
        super(model, rootView);
    }

    /**
     * @param content 内容
     * @param title   标题
     */
    public void forumPost(String title, String content) {
        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
        mModel.forumPost(token, title, "article", content)
                .retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<ArticleBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull ArticleBean articleBean) {
//                        if (articleBean != null) {
//                            EventBus.getDefault().post(articleBean, EventBusTags.Tag_Recycle);
//                        }
                        mRootView.showMessage("帖子已发布，正在审核...");
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
