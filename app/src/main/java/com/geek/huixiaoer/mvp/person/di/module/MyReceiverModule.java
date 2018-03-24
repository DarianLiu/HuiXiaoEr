package com.geek.huixiaoer.mvp.person.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.geek.huixiaoer.mvp.person.contract.MyReceiverContract;
import com.geek.huixiaoer.mvp.person.model.MyReceiverModel;
import com.geek.huixiaoer.mvp.person.ui.adapter.ReceiverAdapter;
import com.geek.huixiaoer.storage.entity.shop.ReceiverBean;
import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;


@Module
public class MyReceiverModule {
    private MyReceiverContract.View view;

    /**
     * 构建MyReceiverModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MyReceiverModule(MyReceiverContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyReceiverContract.View provideMyReceiverView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyReceiverContract.Model provideMyReceiverModel(MyReceiverModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getActivity());
    }

    @ActivityScope
    @Provides
    List<ReceiverBean> providerDataList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    RecyclerView.Adapter providerAdapter(List<ReceiverBean> list) {
        return new ReceiverAdapter(list);
    }
}