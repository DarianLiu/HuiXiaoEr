package com.geek.huixiaoer.mvp.common.model;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.storage.BaseResponse;
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
    public Observable<BaseResponse<UserBean>> register(String nickname, String mobile,
                                                       String md5Password, String refererCode) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).register(nickname, mobile,
                md5Password, refererCode);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}