package com.geek.huixiaoer.mvp.recycle.presenter;

import com.geek.huixiaoer.api.utils.RxUtil;
import com.geek.huixiaoer.common.config.EventBusTags;
import com.geek.huixiaoer.common.utils.BitmapUtils;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.utils.StringUtils;
import com.geek.huixiaoer.mvp.recycle.contract.RecycleAddContract;
import com.geek.huixiaoer.storage.entity.article.ArticleBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.DataHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import timber.log.Timber;


@ActivityScope
public class RecycleAddPresenter extends BasePresenter<RecycleAddContract.Model, RecycleAddContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;

    @Inject
    RecycleAddPresenter(RecycleAddContract.Model model, RecycleAddContract.View rootView
            , RxErrorHandler handler, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
    }

    /**
     * @param content   内容
     * @param imageList 图片地址地址列表
     */
    public void recycleAdd(String content, List<String> imageList) {
        JSONArray jsonArray = new JSONArray();
        //内容（文字加九宫格图片）
        JSONObject contentObj = new JSONObject();
        try {
            contentObj.put("text", content);
            jsonArray.put(contentObj);
            for (int i = 0; i < imageList.size(); i++) {
                JSONObject imgObj = new JSONObject();
                String encodeString = StringUtils.bitmapToString(BitmapUtils.getBitmapForPath(imageList.get(i)));
                imgObj.put("image", encodeString);
                jsonArray.put(imgObj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Timber.d("======contentParams==" + jsonArray.toString());
        String contentParams = jsonArray.toString();
        String token = DataHelper.getStringSF(mAppManager.getTopActivity(), Constants.SP_TOKEN);
        mModel.recycleAdd(token, "mood", contentParams).retryWhen(new RetryWithDelay(3, 2))
                .compose(RxUtil.applySchedulers(mRootView))
                .compose(RxUtil.handleBaseResult(mAppManager.getTopActivity()))
                .subscribeWith(new ErrorHandleSubscriber<ArticleBean>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull ArticleBean articleBean) {
                        if (articleBean != null) {
                            EventBus.getDefault().post(articleBean, EventBusTags.Tag_Recycle);
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }

}
