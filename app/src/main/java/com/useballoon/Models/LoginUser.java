package com.useballoon.Models;

import com.google.gson.annotations.SerializedName;

public class LoginUser {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    private transient boolean isLoading = false;

    private transient boolean loginStatus = false;

    private transient boolean isNetworkAvailable = false;

    private transient boolean isError = false;

    public LoginUser(String email, String password){
           this.email = email;
           this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public void setIsLoading(boolean isLoading){
        this.isLoading = isLoading;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean isLoginStatus() {
        return loginStatus;
    }

    public void setNetworkAvailable(boolean networkAvailable) {
        isNetworkAvailable = networkAvailable;
    }

    public boolean isNetworkAvailable() {
        return isNetworkAvailable;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }
}
