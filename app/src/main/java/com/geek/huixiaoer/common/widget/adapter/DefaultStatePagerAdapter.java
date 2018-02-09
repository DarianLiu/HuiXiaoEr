package com.geek.huixiaoer.common.widget.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 商品列表适配器
 */
public class DefaultStatePagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> list_fragment;                         //fragment列表
    private List<String> titles;                              //tab名的列表

    public DefaultStatePagerAdapter(FragmentManager fm, List<Fragment> list_fragment, List<String> titles) {
        super(fm);
        this.list_fragment = list_fragment;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }
    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
