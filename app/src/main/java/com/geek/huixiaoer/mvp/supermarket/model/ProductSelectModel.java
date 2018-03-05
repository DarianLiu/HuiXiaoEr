//package com.geek.huixiaoer.mvp.supermarket.model;
//
//import android.app.Application;
//
//import com.geek.huixiaoer.api.BaseApi;
//import com.geek.huixiaoer.storage.BaseArrayData;
//import com.geek.huixiaoer.storage.BaseResponse;
//import com.geek.huixiaoer.storage.entity.shop.GoodsBean;
//import com.geek.huixiaoer.storage.entity.shop.SpecificationBean;
//import com.google.gson.Gson;
//import com.jess.arms.integration.IRepositoryManager;
//import com.jess.arms.mvp.BaseModel;
//
//import com.jess.arms.di.scope.ActivityScope;
//
//import javax.inject.Inject;
//
//import com.geek.huixiaoer.mvp.supermarket.contract.ProductSelectContract;
//
//import io.reactivex.Observable;
//
//
//@ActivityScope
//public class ProductSelectModel extends BaseModel implements ProductSelectContract.Model {
//    @Inject
//    Gson mGson;
//    @Inject
//    Application mApplication;
//
//    @Inject
//    ProductSelectModel(IRepositoryManager repositoryManager) {
//        super(repositoryManager);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        this.mGson = null;
//        this.mApplication = null;
//    }
//
//    @Override
//    public Observable<BaseResponse<BaseArrayData<SpecificationBean>>> goodsSpecification(String goods_sn) {
//        return mRepositoryManager.obtainRetrofitService(BaseApi.class).goodsSpecification(goods_sn);
//    }
//
//    @Override
//    public Observable<BaseResponse<GoodsBean>> cartAdd(String token, String productId, int quantity) {
//        return  mRepositoryManager.obtainRetrofitService(BaseApi.class).cartAdd(token, productId, quantity);
//    }
//}