package com.geek.huixiaoer.mvp.dinner.di.module;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.geek.huixiaoer.mvp.supermarket.ui.adapter.GoodsAdapter;
import com.geek.huixiaoer.storage.entity.shop.GoodsBean;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.dinner.contract.DinnerContract;
import com.geek.huixiaoer.mvp.dinner.model.DinnerModel;

import java.util.ArrayList;
import java.util.List;


@Module
public class DinnerModule {
    private DinnerContract.View view;

    /**
     * 构建DinnerModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public DinnerModule(DinnerContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    DinnerContract.View provideDinnerView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    DinnerContract.Model provideDinnerModel(DinnerModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    @ActivityScope
    @Provides
    List<GoodsBean> providerDataList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    RecyclerView.Adapter providerAdapter(List<GoodsBean> list) {
        return new GoodsAdapter(list);
    }
}