package com.geek.huixiaoer.mvp.recycle.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.geek.huixiaoer.mvp.recycle.contract.RecycleListContract;
import com.geek.huixiaoer.mvp.recycle.model.RecycleListModel;
import com.geek.huixiaoer.mvp.recycle.ui.adpater.RecycleAdapter;
import com.geek.huixiaoer.storage.entity.article.ArticleBean;
import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;


@Module
public class RecycleListModule {
    private RecycleListContract.View view;

    /**
     * 构建RecycleListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public RecycleListModule(RecycleListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RecycleListContract.View provideRecycleListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    RecycleListContract.Model provideRecycleListModel(RecycleListModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getActivity());
    }

    @ActivityScope
    @Provides
    List<ArticleBean> providerDataList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    RecyclerView.Adapter providerAdapter(List<ArticleBean> list) {
        return new RecycleAdapter(list);
    }
}