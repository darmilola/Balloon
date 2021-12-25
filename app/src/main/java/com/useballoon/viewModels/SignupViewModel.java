package com.useballoon.viewModels;

import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.useballoon.Models.LoginUser;
import com.useballoon.Models.ResponseStatus;
import com.useballoon.Models.SignupUser;
import com.useballoon.Retrofit.API;
import com.useballoon.Retrofit.RetrofitClient;
import com.useballoon.Utils.NetworkUtils;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

@HiltViewModel
public class SignupViewModel extends ViewModel {
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> firstname = new MutableLiveData<>();
    public MutableLiveData<String> lastname = new MutableLiveData<>();
    public MutableLiveData<String> confirmPassword = new MutableLiveData<>();
    private MutableLiveData<SignupUser> userMutableLiveData;
    @Inject
    API api;
    @Inject
    CompositeDisposable compositeDisposable;
    @Inject
    Retrofit retrofit;
    private SignupUser signupUser;

    @Inject
    public SignupViewModel(){}

    public MutableLiveData<SignupUser> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    // function to generate a random string of length n
    static String generateKeynode()
    {
        // chose a Character random from this String
        String AlphaNumericString = "0123456789";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(5);

        for (int i = 0; i < 5; i++) {

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


        signupUser = new SignupUser(firstname.getValue(), lastname.getValue(), email.getValue(), password.getValue(), confirmPassword.getValue());

        if(!NetworkUtils.isNetworkAvailable(view.getContext())){
            signupUser.setNetworkAvailable(false);
            userMutableLiveData.setValue(signupUser);
            return;
        }


        signupUser.setNetworkAvailable(true);

        if(!TextUtils.isEmpty(firstname.getValue()) && !TextUtils.isEmpty(lastname.getValue()) && !TextUtils.isEmpty(email.getValue()) && !TextUtils.isEmpty(password.getValue()) && !TextUtils.isEmpty(confirmPassword.getValue())  && signupUser.isPasswordMatch()){

            signupUser.setIsLoading(true);
            userMutableLiveData.setValue(signupUser);

            SignupUser mSignupUser = new SignupUser(firstname.getValue(), lastname.getValue(), email.getValue(), password.getValue(),Integer.parseInt(generateKeynode()));
            compositeDisposable.add(api.signUp(mSignupUser)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::HandleResults, this::handleError));
        }
        else {
            signupUser.setIsLoading(false);
            userMutableLiveData.setValue(signupUser);

        }
    }

    void HandleResults(ResponseStatus responseStatus){

        if(responseStatus.getStatus().equalsIgnoreCase("success")){
            signupUser.setIsLoading(false);
            signupUser.setSignupStatus(true);
            userMutableLiveData.setValue(signupUser);
        }
        else if(responseStatus.getStatus().equalsIgnoreCase("failure")){
            signupUser.setIsLoading(false);
            signupUser.setSignupStatus(false);
            userMutableLiveData.setValue(signupUser);
        }
    }

    private void handleError(Throwable t) {
        signupUser.setError(true);
        userMutableLiveData.setValue(signupUser);
    }

}
