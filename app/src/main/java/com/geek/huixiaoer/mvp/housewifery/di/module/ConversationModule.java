package com.geek.huixiaoer.mvp.housewifery.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.housewifery.contract.ConversationContract;
import com.geek.huixiaoer.mvp.housewifery.model.ConversationModel;


@Module
public class ConversationModule {
    private ConversationContract.View view;

    /**
     * 构建ConversationModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ConversationModule(ConversationContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ConversationContract.View provideConversationView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ConversationContract.Model provideConversationModel(ConversationModel model) {
        return model;
    }
}