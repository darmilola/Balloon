package com.useballoon.Utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.useballoon.Models.AttachmentResponse;
import com.useballoon.Models.Attachments;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.github.lizhangqu.coreprogress.ProgressHelper;
import io.github.lizhangqu.coreprogress.ProgressUIListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FileUploadUtil {
    private FileUploadDialog fileUploadDialog;
    private String responseJson = "";
    private UploadListener uploadListener;

    public FileUploadUtil(Context context){
           fileUploadDialog = new FileUploadDialog(context);
    }

    public interface UploadListener{
        void onUploadSuccess(Attachments attachments);
    }

    public void setUploadListener(UploadListener uploadListener) {
        this.uploadListener = uploadListener;
    }

    public void uploadFile(File file, String shortTitle, String fileType, String missionId, int missionCreatorId) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        fileUploadDialog.showDialog();
        Runnable runnable = () -> {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(),
                            RequestBody.create(MediaType.parse(fileType), file))
                    .addFormDataPart("shortTitle", shortTitle)
                    .addFormDataPart("missionId", missionId)
                    .addFormDataPart("missionCreatorId", String.valueOf(missionCreatorId))
                    .addFormDataPart("date", dateFormat.format(new Date()))
                    .build();

            Request.Builder builder = new Request.Builder();
            builder.url("https://glacial-spire-23584.herokuapp.com/public/api/artists/mission/upload");

            RequestBody requestBody1 = ProgressHelper.withProgress(requestBody, new ProgressUIListener() {

                @Override
                public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
                    fileUploadDialog.updateProgress(100 * percent);

                }

                //if you don't need this method, don't override this methd. It isn't an abstract method, just an empty method.
                @Override
                public void onUIProgressFinish() {
                    super.onUIProgressFinish();
                }
            });

            //post the wrapped request body
            builder.post(requestBody1);
            Call call = client.newCall(builder.build());
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    Log.e("Error occurred", e.getLocalizedMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response != null) {
                        fileUploadDialog.cancelDialog();
                        responseJson = response.body().string();
                        //Log.e("Response ", response.body().string());
                        Message msg = UploadHandler.obtainMessage();
                        Bundle bundle = new Bundle();
                        bundle.putString("response", responseJson);
                        msg.setData(bundle);
                        UploadHandler.sendMessage(msg);

                    }
                }
            });



        };
        Thread myThread = new Thread(runnable);
        myThread.start();

    };


    private Handler UploadHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NotNull Message msg) {
            fileUploadDialog.cancelDialog();
            Bundle bundle = msg.getData();
            String response = bundle.getString("response");
            Gson gson = new Gson();
            AttachmentResponse attachmentResponse = gson.fromJson(response, AttachmentResponse.class);
            uploadListener.onUploadSuccess(attachmentResponse.getAttachments());

        }
    };




}


