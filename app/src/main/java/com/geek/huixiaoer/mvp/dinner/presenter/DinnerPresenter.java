package com.geek.huixiaoer.mvp.dinner.presenter;

import android.app.Application;
import android.support.v7.widget.RecyclerView;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.entity.shop.GoodsBean;
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

import com.geek.huixiaoer.mvp.dinner.contract.DinnerContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;


@ActivityScope
public class DinnerPresenter extends BasePresenter<DinnerContract.Model, DinnerContract.View> {
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
    List<GoodsBean> mList;

    @Inject
    public DinnerPresenter(DinnerContract.Model model, DinnerContract.View rootView) {
        super(model, rootView);
    }

    private int page_no;//当前页数
    private int current_position;//当前位置

    /**
     * 获取 招牌列表
     *
     * @param isRefresh   是否刷新
     * @param startPrice 最小价格（可以不加）
     * @param endPrice   最高价格（可以不加）
     * @param orderType  排序方式（topDesc:置顶降序 priceAsc:价格升序 priceDesc:价格降序 salesDesc:销量降序 dateDesc:日期降序）
     */
    public void dishList(boolean isRefresh,  String startPrice, String endPrice, String orderType) {
        if (isRefresh) page_no = 0;
        mModel.dishList(page_no + 1,20,startPrice,endPrice,orderType).retryWhen(new RetryWithDelay(3, 2))
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
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
