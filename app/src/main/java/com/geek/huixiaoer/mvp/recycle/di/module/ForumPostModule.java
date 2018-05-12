package com.geek.huixiaoer.mvp.recycle.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.recycle.contract.ForumPostContract;
import com.geek.huixiaoer.mvp.recycle.model.ForumPostModel;


@Module
public class ForumPostModule {
    private ForumPostContract.View view;

    /**
     * 构建ForumPostModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ForumPostModule(ForumPostContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ForumPostContract.View provideForumPostView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ForumPostContract.Model provideForumPostModel(ForumPostModel model) {
        return model;
    }
}