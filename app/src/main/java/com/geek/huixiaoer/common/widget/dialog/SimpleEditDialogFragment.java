package com.geek.huixiaoer.common.widget.dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.geek.huixiaoer.R;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.common.widget.MoneyValueFilter;

/**
 * 带输入框的Dialog
 * Created by Administrator on 2018/3/6.
 */
public class SimpleEditDialogFragment extends DialogFragment {

    private DialogClickListener dialogClickListener;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);
            // 这里用透明颜色替换掉系统自带背景
            int color = ContextCompat.getColor(getActivity(), android.R.color.transparent);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(color));
        }
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edittext, container, false);
        TextView tvTitle = dialogView.findViewById(R.id.tv_title);
        EditText etContent = dialogView.findViewById(R.id.et_content);
        TextView tvCancel = dialogView.findViewById(R.id.tv_cancel);
        TextView tvSure = dialogView.findViewById(R.id.tv_sure);

        String title = getArguments().getString(Constants.INTENT_DIALOG_TITLE);
        String content = getArguments().getString(Constants.INTENT_DIALOG_MESSAGE);
        int input_type = getArguments().getInt(Constants.INTENT_DIALOG_INPUT_TYPE, -1);
        String negative_text = getArguments().getString(Constants.INTENT_DIALOG_NEGATIVE_TEXT);
        String positive_text = getArguments().getString(Constants.INTENT_DIALOG_POSITIVE_TEXT);

        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }

        if (!TextUtils.isEmpty(content)) {
            etContent.setText(content);
        }

        if (input_type != -1) {
            etContent.setInputType(input_type);
            etContent.setFilters(new InputFilter[]{new MoneyValueFilter()});
        }

        if (!TextUtils.isEmpty(negative_text)) {
            tvCancel.setText(negative_text);
        }

        if (!TextUtils.isEmpty(positive_text)) {
            tvSure.setText(positive_text);
        }

        tvCancel.setOnClickListener(v -> dismiss());

        tvSure.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(etContent.getText().toString())) {
                if (dialogClickListener != null) {
                    dialogClickListener.onConfirmClick(etContent.getText().toString());
                }
                dismiss();
            }
        });
        return dialogView;
    }

    public void setOnDialogClick(DialogClickListener dialogClickListener) {
        this.dialogClickListener = dialogClickListener;
    }

    public interface DialogClickListener {

        void onConfirmClick(String content);

    }
}
