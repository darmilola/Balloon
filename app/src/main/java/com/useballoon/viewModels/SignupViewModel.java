package com.useballoon.viewModels;

import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.useballoon.Models.ResponseStatus;
import com.useballoon.Models.SignupUser;
import com.useballoon.Retrofit.API;
import com.useballoon.Retrofit.RetrofitClient;
import com.useballoon.Signup;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SignupViewModel extends ViewModel {
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> firstname = new MutableLiveData<>();
    public MutableLiveData<String> lastname = new MutableLiveData<>();
    public MutableLiveData<String> confirmPassword = new MutableLiveData<>();
    public MutableLiveData<String> phonenumber = new MutableLiveData<>();
    private MutableLiveData<SignupUser> userMutableLiveData;
    private API api;
    private Retrofit retrofit;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MutableLiveData<SignupUser> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }


    public void onClick(View view) {


        SignupUser signupUser = new SignupUser(firstname.getValue(), lastname.getValue(), email.getValue(), password.getValue(), confirmPassword.getValue(), phonenumber.getValue());


        if(!TextUtils.isEmpty(firstname.getValue()) && !TextUtils.isEmpty(lastname.getValue()) && !TextUtils.isEmpty(email.getValue()) && !TextUtils.isEmpty(password.getValue()) && !TextUtils.isEmpty(confirmPassword.getValue()) && !TextUtils.isEmpty(phonenumber.getValue()) && signupUser.isPasswordMatch()){

            signupUser.setIsLoading(true);
            userMutableLiveData.setValue(signupUser);

            SignupUser mSignupUser = new SignupUser(firstname.getValue(), lastname.getValue(), email.getValue(), password.getValue(), phonenumber.getValue());
            Retrofit retrofit = RetrofitClient.getInstance();
            api = retrofit.create(API.class);
            compositeDisposable.add(api.signUp(mSignupUser)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ResponseStatus>() {
                        @Override
                        public void accept(ResponseStatus responseStatus) throws Exception {
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

                    }));
        }
        else {
            signupUser.setIsLoading(false);
            userMutableLiveData.setValue(signupUser);

        }
    }

}
