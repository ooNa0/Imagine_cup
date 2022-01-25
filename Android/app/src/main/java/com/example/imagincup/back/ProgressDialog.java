package com.example.imagincup.back;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.example.imagincup.R;

public class ProgressDialog {
    private Dialog dialog;
    private static volatile ProgressDialog mInstance = null;

    //  Bum 성능상의 이슈
    public static ProgressDialog getInstance() {
        if (mInstance == null) {
            synchronized (ProgressDialog.class) {
                if (mInstance == null) {
                    mInstance = new ProgressDialog();
                }
            }
        }
        return mInstance;
    }

    public void show(Context context) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new Dialog(context, R.style.ProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_progress_dialog);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
