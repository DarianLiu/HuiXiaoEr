package com.geek.huixiaoer.common.widget.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
        String negative_text = getArguments().getString(Constants.INTENT_DIALOG_NEGATIVE_TEXT);
        String positive_text = getArguments().getString(Constants.INTENT_DIALOG_POSITIVE_TEXT);

        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }

        if (!TextUtils.isEmpty(content)) {
            etContent.setText(content);
        }

        if (!TextUtils.isEmpty(negative_text)) {
            tvCancel.setText(negative_text);
        }

        if (!TextUtils.isEmpty(positive_text)) {
            tvSure.setText(positive_text);
        }

        tvCancel.setOnClickListener(v -> dismiss());

        tvSure.setOnClickListener(v -> {
            if (dialogClickListener != null) {
                dialogClickListener.onConfirmClick(etContent.getText().toString());
            }
            dismiss();
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
