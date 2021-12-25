package com.useballoon.DaggerModules;

import com.useballoon.Retrofit.API;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.ViewModelComponent;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

@Module
@InstallIn({ActivityComponent.class, ViewModelComponent.class})
public class CompositeDisposableModule {

    public CompositeDisposableModule(){}

    @Provides
    public CompositeDisposable provideCompositeDisposable()  {
        return new CompositeDisposable();
    }
}

