package com.geek.huixiaoer.mvp.supermarket.presenter;

import android.support.v7.widget.RecyclerView;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.mvp.supermarket.contract.GoodsListContract;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.entity.GoodsBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;


@ActivityScope
public class GoodsListPresenter extends BasePresenter<GoodsListContract.Model, GoodsListContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private RecyclerView.Adapter mAdapter;
    private List<GoodsBean> mList;

    @Inject
    GoodsListPresenter(GoodsListContract.Model model, GoodsListContract.View rootView
            , RxErrorHandler handler, AppManager appManager, RecyclerView.Adapter adapter,
                       List<GoodsBean> goodsList) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
        this.mAdapter = adapter;
        this.mList = goodsList;
    }

    private int page_no;//当前页数
    private int current_position;//当前位置

    /**
     * 获取指定类别商品列表
     *
     * @param isRefresh   是否刷新
     * @param category_id 商品类别ID
     */
    public void getGoodsList(boolean isRefresh, int category_id) {
        if (isRefresh) page_no = 0;
        mModel.goodsList(category_id, page_no + 1).retryWhen(new RetryWithDelay(3, 2))
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
                .subscribeWith(new ErrorHandleSubscriber<BaseArrayData<GoodsBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseArrayData<GoodsBean> goodsBaseArrayData) {
                        if (goodsBaseArrayData.getPageData() != null
                                && goodsBaseArrayData.getPageData().size() != 0) {
                            page_no = goodsBaseArrayData.getPageNumber();
                            if (isRefresh) {
                                mList.clear();
                                mList.addAll(goodsBaseArrayData.getPageData());
                                mAdapter.notifyDataSetChanged();
                            } else {
                                current_position = mList.size();
                                mList.addAll(goodsBaseArrayData.getPageData());
                                mAdapter.notifyItemRangeInserted(current_position,
                                        goodsBaseArrayData.getPageData().size());
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
