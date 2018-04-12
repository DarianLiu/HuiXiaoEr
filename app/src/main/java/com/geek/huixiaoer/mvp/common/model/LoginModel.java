package com.geek.huixiaoer.mvp.common.model;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.UserBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.geek.huixiaoer.mvp.common.contract.LoginContract;

import io.reactivex.Observable;

@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.Model {

    @Inject
    LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse<UserBean>> login(String mobile, String md5Password) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).login("", mobile, md5Password);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}