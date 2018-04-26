package com.geek.huixiaoer.mvp.recycle.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.geek.huixiaoer.mvp.recycle.ui.adpater.RankAdapter;
import com.geek.huixiaoer.storage.entity.recycle.RankBean;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.recycle.contract.RankContract;
import com.geek.huixiaoer.mvp.recycle.model.RankModel;

import java.util.ArrayList;
import java.util.List;


@Module
public class RankModule {
    private RankContract.View view;

    /**
     * 构建RankModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public RankModule(RankContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RankContract.View provideRankView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    RankContract.Model provideRankModel(RankModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getContext());
    }

    @ActivityScope
    @Provides
    List<RankBean> providerDataList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    RecyclerView.Adapter providerAdapter(List<RankBean> list) {
        return new RankAdapter(list);
    }
}