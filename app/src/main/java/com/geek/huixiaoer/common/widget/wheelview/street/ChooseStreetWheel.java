package com.geek.huixiaoer.common.widget.wheelview.street;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.widget.wheelview.base.MyOnWheelChangedListener;
import com.geek.huixiaoer.common.widget.wheelview.base.MyWheelView;
import com.geek.huixiaoer.storage.entity.street.CommunityBean;
import com.geek.huixiaoer.storage.entity.street.RegionBean;
import com.geek.huixiaoer.storage.entity.street.StreetBean;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 市行政区、街道、社区联动选择器
 */
public class ChooseStreetWheel implements MyOnWheelChangedListener {

    @BindView(R.id.region_wheel)
    MyWheelView regionWheel;
    @BindView(R.id.street_wheel)
    MyWheelView streetWheel;
    @BindView(R.id.community_wheel)
    MyWheelView communityWheel;

    private Activity context;
    private View parentView;
    private PopupWindow popupWindow = null;
    private WindowManager.LayoutParams layoutParams = null;
    private LayoutInflater layoutInflater = null;

    private List<RegionBean> regionList = null;

    private OnStreetChangeListener onStreetChangeListener = null;

    public ChooseStreetWheel(Activity context) {
        this.context = context;
        init();
    }

    private void init() {
        layoutParams = context.getWindow().getAttributes();
        layoutInflater = context.getLayoutInflater();
        initView();
        initPopupWindow();
    }

    private void initView() {
        parentView = layoutInflater.inflate(R.layout.include_select_street, null);
        ButterKnife.bind(this, parentView);

        regionWheel.setVisibleItems(7);
        streetWheel.setVisibleItems(7);
        communityWheel.setVisibleItems(7);

        regionWheel.addChangingListener(this);
        streetWheel.addChangingListener(this);
        communityWheel.addChangingListener(this);
    }

    private void initPopupWindow() {
        popupWindow = new PopupWindow(parentView, WindowManager.LayoutParams.MATCH_PARENT, (int) (ArmsUtils.getScreenHeidth(context) * (2.0 / 5)));
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setAnimationStyle(R.style.anim_push_bottom);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                layoutParams.alpha = 1.0f;
                context.getWindow().setAttributes(layoutParams);
                popupWindow.dismiss();
            }
        });
    }

    private void bindData() {
        regionWheel.setViewAdapter(new RegionWheelAdapter(context, regionList));
        updateStreet();
        updateCommunity();
    }

    @Override
    public void onChanged(MyWheelView wheel, int oldValue, int newValue) {
        if (wheel == regionWheel) {
            updateStreet();//行政区改变后街道联动
        } else if (wheel == streetWheel) {
            updateCommunity();//街道改变后社区联动
        }
    }

    private void updateStreet() {
        int index = regionWheel.getCurrentItem();
        List<StreetBean> streetList = regionList.get(index).getStreet();
        if (streetList != null && streetList.size() > 0) {
            streetWheel.setViewAdapter(new StreetWheelAdapter(context, streetList));
            streetWheel.setCurrentItem(0);
        }
    }

    private void updateCommunity() {
        int regionIndex = regionWheel.getCurrentItem();
        List<StreetBean> streetList = regionList.get(regionIndex).getStreet();
        int streetIndex = regionWheel.getCurrentItem();
        List<CommunityBean> communityList = streetList.get(streetIndex).getCommunity();
        if (communityList != null && communityList.size() > 0) {
            communityWheel.setViewAdapter(new CommunityWheelAdapter(context, communityList));
            communityWheel.setCurrentItem(0);
        }
    }

    public void show(View v) {
        layoutParams.alpha = 0.6f;
        context.getWindow().setAttributes(layoutParams);
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
    }

    public void setRegion(List<RegionBean> regionList) {
        this.regionList = regionList;
        bindData();
    }

    public void defaultValue(String regionStr, String streetStr, String communityStr) {
        if (TextUtils.isEmpty(regionStr)) return;
        for (int i = 0; i < regionList.size(); i++) {
            RegionBean region = regionList.get(i);
            if (region != null && region.getName().equalsIgnoreCase(streetStr)) {
                regionWheel.setCurrentItem(i);
                if (TextUtils.isEmpty(streetStr)) return;
                List<StreetBean> streetList = region.getStreet();
                for (int j = 0; j < streetList.size(); j++) {
                    StreetBean streetBean = streetList.get(j);
                    if (streetBean != null && streetBean.getName().equalsIgnoreCase(streetStr)) {
                        regionWheel.setViewAdapter(new StreetWheelAdapter(context, streetList));
                        regionWheel.setCurrentItem(j);
                        if (TextUtils.isEmpty(communityStr)) return;
                        List<CommunityBean> communityList = streetBean.getCommunity();
                        for (int k = 0; k < communityList.size(); k++) {
                            CommunityBean communityBean = communityList.get(k);
                            if (communityBean != null && communityBean.getName().equalsIgnoreCase(communityStr)) {
                                communityWheel.setViewAdapter(new CommunityWheelAdapter(context, communityList));
                                communityWheel.setCurrentItem(k);
                            }
                        }
                    }
                }
            }
        }
    }

    @OnClick(R.id.confirm_button)
    public void confirm() {
        if (onStreetChangeListener != null) {
            int regionIndex = regionWheel.getCurrentItem();
            int streetIndex = streetWheel.getCurrentItem();
            int communityIndex = communityWheel.getCurrentItem();

            String regionName = null, streetName = null, communityName = null, regionId = null,
                    streetId = null, communityId = null;

            List<StreetBean> streetList = null;
            if (regionList != null && regionList.size() > regionIndex) {
                RegionBean regionBean = regionList.get(regionIndex);
                streetList = regionBean.getStreet();
                regionName = regionBean.getName();
                regionId = regionBean.getId();
            }

            List<CommunityBean> communityList = null;
            if (streetList != null && streetList.size() > streetIndex) {
                StreetBean streetBean = streetList.get(streetIndex);
                communityList = streetBean.getCommunity();
                streetName = streetBean.getName();
                streetId = streetBean.getId();
            }

            if (communityList != null && communityList.size() > communityIndex) {
                CommunityBean communityBean = communityList.get(communityIndex);
                communityName = communityBean.getName();
                communityId = communityBean.getId();
            }

            onStreetChangeListener.onAddressChange(regionName, streetName, communityName, regionId, streetId, communityId);
        }
        cancel();
    }

    @OnClick(R.id.cancel_button)
    public void cancel() {
        popupWindow.dismiss();
    }

    public void setOnStreetChangeListener(OnStreetChangeListener onStreetChangeListener) {
        this.onStreetChangeListener = onStreetChangeListener;
    }

}