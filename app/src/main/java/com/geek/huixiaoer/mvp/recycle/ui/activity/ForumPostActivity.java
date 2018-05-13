package com.geek.huixiaoer.mvp.recycle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.widget.dialog.CircleProgressDialog;
import com.geek.huixiaoer.common.widget.richEditText.RichTextEditor;
import com.geek.huixiaoer.mvp.recycle.contract.ForumPostContract;
import com.geek.huixiaoer.mvp.recycle.di.component.DaggerForumPostComponent;
import com.geek.huixiaoer.mvp.recycle.di.module.ForumPostModule;
import com.geek.huixiaoer.mvp.recycle.presenter.ForumPostPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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

    private CircleProgressDialog loadingDialog;

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
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        tvToolbarTitle.setText(R.string.title_forum_post);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_upload, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_upload:
                String title = etTitle.getText().toString();
                int size = richText.getImageList();
                if (TextUtils.isEmpty(title)) {
                    showMessage("标题不能为空");
                } else if (size <= 0) {
                    showMessage("必须上传一张图片");
                } else {
                    mPresenter.forumPost(title, richText.buildEditData());
                }
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    for (int i = 0; i < selectList.size(); i++) {
                        // 例如 LocalMedia 里面返回三种path
                        // 1.media.getPath(); 为原图path
                        // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                        // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                        if (selectList.get(i).isCompressed()) {
                            richText.insertImage(selectList.get(i).getCompressPath());
                        } else if (selectList.get(i).isCut()) {

                        } else {

                        }
                    }

                    break;
            }
        }
    }

    @Override
    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new CircleProgressDialog.Builder(this).create();
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //包括裁剪和压缩后的缓存，要在上传成功后调用
        PictureFileUtils.deleteCacheDirFile(ForumPostActivity.this);
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        loadingDialog = null;
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


    @OnClick(R.id.tv_add_pic)
    public void onViewClicked() {
        //打开相册
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.SINGLE)
                .maxSelectNum(9)
                .imageSpanCount(4)
                .previewImage(true)
                .isCamera(true)
                .compress(true)
                .enableCrop(true)
                .glideOverride(400, 400)
//                .hideBottomControls(false)
//                .withAspectRatio(16,9)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
//                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
//                .isDragFrame(true)// 是否可拖动裁剪框(固定)
//                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩

                .forResult(PictureConfig.CHOOSE_REQUEST);
    }
}
