package com.useballoon.Utils;

import android.app.Dialog;
import android.content.Context;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.useballoon.R;

public class FileUploadDialog {

    private Context mContext;
    private Dialog uploadDialog;
    private NumberProgressBar numberProgressBar;

    public FileUploadDialog(Context context){
        this.mContext = context;
        uploadDialog = new Dialog(mContext);
        uploadDialog.setContentView(R.layout.upload_progressbar_layout);
        uploadDialog.setCancelable(false);
        numberProgressBar = uploadDialog.findViewById(R.id.image_upload_progress_bar);
        numberProgressBar.setMax(100);
    }

    public void showDialog(){
        uploadDialog.show();
    }

    public void cancelDialog(){
        uploadDialog.dismiss();
    }

    public void updateProgress(float progress){
        numberProgressBar.setProgress((int) progress);
    }
}
