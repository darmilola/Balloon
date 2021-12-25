package com.useballoon.DaggerModules;


import android.app.Dialog;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.qualifiers.ActivityContext;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn({ActivityComponent.class, ViewModelComponent.class})
public class RetrofitModule {

    public RetrofitModule(){}

    @Provides
    public Retrofit provideRetrofit()  {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://glacial-spire-23584.herokuapp.com/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }
}
