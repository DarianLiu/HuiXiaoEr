//package com.geek.huixiaoer.mvp.supermarket.di.module;
//
//import com.jess.arms.di.scope.ActivityScope;
//
//import dagger.Module;
//import dagger.Provides;
//
//import com.geek.huixiaoer.mvp.supermarket.contract.ProductSelectContract;
//import com.geek.huixiaoer.mvp.supermarket.model.ProductSelectModel;
//
//
//@Module
//public class ProductSelectModule {
//    private ProductSelectContract.View view;
//
//    /**
//     * 构建ProductSelectModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
//     *
//     * @param view
//     */
//    public ProductSelectModule(ProductSelectContract.View view) {
//        this.view = view;
//    }
//
//    @ActivityScope
//    @Provides
//    ProductSelectContract.View provideProductSelectView() {
//        return this.view;
//    }
//
//    @ActivityScope
//    @Provides
//    ProductSelectContract.Model provideProductSelectModel(ProductSelectModel model) {
//        return model;
//    }
//}