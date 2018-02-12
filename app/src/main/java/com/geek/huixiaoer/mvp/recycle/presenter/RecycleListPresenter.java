package com.geek.huixiaoer.mvp.recycle.presenter;

import android.support.v7.widget.RecyclerView;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.mvp.recycle.contract.RecycleListContract;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.entity.article.ArticleBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class RecycleListPresenter extends BasePresenter<RecycleListContract.Model, RecycleListContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private RecyclerView.Adapter mAdapter;
    private List<ArticleBean> mList;

    @Inject
    RecycleListPresenter(RecycleListContract.Model model, RecycleListContract.View rootView
            , RxErrorHandler handler, AppManager appManager, RecyclerView.Adapter adapter,
                         List<ArticleBean> recycleList) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
        this.mAdapter = adapter;
        this.mList = recycleList;
    }

    private int page_no;//当前页数
    private int current_position;//当前位置

    /**
     * 获取垃圾回收列表
     * //@param pageNumber 页数
     * //@param pageSize   每页数量
     * //@param type       排序类型
     * //@param category   文章类型
     */
    public void getRecycleList(boolean isRefresh) {
        if (isRefresh) page_no = 0;
        mModel.articleList(page_no + 1, 10, "createDate", "mood").retryWhen(new RetryWithDelay(3, 2))
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
        this.mAdapter = null;
        this.mList = null;
    }

}
