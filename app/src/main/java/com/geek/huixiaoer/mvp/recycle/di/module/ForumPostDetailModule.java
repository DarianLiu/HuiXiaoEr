package com.geek.huixiaoer.mvp.recycle.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.recycle.contract.ForumPostDetailContract;
import com.geek.huixiaoer.mvp.recycle.model.ForumPostDetailModel;


@Module
public class ForumPostDetailModule {
    private ForumPostDetailContract.View view;

    /**
     * 构建ForumPostDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ForumPostDetailModule(ForumPostDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ForumPostDetailContract.View provideForumPostDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ForumPostDetailContract.Model provideForumPostDetailModel(ForumPostDetailModel model) {
        return model;
    }
}