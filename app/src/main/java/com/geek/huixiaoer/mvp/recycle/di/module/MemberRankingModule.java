package com.geek.huixiaoer.mvp.recycle.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.recycle.contract.MemberRankingContract;
import com.geek.huixiaoer.mvp.recycle.model.MemberRankingModel;


@Module
public class MemberRankingModule {
    private MemberRankingContract.View view;

    /**
     * 构建MemberRankingModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MemberRankingModule(MemberRankingContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MemberRankingContract.View provideMemberRankingView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MemberRankingContract.Model provideMemberRankingModel(MemberRankingModel model) {
        return model;
    }
}