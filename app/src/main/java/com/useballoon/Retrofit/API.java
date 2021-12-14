package com.useballoon.Retrofit;

import com.useballoon.Models.LoginUser;
import com.useballoon.Models.ResponseStatus;
import com.useballoon.Models.SignupUser;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {
    @POST("users")
     Observable<ResponseStatus> signUp(@Body SignupUser signupUser);

    @POST("users/auth")
    Observable<ResponseStatus> login(@Body LoginUser loginUser);
}
