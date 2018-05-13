package com.geek.huixiaoer.mvp.recycle.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.geek.huixiaoer.mvp.recycle.ui.adpater.ForumAdapter;
import com.geek.huixiaoer.storage.entity.recycle.ArticleBean;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.recycle.contract.ForumContract;
import com.geek.huixiaoer.mvp.recycle.model.ForumModel;

import java.util.ArrayList;
import java.util.List;


@Module
public class ForumModule {
    private ForumContract.View view;

    /**
     * 构建ForumModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ForumModule(ForumContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ForumContract.View provideForumView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ForumContract.Model provideForumModel(ForumModel model) {
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
        return new ForumAdapter(list);
    }
}