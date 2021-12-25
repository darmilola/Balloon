package com.useballoon.DaggerModules;

import com.useballoon.Retrofit.API;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.ViewModelComponent;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn({ViewModelComponent.class,ActivityComponent.class})
public class ApiModule {

    public ApiModule(){}

    @Provides
    @Inject
    public API provideAPI(Retrofit retrofit)  {
       API api = retrofit.create(API.class);
       return api;
    }

}
