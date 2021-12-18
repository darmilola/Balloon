package com.useballoon.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class IntroResponse {
    @SerializedName("status")
    private String status = "";

    @SerializedName("data")
    private List<User> data = new ArrayList<>();

    private transient boolean isLoading = false;

    private transient boolean isNetworkAvailable = true;

    private transient boolean isError = false;

    public IntroResponse(){

    }

    public IntroResponse(String status, List<User> data){
           this.status = status;
           this.data = data;
    }

    public List<User> getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean isError() {
        return isError;
    }


    public boolean isNetworkAvailable() {
        return isNetworkAvailable;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void setNetworkAvailable(boolean networkAvailable) {
        isNetworkAvailable = networkAvailable;
    }

    public void setError(boolean error) {
        isError = error;
    }

}
