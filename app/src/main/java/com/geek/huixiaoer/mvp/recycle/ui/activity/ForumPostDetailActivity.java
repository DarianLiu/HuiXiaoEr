package com.geek.huixiaoer.mvp.recycle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.utils.AndroidUtil;
import com.geek.huixiaoer.common.utils.DateUtil;
import com.geek.huixiaoer.common.widget.richEditText.RichTextEditor;
import com.geek.huixiaoer.mvp.recycle.contract.ForumPostDetailContract;
import com.geek.huixiaoer.mvp.recycle.di.component.DaggerForumPostDetailComponent;
import com.geek.huixiaoer.mvp.recycle.di.module.ForumPostDetailModule;
import com.geek.huixiaoer.mvp.recycle.presenter.ForumPostDetailPresenter;
import com.geek.huixiaoer.storage.entity.ImageBean;
import com.geek.huixiaoer.storage.entity.recycle.ArticleBean;
import com.geek.huixiaoer.storage.entity.recycle.ContentBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 论坛帖子详情
 */
public class ForumPostDetailActivity extends BaseActivity<ForumPostDetailPresenter> implements ForumPostDetailContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.richText)
    RichTextEditor richText;
    @BindView(R.id.iv_user_head)
    ImageView ivUserHead;
    @BindView(R.id.tv_user_name_date)
    TextView tvUserNameDate;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerForumPostDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .forumPostDetailModule(new ForumPostDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_forum_post_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        tvToolbarTitle.setText(R.string.title_forum_post_detail);

        richText.setIntercept(true);

        ArticleBean articleBean = (ArticleBean) getIntent().getExtras().getSerializable("forum");
        if (articleBean != null) {
            updateView(articleBean);
        }
    }

    /**
     * 更新VIEW
     *
     * @param articleBean 文章详情
     */
    private void updateView(ArticleBean articleBean) {
        GlideArms.with(this).load(articleBean.getMember_headUrl()).circleCrop().into(ivUserHead);
        String name = articleBean.getMember_nickname() == null ? articleBean.getMember_username() : articleBean.getMember_nickname();
        String date = DateUtil.getTime(String.valueOf(articleBean.getCreateDate()), DateUtil.getCurrentDate());
        AndroidUtil.setTextSizeColor(tvUserNameDate, new String[]{name + "\n", date},
                new int[]{ArmsUtils.getColor(this, R.color.color_text_caption),
                        ArmsUtils.getColor(this, R.color.color_text_body)},
                new int[]{13, 10});
        tvTitle.setText(articleBean.getTitle());

        List<ContentBean> contentBeanList = articleBean.getContentList();
        int size = contentBeanList.size();
        for (int i = 0; i < size; i++) {
            if (contentBeanList.get(i).getImage() != null) {
                ImageBean imageBean = contentBeanList.get(i).getImage();
                richText.insertImageByURL(imageBean.getUrl(), imageBean.getHeight(), imageBean.getWidth(), this);
            } else {
                richText.insertText(contentBeanList.get(i).getText());
            }
        }
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
