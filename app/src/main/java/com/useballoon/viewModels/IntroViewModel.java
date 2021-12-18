package com.useballoon.viewModels;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.useballoon.Intro;
import com.useballoon.Models.IntroResponse;
import com.useballoon.Models.LoginUser;
import com.useballoon.Models.ResponseStatus;
import com.useballoon.Models.SignupUser;
import com.useballoon.Models.User;
import com.useballoon.R;
import com.useballoon.Retrofit.API;
import com.useballoon.Retrofit.RetrofitClient;
import com.useballoon.Utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class IntroViewModel extends AndroidViewModel {
    private API api;
    private Retrofit retrofit;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<IntroResponse> IntroMutableLiveData;
    private IntroResponse introResponse = new IntroResponse();
    private User user;

    public IntroViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<IntroResponse> getIntro() {
        if (IntroMutableLiveData == null) {
            IntroMutableLiveData = new MutableLiveData<>();
        }
        return IntroMutableLiveData;
    }

    public void loadData() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplication());
        String mUserEmail = preferences.getString(getApplication().getString(R.string.saved_user_email), "");

        user = new User(mUserEmail);
        if (!NetworkUtils.isNetworkAvailable(getApplication())) {
            introResponse.setNetworkAvailable(false);
            IntroMutableLiveData.setValue(introResponse);
            return;
        }

        Retrofit retrofit = RetrofitClient.getInstance();
        api = retrofit.create(API.class);
        compositeDisposable.add(api.search(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::HandleResults, this::handleError));
    }

    void HandleResults(IntroResponse introResponse){

        if(introResponse.getStatus().equalsIgnoreCase("success")){
            introResponse.setLoading(false);
            IntroMutableLiveData.setValue(introResponse);
        }
        else if(introResponse.getStatus().equalsIgnoreCase("failure")){
             introResponse.setLoading(false);
             IntroMutableLiveData.setValue(introResponse);
        }
    }

    private void handleError(Throwable t) {
        introResponse.setError(true);
        IntroMutableLiveData.setValue(introResponse);
        Log.e("Error ", t.getLocalizedMessage());
    }
}

