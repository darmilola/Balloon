package com.useballoon.Retrofit;

import com.useballoon.Models.ActivateUser;
import com.useballoon.Models.IntroResponse;
import com.useballoon.Models.LoginUser;
import com.useballoon.Models.Mission;
import com.useballoon.Models.ResponseStatus;
import com.useballoon.Models.SignupUser;
import com.useballoon.Models.User;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {
    @POST("users")
     Observable<ResponseStatus> signUp(@Body SignupUser signupUser);

    @POST("users/auth")
    Observable<ResponseStatus> login(@Body LoginUser loginUser);

    @POST("users/activate")
    Observable<ResponseStatus> activate(@Body ActivateUser activateUser);

    @POST("users/search")
    Observable<IntroResponse> search(@Body User user);

    @POST("artists/mission/create")
    Observable<ResponseStatus> createMission(@Body Mission mission);

    @POST("artists/mission/update")
    Observable<ResponseStatus> updateMission(@Body Mission mission);
}
