package com.geek.huixiaoer.mvp.supermarket.di.module;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.geek.huixiaoer.mvp.supermarket.contract.GoodsListContract;
import com.geek.huixiaoer.mvp.supermarket.ui.adapter.GoodsAdapter;
import com.geek.huixiaoer.storage.entity.GoodsBean;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.supermarket.model.GoodsListModel;

import java.util.ArrayList;
import java.util.List;


@Module
public class GoodsListModule {
    private GoodsListContract.View view;

    /**
     * 构建GoodsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     */
    public GoodsListModule(GoodsListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    GoodsListContract.View provideGoodsView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    GoodsListContract.Model provideGoodsModel(GoodsListModel model) {
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