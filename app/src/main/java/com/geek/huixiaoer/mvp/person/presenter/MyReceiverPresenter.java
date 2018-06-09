package com.geek.huixiaoer.mvp.person.presenter;

import android.app.Application;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.mvp.person.contract.MyReceiverContract;
import com.geek.huixiaoer.mvp.person.ui.activity.ReceiverAddActivity;
import com.geek.huixiaoer.mvp.person.ui.adapter.ReceiverAdapter;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.entity.shop.ReceiverBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class MyReceiverPresenter extends BasePresenter<MyReceiverContract.Model, MyReceiverContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private RecyclerView.Adapter mAdapter;
    private List<ReceiverBean> mList;

    @Inject
    public MyReceiverPresenter(MyReceiverContract.Model model, MyReceiverContract.View rootView,
                               RecyclerView.Adapter adapter,
                               List<ReceiverBean> receiverList) {
        super(model, rootView);
        this.mAdapter = adapter;
        this.mList = receiverList;
    }

    private int page_no;//当前页数
    private int current_position;//当前位置

    /**
     * 收货地址列表
     *
     * @param isRefresh 是否刷新
     */
    public void receiverList(boolean isRefresh) {
        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
        if (isRefresh) page_no = 0;
        mModel.receiverList(page_no + 1, token).retryWhen(new RetryWithDelay(3, 2))
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
                .subscribeWith(new ErrorHandleSubscriber<BaseArrayData<ReceiverBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseArrayData<ReceiverBean> arrayData) {
                        if (arrayData.getPageData() != null && arrayData.getPageData().size() != 0) {
//                            page_no = arrayData.getPageNumber();

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

//                            ((ReceiverAdapter) mAdapter).setOnItemClickListener((view, viewType, data, position) -> {
//                                Intent intent = new Intent(mAppManager.getTopActivity(), ReceiverAddActivity.class);
//                                intent.putExtra(Constants.INTENT_TYPE, "update");
//                                intent.putExtra(Constants.INTENT_LIST_POSITION, position);
//                                intent.putExtra(Constants.INTENT_RECEIVER, mList.get(position));
//                                mRootView.launchActivity(intent);
//                            });
                        }
                    }
                });

    }

    /**
     * 收货地址更新（设置为默认）
     *
     * @param oldSelectedPos 原有选中项
     * @param position       更新的position
     */
    public void receiverUpdate(int oldSelectedPos, int position) {
        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
        ReceiverBean receiverBean = mList.get(position);
        mModel.receiverUpdate(token, receiverBean.getConsignee(), receiverBean.getAreaName(),
                receiverBean.getAddress(), receiverBean.getZipCode(), receiverBean.getPhone(),
                true, receiverBean.getArea_id(), receiverBean.getId(), receiverBean.getId())
                .retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<ReceiverBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull ReceiverBean receiverBean) {
                        if (oldSelectedPos >= 0)
                            mList.get(oldSelectedPos).setIsDefault(false);
                        mRootView.receiverUpdate(position, true);
                    }

                    @Override
                    public void onError(@NonNull Throwable t) {
                        super.onError(t);
                        mRootView.receiverUpdate(position, false);
                    }
                });
    }

    /**
     * 收货地址删除
     *
     * @param position 更新的position
     */
    public void receiverDelete(int position) {
        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
        mModel.receiverDelete(token, mList.get(position).getId()).retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<ReceiverBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull ReceiverBean receiverBean) {
                        mList.remove(position);
                        mAdapter.notifyItemRemoved(position);
                    }
                });
    }

    /**
     * 更新列表
     *
     * @param position     item位置
     * @param receiverBean 收货地址
     */
    public void updateItem(int position, ReceiverBean receiverBean) {
        if (position >= 0) {
            mList.set(position, receiverBean);
            mAdapter.notifyItemChanged(position);
        } else {
            mList.add(0, receiverBean);
            mAdapter.notifyDataSetChanged();
        }
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
