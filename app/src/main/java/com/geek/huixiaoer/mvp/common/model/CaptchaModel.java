package com.geek.huixiaoer.mvp.common.model;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.CaptchaBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.common.contract.CaptchaContract;

import io.reactivex.Observable;


@ActivityScope
public class CaptchaModel extends BaseModel implements CaptchaContract.Model {

    @Inject
    CaptchaModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse<CaptchaBean>> verificationCode(String mobile, int type) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).verificationCode(mobile, type);
    }

    @Override
    public Observable<BaseResponse<CaptchaBean>> checkCode(String code) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).checkCode(code);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}