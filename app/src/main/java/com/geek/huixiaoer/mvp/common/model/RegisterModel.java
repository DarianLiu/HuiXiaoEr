package com.geek.huixiaoer.mvp.common.model;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.SingleResultBean;
import com.geek.huixiaoer.storage.entity.UserBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.common.contract.RegisterContract;

import io.reactivex.Observable;

@ActivityScope
public class RegisterModel extends BaseModel implements RegisterContract.Model {

    @Inject
    RegisterModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse<SingleResultBean>> verificationCode(String mobile, int type) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).verificationCode(mobile);
    }

    @Override
    public Observable<BaseResponse<UserBean>> register(String nickname, String card, String cityCode, String areaCode,String communityCode,
                                                       String address, String mobile, String enPassword, String dynamicCode,
                                                       int volunteer) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).register(nickname, card, cityCode, areaCode,communityCode, address, mobile, enPassword, dynamicCode, volunteer);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}