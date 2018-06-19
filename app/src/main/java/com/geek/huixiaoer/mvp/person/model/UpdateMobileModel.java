package com.geek.huixiaoer.mvp.person.model;

import android.app.Application;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.SingleResultBean;
import com.geek.huixiaoer.storage.entity.recycle.RankBean;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.person.contract.UpdateMobileContract;

import io.reactivex.Observable;


@ActivityScope
public class UpdateMobileModel extends BaseModel implements UpdateMobileContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public UpdateMobileModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<SingleResultBean>> verificationCode(String mobile, int type) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).verificationCode(mobile);
    }

    @Override
    public Observable<BaseResponse<BaseArrayData<RankBean>>> mobileUpdate(String token, String mobile) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).mobileUpdate(token, mobile);
    }
}