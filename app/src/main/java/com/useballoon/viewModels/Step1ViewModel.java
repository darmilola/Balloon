package com.useballoon.viewModels;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.useballoon.ForgotAccount;
import com.useballoon.Models.LoginUser;
import com.useballoon.Models.Mission;
import com.useballoon.Models.ResponseStatus;
import com.useballoon.R;
import com.useballoon.Retrofit.API;
import com.useballoon.Retrofit.RetrofitClient;
import com.useballoon.Signup;
import com.useballoon.Utils.NetworkUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Step1ViewModel extends AndroidViewModel {
    public MutableLiveData<String> subject = new MutableLiveData<>();
    public MutableLiveData<String> url = new MutableLiveData<>();
    private MutableLiveData<Mission> userMutableLiveData;
    private API api;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Mission mission;

    public Step1ViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Mission> getMission() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    // function to generate a random string of length n
    static String generateCodedId()
    {
        // chose a Character random from this String
        String AlphaNumericString = "0123456789";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }



    public void onClick(View view) {

        String missionCodedId = generateCodedId();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplication());

        int mUserId = preferences.getInt(view.getContext().getString(R.string.saved_user_id), 0);
        preferences.edit().putString(view.getContext().getString(R.string.saved_mission_id),missionCodedId).apply();

        Toast.makeText(view.getContext(), Integer.toString(mUserId), Toast.LENGTH_SHORT).show();
        mission = new Mission(subject.getValue(), url.getValue(),missionCodedId,mUserId);

        if(!NetworkUtils.isNetworkAvailable(view.getContext())){
            mission.setNetworkAvailable(false);
            userMutableLiveData.setValue(mission);
            return;
        }


        mission.setNetworkAvailable(true);

        if(!TextUtils.isEmpty(subject.getValue()) && !TextUtils.isEmpty(url.getValue())){

            mission.setLoading(true);
            userMutableLiveData.setValue(mission);

            Retrofit retrofit = RetrofitClient.getInstance();
            api = retrofit.create(API.class);
            compositeDisposable.add(api.createMission(mission)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::HandleResults, this::handleError));
        }
        else {
            mission.setLoading(false);
            userMutableLiveData.setValue(mission);
        }
    }

    void HandleResults(ResponseStatus responseStatus){

        if(responseStatus.getStatus().equalsIgnoreCase("success")){
            mission.setLoading(false);
            mission.setIsSaved(true);
            userMutableLiveData.setValue(mission);
        }
        else if(responseStatus.getStatus().equalsIgnoreCase("failure")){
            mission.setLoading(false);
            mission.setIsSaved(false);
            userMutableLiveData.setValue(mission);
        }
    }

    private void handleError(Throwable t) {
        mission.setError(true);
        userMutableLiveData.setValue(mission);
    }

}
