package com.useballoon.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.widget.TextView;

import com.useballoon.R;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ActivityContext;


public class LottieLoadingDialog {


     Dialog loadingDialog;
     Context mContext;

    @Inject
    public LottieLoadingDialog(@ActivityContext Context context){
        this.mContext = context;
        loadingDialog = new Dialog(context);
        loadingDialog.setContentView(R.layout.lottie_loading);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void showLoadingDialog(){
        loadingDialog.show();
    }
    public void cancelLoadingDialog(){
        loadingDialog.dismiss();
    }
}
