package com.useballoon.viewModels;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.useballoon.Models.Mission;
import com.useballoon.Models.ResponseStatus;
import com.useballoon.R;
import com.useballoon.Retrofit.API;
import com.useballoon.Retrofit.RetrofitClient;
import com.useballoon.Utils.NetworkUtils;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

@HiltViewModel
public class Step2ViewModel extends ViewModel {
    public MutableLiveData<String> step1 = new MutableLiveData<>();
    public MutableLiveData<String> step2 = new MutableLiveData<>();
    public MutableLiveData<String> step3 = new MutableLiveData<>();
    public MutableLiveData<String> step4 = new MutableLiveData<>();
    public MutableLiveData<String> step5 = new MutableLiveData<>();

    private MutableLiveData<Mission> userMutableLiveData;
    @Inject
    API api;
    @Inject
    CompositeDisposable compositeDisposable;
    @Inject
    Retrofit retrofit;
    private Mission mission;


    @Inject
    public Step2ViewModel(){
    }

    public MutableLiveData<Mission> getMission() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }




    public void onClick(View view) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        int creatorId = preferences.getInt(view.getContext().getString(R.string.saved_user_id), 0);
        String missionId = preferences.getString(view.getContext().getString(R.string.saved_mission_id), "");

        Toast.makeText(view.getContext(), missionId, Toast.LENGTH_SHORT).show();

        mission = new Mission(step1.getValue(), step2.getValue(), step3.getValue(), step4.getValue(), step5.getValue(),missionId,creatorId);

        if(!NetworkUtils.isNetworkAvailable(view.getContext())){
            mission.setNetworkAvailable(false);
            userMutableLiveData.setValue(mission);
            return;
        }


        mission.setNetworkAvailable(true);

        if(!TextUtils.isEmpty(step1.getValue()) && !TextUtils.isEmpty(step2.getValue()) && !TextUtils.isEmpty(step3.getValue())){

            mission.setLoading(true);
            userMutableLiveData.setValue(mission);

            compositeDisposable.add(api.updateMission(mission)
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