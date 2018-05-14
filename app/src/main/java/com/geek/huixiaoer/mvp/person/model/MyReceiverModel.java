package com.geek.huixiaoer.mvp.person.model;

import android.app.Application;

import com.geek.huixiaoer.api.BaseApi;
import com.geek.huixiaoer.common.utils.StringUtils;
import com.geek.huixiaoer.mvp.person.contract.MyReceiverContract;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.shop.ReceiverBean;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class MyReceiverModel extends BaseModel implements MyReceiverContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MyReceiverModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse<BaseArrayData<ReceiverBean>>> receiverList(int pageNumber, String token) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).receiverList(pageNumber,token);
    }

//    @Override
//    public Observable<BaseResponse<ReceiverBean>> receiverSave(String token, String consignee, String areaName, String address, String zipCode, String phone, boolean isDefault, String areaId) {
//        return mRepositoryManager.obtainRetrofitService(BaseApi.class).receiverSave(token, consignee, areaName, address, zipCode, phone, isDefault, areaId);
//    }

    @Override
    public Observable<BaseResponse<ReceiverBean>> receiverUpdate(String token, String consignee, String areaName, String address, String zipCode, String phone, boolean isDefault, String areaId, String id, String oId) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).receiverUpdate(token, StringUtils.stringUTF8(consignee) ,StringUtils.stringUTF8(areaName) ,StringUtils.stringUTF8(address) , zipCode, phone, isDefault, areaId, id, oId);
    }

    @Override
    public Observable<BaseResponse<ReceiverBean>> receiverDelete(String token, String id) {
        return mRepositoryManager.obtainRetrofitService(BaseApi.class).receiverDelete(token, id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

}