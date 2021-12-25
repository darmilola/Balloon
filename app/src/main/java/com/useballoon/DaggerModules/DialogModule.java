package com.useballoon.DaggerModules;

import android.app.Dialog;
import android.content.Context;

import com.useballoon.Utils.LottieLoadingDialog;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.qualifiers.ActivityContext;

@Module
@InstallIn(ActivityComponent.class)
public class DialogModule {


    public DialogModule(){}

    @Provides
    public Dialog provideDialog(@ActivityContext Context context) {
        return new Dialog(context);
    }

}
