package com.geek.huixiaoer.mvp.recycle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.widget.dialog.CircleProgressDialog;
import com.geek.huixiaoer.common.widget.recyclerview.GridSpacingItemDecoration;
import com.geek.huixiaoer.common.widget.recyclerview.MoveCallBack;
import com.geek.huixiaoer.mvp.recycle.contract.RecycleAddContract;
import com.geek.huixiaoer.mvp.recycle.di.component.DaggerRecycleAddComponent;
import com.geek.huixiaoer.mvp.recycle.di.module.RecycleAddModule;
import com.geek.huixiaoer.mvp.recycle.presenter.RecycleAddPresenter;
import com.geek.huixiaoer.mvp.recycle.ui.adpater.RecycleAddAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
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
    @BindView(R.id.tv_delete)
    TextView tvDelete;

    @BindString(R.string.error_content_null)
    String errorContentNull;
    @BindString(R.string.error_image_null)
    String errorImageNull;

    private List<String> mImageList = new ArrayList<>();
    private RecycleAddAdapter mAdapter;

    private CircleProgressDialog loadingDialog;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_upload, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_upload:
                String content = etContent.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    showMessage(errorContentNull);
                } else if (mImageList.size() <= 0) {
                    showMessage(errorImageNull);
                } else {
                    mPresenter.recycleAdd(content, mImageList);
                }
                break;
        }
        return true;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        tvToolbarTitle.setText(R.string.title_release);

//        etContent.setOnClickListener(v ->{
//            etContent.setFocusable(true);
//            etContent.setFocusableInTouchMode(true);
//            etContent.requestFocus();
//        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(4, 15, false));
        mAdapter = new RecycleAddAdapter(this, mImageList);
        recyclerView.setAdapter(mAdapter);

        //RecyclerView设置拖拽排序和拖到底部删除
        MoveCallBack callBack = new MoveCallBack(mAdapter, mImageList);
        callBack.setDragListener(new MoveCallBack.DragListener() {
            @Override
            public void deleteState(boolean delete) {
                if (delete) {
                    tvDelete.setAlpha(0.6f);
                    tvDelete.setText(R.string.loose_hand_can_be_deleted);
                } else {
                    tvDelete.setAlpha(0.8f);
                    tvDelete.setText(R.string.drag_to_delete_here);
                }
            }

            @Override
            public void dragState(boolean start) {
                if (start) {
                    tvDelete.setVisibility(View.VISIBLE);
                } else {
                    tvDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void clearView() {

            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        //长按点击事件处理
        GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            //长按事件
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null) {
                    int position = recyclerView.getChildLayoutPosition(childView);
                    //设置添加按钮不能拖动
                    if (mAdapter.getItemViewType(position) == 1) {
                        itemTouchHelper.startDrag(recyclerView.getChildViewHolder(childView));
                    }
                }
            }
        });

        //触摸事件监听
        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return gestureDetector.onTouchEvent(e);
            }

        });

        //item点击事件监听
        mAdapter.setOnItemClickListener(new RecycleAddAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onAddClick(int position) {
                //打开相册
                PictureSelector.create(RecycleAddActivity.this)
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(9)
                        .imageSpanCount(4)
                        .previewImage(true)
                        .isCamera(true)
                        .compress(true)
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .glideOverride(400, 400)
                        .forResult(PictureConfig.CHOOSE_REQUEST);

            }

            //图片预览
            @Override
            public void onPreviewClick(List<String> images, int position) {

            }
        });
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
                            mImageList.add(selectList.get(i).getCompressPath());
                        } else if (selectList.get(i).isCut()) {
                            mImageList.add(selectList.get(i).getCutPath());
                        } else {
                            mImageList.add(selectList.get(i).getPath());
                        }
                    }

                    mAdapter.notifyDataSetChanged();
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
    protected void onDestroy() {
        super.onDestroy();
        //包括裁剪和压缩后的缓存，要在上传成功后调用
        PictureFileUtils.deleteCacheDirFile(RecycleAddActivity.this);
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        loadingDialog = null;
    }

    @Override
    public void killMyself() {
        finish();
    }

}
