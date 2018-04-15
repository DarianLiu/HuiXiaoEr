package com.geek.huixiaoer.mvp.housewifery.ui.plugin;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.inputmethod.EditorInfo;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.widget.dialog.SimpleEditDialogFragment;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;

/**
 * 支付宝支付Plugin
 * Created by Administrator on 2018/3/8.
 */

public class ALiPayPlugin implements IPluginModule{

    @Override
    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.selector_alipay);
    }

    @Override
    public String obtainTitle(Context context) {
        return context.getString(R.string.plugin_title_alipay);
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
        SimpleEditDialogFragment dialogFragment = new SimpleEditDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.INTENT_DIALOG_TITLE,
                fragment.getActivity().getString(R.string.plugin_title_alipay));
        bundle.putInt(Constants.INTENT_DIALOG_INPUT_TYPE, EditorInfo.TYPE_CLASS_NUMBER);
        dialogFragment.setArguments(bundle);
        dialogFragment.setOnDialogClick(content -> {

        });
        dialogFragment.show(fragment.getActivity().getSupportFragmentManager(), "dialog_edit");
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }
}
