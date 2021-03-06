package com.geek.huixiaoer.mvp.housewifery.ui.plugin;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.mvp.housewifery.ui.activity.HelpOrderConfirmActivity;
import com.jess.arms.utils.ArmsUtils;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;

/**
 * 支付宝支付Plugin
 * Created by Administrator on 2018/3/8.
 */

public class ALiPayPlugin implements IPluginModule {

    Context context;

    @Override
    public Drawable obtainDrawable(Context context) {
        this.context = context;
        return ContextCompat.getDrawable(context, R.drawable.selector_alipay);
    }

    @Override
    public String obtainTitle(Context context) {
        return context.getString(R.string.plugin_title_alipay);
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
        String targetId = rongExtension.getTargetId();
        String title = fragment.getActivity().getIntent().getData().getQueryParameter("title");
        Intent intent = new Intent(context, HelpOrderConfirmActivity.class);
        intent.putExtra("title", title);
        Log.d("====ALiPayPlugin-title",title);
        intent.putExtra("targetId", targetId);
        ArmsUtils.startActivity(intent);
//        SimpleEditDialogFragment dialogFragment = new SimpleEditDialogFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString(Constants.INTENT_DIALOG_TITLE,
//                fragment.getActivity().getString(R.string.plugin_title_alipay));
//        bundle.putInt(Constants.INTENT_DIALOG_INPUT_TYPE, EditorInfo.TYPE_CLASS_NUMBER);
//        dialogFragment.setArguments(bundle);
//        dialogFragment.setOnDialogClick(content -> {
//
//        });
//        dialogFragment.show(fragment.getActivity().getSupportFragmentManager(), "dialog_edit");
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }
}
