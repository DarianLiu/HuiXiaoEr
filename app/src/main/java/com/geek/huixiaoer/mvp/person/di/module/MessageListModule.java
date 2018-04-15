package com.geek.huixiaoer.mvp.person.di.module;

import android.support.v7.widget.RecyclerView;

import com.geek.huixiaoer.mvp.person.ui.adapter.MessageAdapter;
import com.geek.huixiaoer.mvp.person.ui.adapter.ReceiverAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.geek.huixiaoer.mvp.person.contract.MessageListContract;
import com.geek.huixiaoer.mvp.person.model.MessageListModel;

import java.util.ArrayList;
import java.util.List;


@Module
public class MessageListModule {
    private MessageListContract.View view;

    /**
     * 构建MessageListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MessageListModule(MessageListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MessageListContract.View provideMessageListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MessageListContract.Model provideMessageListModel(MessageListModel model) {
        return model;
    }

}