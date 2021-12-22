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

import com.useballoon.Models.Mission;
import com.useballoon.Models.ResponseStatus;
import com.useballoon.R;
import com.useballoon.Retrofit.API;
import com.useballoon.Retrofit.RetrofitClient;
import com.useballoon.Utils.NetworkUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Step3ViewModel extends AndroidViewModel {
    public MutableLiveData<String> envangelistCount = new MutableLiveData<>();
    public MutableLiveData<String> compensationAmount = new MutableLiveData<>();
    public MutableLiveData<String> proofOfExecution = new MutableLiveData<>();


    private MutableLiveData<Mission> userMutableLiveData;
    private API api;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Mission mission;

    public Step3ViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Mission> getMission() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }


    public void onClick(View view) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplication());
        int creatorId = preferences.getInt(view.getContext().getString(R.string.saved_user_id), 0);
        String missionId = preferences.getString(view.getContext().getString(R.string.saved_mission_id), "");


        Toast.makeText(view.getContext(), missionId, Toast.LENGTH_SHORT).show();

        mission = new Mission(envangelistCount.getValue(), compensationAmount.getValue(), proofOfExecution.getValue(), missionId, creatorId);

        if (!NetworkUtils.isNetworkAvailable(view.getContext())) {
            mission.setNetworkAvailable(false);
            userMutableLiveData.setValue(mission);
            return;
        }

        mission.setNetworkAvailable(true);

        if (!TextUtils.isEmpty(envangelistCount.getValue()) && !TextUtils.isEmpty(compensationAmount.getValue()) && !TextUtils.isEmpty(proofOfExecution.getValue())) {

            mission.setLoading(true);
            userMutableLiveData.setValue(mission);

            Retrofit retrofit = RetrofitClient.getInstance();
            api = retrofit.create(API.class);
            compositeDisposable.add(api.updateMission(mission)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::HandleResults, this::handleError));
        } else {
            mission.setLoading(false);
            userMutableLiveData.setValue(mission);
        }
    }

    void HandleResults(ResponseStatus responseStatus) {

        if (responseStatus.getStatus().equalsIgnoreCase("success")) {
            mission.setLoading(false);
            mission.setIsSaved(true);
            userMutableLiveData.setValue(mission);
        } else if (responseStatus.getStatus().equalsIgnoreCase("failure")) {
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

