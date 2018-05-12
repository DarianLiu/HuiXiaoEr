package com.geek.huixiaoer.mvp.recycle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.widget.richEditText.RichTextEditor;
import com.geek.huixiaoer.mvp.recycle.contract.ForumPostContract;
import com.geek.huixiaoer.mvp.recycle.di.component.DaggerForumPostComponent;
import com.geek.huixiaoer.mvp.recycle.di.module.ForumPostModule;
import com.geek.huixiaoer.mvp.recycle.presenter.ForumPostPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 论坛发帖
 */
public class ForumPostActivity extends BaseActivity<ForumPostPresenter> implements ForumPostContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.richText)
    RichTextEditor richText;
    @BindView(R.id.tv_add_pic)
    TextView tvAddPic;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerForumPostComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .forumPostModule(new ForumPostModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_forum_post; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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
