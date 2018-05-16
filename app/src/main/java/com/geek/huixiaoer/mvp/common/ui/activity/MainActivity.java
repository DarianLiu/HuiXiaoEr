package com.geek.huixiaoer.mvp.common.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.mvp.common.contract.MainContract;
import com.geek.huixiaoer.mvp.common.di.component.DaggerMainComponent;
import com.geek.huixiaoer.mvp.common.di.module.MainModule;
import com.geek.huixiaoer.mvp.common.presenter.MainPresenter;
import com.geek.huixiaoer.mvp.common.ui.fragment.TabHomeFragment;
import com.geek.huixiaoer.mvp.common.ui.fragment.TabMessageFragment;
import com.geek.huixiaoer.mvp.person.ui.fragment.TabMineFragment;
import com.geek.huixiaoer.mvp.person.ui.fragment.TabOrderFragment;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.lang.reflect.Field;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bottom_nav)
    BottomNavigationView bottom_nav;

    private Fragment[] fragments;
    private int lastShowFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottom_nav_home:
                    if (lastShowFragment != 0) {
                        switchFragment(lastShowFragment, 0);
                        lastShowFragment = 0;
                        toolbarTitle.setText("智慧生活");
                    }
                    return true;
                case R.id.bottom_nav_message:
                    if (lastShowFragment != 1) {
                        switchFragment(lastShowFragment, 1);
                        lastShowFragment = 1;
                        toolbarTitle.setText(R.string.tab_message);
                    }
                    return true;
                case R.id.bottom_nav_order:
                    if (lastShowFragment != 2) {
                        switchFragment(lastShowFragment, 2);
                        lastShowFragment = 2;
                        toolbarTitle.setText(R.string.tab_order);
                    }
                    return true;
                case R.id.bottom_nav_mine:
                    if (lastShowFragment != 3) {
                        switchFragment(lastShowFragment, 3);
                        lastShowFragment = 3;
                        toolbarTitle.setText(R.string.tab_mine);
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText("智慧生活");

        bottom_nav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        disableShiftMode(bottom_nav);
        initFragment();
        //获取轮播图
//        mPresenter.getBanner();
//        connect("n4pjm6smproL+ZqOY85/bjKi7JFx+PONnfOT+/F9EVfelh52VYGy/NVv1Q3UZvA1GayXo7CeB4uGmkX1jBZ5Og==");
    }

    private void connect(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {
                Log.e(TAG,"====== onTokenIncorrect");
            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                Log.e(TAG,"====== onSuccess userid: "+userid);
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e(TAG,"====== onError errorCode: "+errorCode);
            }
        });
//        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
//            @Override
//            public UserInfo getUserInfo(String s) {
//                return null;
//            }
//        },true);
        Log.e(TAG,"======userid: "+RongIM.getInstance().getCurrentUserId());
    }
    /**
     * 初始化Fragment
     */
    private void initFragment() {
        TabHomeFragment tabHomeFragment = TabHomeFragment.newInstance();
        TabMessageFragment tabMessageFragment = TabMessageFragment.newInstance();
        TabOrderFragment tabOrderFragment = TabOrderFragment.newInstance();
        TabMineFragment tabMineFragment = TabMineFragment.newInstance();
        fragments = new Fragment[]{tabHomeFragment, tabMessageFragment, tabOrderFragment,
                tabMineFragment};
        lastShowFragment = 0;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_content, tabHomeFragment)
                .show(tabHomeFragment)
                .commit();
    }

    /**
     * @param lastIndex 最後選中項目
     * @param index 当前选中项
     */
    private void switchFragment(int lastIndex, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastIndex]);
        if (!fragments[index].isAdded()) {
            transaction.add(R.id.fl_content, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void killMyself() {
        finish();
    }

    /**
     * 取消底部动画移动
     * @param view BottomNavigationView
     */
    public void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Timber.e("disableShiftMode", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Timber.e("disableShiftMode", "Unable to change value of shift mode", e);
        }
    }
}
