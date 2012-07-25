package com.thoughtworks.hp;

import android.app.ProgressDialog;
import android.content.Context;

public class CustomJavaScriptInterface {

    private Context mContext;
    private ProgressDialog loadingDialog;
    private static final String LOADING_MESSAGE = "Loading";


    public CustomJavaScriptInterface(Context context) {
        this.mContext = context;
//        loadingDialog = ProgressDialog.show(mContext, "", LOADING_MESSAGE, true);
    }

    public void showLoading() {
        if (!loadingDialog.isShowing() && loadingDialog !=null){
            loadingDialog.show();
            loadingDialog.setMessage(LOADING_MESSAGE);
        }
    }

    public void hideLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }


}
