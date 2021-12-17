package com.useballoon.Models;

import com.google.gson.annotations.SerializedName;

public class ActivateUser {

    @SerializedName("userId")
    private int userId;

    @SerializedName("validate")
    private int validate;

    @SerializedName("validatedate")
    private String validatedate;

    private transient boolean isLoading = false;

    private transient boolean activationStatus = false;

    private transient boolean isNetworkAvailable = false;

    public ActivateUser(int userId, int validate, String validatedate){
           this.userId = userId;
           this.validate = validate;
           this.validatedate = validatedate;
    }

    public int getUserId() {
        return userId;
    }

    public int getValidate() {
        return validate;
    }

    public String getValidatedate() {
        return validatedate;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean isNetworkAvailable() {
        return isNetworkAvailable;
    }

    public boolean isActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(boolean activationStatus) {
        this.activationStatus = activationStatus;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void setNetworkAvailable(boolean networkAvailable) {
        isNetworkAvailable = networkAvailable;
    }


}
