package com.geek.huixiaoer.mvp.recycle.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.geek.huixiaoer.mvp.recycle.ui.adpater.BlackAdapter;
import com.geek.huixiaoer.storage.entity.recycle.BlackBean;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.recycle.contract.BlackListContract;
import com.geek.huixiaoer.mvp.recycle.model.BlackListModel;

import java.util.ArrayList;
import java.util.List;


@Module
public class BlackListModule {
    private BlackListContract.View view;

    /**
     * 构建BlackListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public BlackListModule(BlackListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    BlackListContract.View provideBlackListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    BlackListContract.Model provideBlackListModel(BlackListModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getActivity());
    }

    @ActivityScope
    @Provides
    List<BlackBean> providerDataList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    RecyclerView.Adapter providerAdapter(List<BlackBean> list) {
        return new BlackAdapter(list);
    }
}