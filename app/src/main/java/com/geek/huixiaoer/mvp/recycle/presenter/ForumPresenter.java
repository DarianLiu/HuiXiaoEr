package com.geek.huixiaoer.mvp.recycle.presenter;

import android.app.Application;
import android.support.v7.widget.RecyclerView;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.entity.recycle.ArticleBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.recycle.contract.ForumContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;


@ActivityScope
public class ForumPresenter extends BasePresenter<ForumContract.Model, ForumContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    RecyclerView.Adapter mAdapter;
    @Inject
    List<ArticleBean> mList;

    @Inject
    public ForumPresenter(ForumContract.Model model, ForumContract.View rootView) {
        super(model, rootView);
    }

    private int page_no;//当前页数
    private int current_position;//当前位置

    /**
     * 获取论坛列表
     * //@param pageNumber 页数
     * //@param pageSize   每页数量
     * //@param type       排序类型
     * //@param category   文章类型
     */
    public void getForumList(boolean isRefresh) {
        if (isRefresh) page_no = 0;
        mModel.articleList(page_no + 1, 10, "createDate", "article").retryWhen(new RetryWithDelay(3, 2))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (isRefresh) {
                        mRootView.endRefresh();//隐藏下拉刷新的进度条
                    } else {
                        mRootView.endLoadMore();//隐藏加载更多的进度条
                    }
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<BaseArrayData<ArticleBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseArrayData<ArticleBean> arrayData) {
                        if (arrayData.getPageData() != null && arrayData.getPageData().size() != 0) {
                            page_no = arrayData.getPageNumber();
                            if (isRefresh) {
                                mList.clear();
                                mList.addAll(arrayData.getPageData());
                                mAdapter.notifyDataSetChanged();
                            } else {
                                current_position = mList.size();
                                mList.addAll(arrayData.getPageData());
                                mAdapter.notifyItemRangeInserted(current_position,
                                        arrayData.getPageData().size());
                            }
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
        this.mAdapter = null;
        this.mList = null;
    }
}
