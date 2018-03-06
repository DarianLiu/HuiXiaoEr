package com.geek.huixiaoer.common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.huixiaoer.R;


/**
 * 自定义转圈圈dialog
 *
 * @author Jason
 */
public class CircleProgressDialog extends Dialog {

    private AnimationDrawable anim;

    public CircleProgressDialog(Builder builder, int themeResId) {
        super(builder.context, themeResId);
        LayoutInflater inflater = LayoutInflater.from(builder.context);
        View view = inflater.inflate(R.layout.dialog_progress_circle, null);
        ImageView progressCircleImg = view.findViewById(R.id.img_progress_circle);
        TextView progressTextTxt = view.findViewById(R.id.txt_progress_text);
        progressTextTxt.setText(builder.message);
        anim = (AnimationDrawable) builder.context.getResources().getDrawable(R.drawable.loading);
        progressCircleImg.setImageDrawable(anim);
        setContentView(view);
        setCancelable(builder.isCancelable);
        setCanceledOnTouchOutside(builder.isCancelOutside);
    }

    public static class Builder {
        private Context context;
        private String message;
        private boolean isCancelable;
        private boolean isCancelOutside;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置提示信息
         *
         * @param message 提示信息
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * 设置是否可以按返回键取消
         *
         * @param isCancelable 返回键取消
         */
        public Builder setCancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            return this;
        }

        /**
         * 设置是否可以取消
         *
         * @param isCancelOutside 取消
         */
        public Builder setCancelOutside(boolean isCancelOutside) {
            this.isCancelOutside = isCancelOutside;
            return this;
        }

        public CircleProgressDialog create() {
            return new CircleProgressDialog(this, R.style.CircleProgressDialog);
        }


    }

    public void show() {
        super.show();
        if (!anim.isRunning()) {
            anim.start();
        }
    }

    public void dismiss() {
        super.dismiss();
        if (anim.isRunning()) {
            anim.stop();
        }
    }
}