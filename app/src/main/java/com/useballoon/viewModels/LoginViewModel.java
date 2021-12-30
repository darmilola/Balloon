package com.useballoon.viewModels;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.useballoon.ForgotAccount;
import com.useballoon.Models.LoginUser;
import com.useballoon.Models.ResponseStatus;
import com.useballoon.Models.SignupUser;
import com.useballoon.Retrofit.API;
import com.useballoon.Retrofit.RetrofitClient;
import com.useballoon.Signup;
import com.useballoon.Utils.NetworkUtils;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


@HiltViewModel
public class LoginViewModel extends ViewModel {
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    private MutableLiveData<LoginUser> userMutableLiveData;
    @Inject
    API api;
    @Inject
    CompositeDisposable compositeDisposable;
    private LoginUser loginUser;
    @Inject
    Retrofit retrofit;

            @Inject
    public LoginViewModel(){

    }

    public MutableLiveData<LoginUser> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void createNewAccount(View view){
        view.getContext().startActivity(new Intent(view.getContext(),Signup.class));
    }

    public void forgotPasssword(View view){
        view.getContext().startActivity(new Intent(view.getContext(), ForgotAccount.class));
    }


    public void onClick(View view) {

        loginUser = new LoginUser(email.getValue(), password.getValue());

        if(!NetworkUtils.isNetworkAvailable(view.getContext())){
            loginUser.setNetworkAvailable(false);
            userMutableLiveData.setValue(loginUser);
            return;
        }


        loginUser.setNetworkAvailable(true);
        if(!TextUtils.isEmpty(email.getValue()) && !TextUtils.isEmpty(password.getValue())){

            loginUser.setIsLoading(true);
            userMutableLiveData.setValue(loginUser);
            compositeDisposable.add(api.login(loginUser)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::HandleResults, this::handleError));
        }
        else {
            loginUser.setIsLoading(false);
            userMutableLiveData.setValue(loginUser);
        }
    }

    void HandleResults(ResponseStatus responseStatus){

        if(responseStatus.getStatus().equalsIgnoreCase("success")){
            loginUser.setIsLoading(false);
            loginUser.setLoginStatus(true);
            userMutableLiveData.setValue(loginUser);
        }
        else if(responseStatus.getStatus().equalsIgnoreCase("failure")){
            loginUser.setIsLoading(false);
            loginUser.setLoginStatus(false);
            userMutableLiveData.setValue(loginUser);
        }
    }

    private void handleError(Throwable t) {
           loginUser.setError(true);
           userMutableLiveData.setValue(loginUser);
    }

}
