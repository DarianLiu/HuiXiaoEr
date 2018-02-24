package com.geek.huixiaoer.mvp.recycle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.widget.recyclerview.GridSpacingItemDecoration;
import com.geek.huixiaoer.mvp.recycle.contract.RecycleAddContract;
import com.geek.huixiaoer.mvp.recycle.di.component.DaggerRecycleAddComponent;
import com.geek.huixiaoer.mvp.recycle.di.module.RecycleAddModule;
import com.geek.huixiaoer.mvp.recycle.presenter.RecycleAddPresenter;
import com.geek.huixiaoer.mvp.recycle.ui.adpater.GridAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class RecycleAddActivity extends BaseActivity<RecycleAddPresenter> implements RecycleAddContract.View {


    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.rv_image)
    RecyclerView recyclerView;

    private List<String> mImageList = new ArrayList<>();
    private GridAdapter mAdapter;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerRecycleAddComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .recycleAddModule(new RecycleAddModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_recycle_add; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        tvToolbarTitle.setText(R.string.title_release);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(4, 10, false));
        mAdapter = new GridAdapter(this, mImageList);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new GridAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onAddClick(int position) {

            }

            @Override
            public void onRemoveClick(int position) {

            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

}
