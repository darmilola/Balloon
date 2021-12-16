package com.useballoon.Models;


import com.google.gson.annotations.SerializedName;

public class SignupUser {
    @SerializedName("firstname")
    private String firstname;

    @SerializedName("surname")
    private String lastname;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("keynode")
    private int keynode;

    private transient String confirmPassword;

    private transient boolean isLoading = false;

    private transient boolean signupStatus = false;

    private transient boolean isNetworkAvailable = false;

    public SignupUser(String firstname, String lastname, String email, String password, int keynode) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.keynode = keynode;
    }

    public SignupUser(String firstname, String lastname, String email, String password, String confirmPassword){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }


    public boolean getIsLoading(){
        return isLoading;
    }

    public boolean getSignupStatus() {
        return signupStatus;
    }

    public void setSignupStatus(boolean signupStatus) {
        this.signupStatus = signupStatus;
    }

    public void setIsLoading(boolean isLoading){
        this.isLoading = isLoading;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getKeynode() {
        return keynode;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }



    public boolean isPasswordMatch(){
           if(password.equalsIgnoreCase(confirmPassword)) return true;
           return false;
    }

    public void setNetworkAvailable(boolean networkAvailable) {
        isNetworkAvailable = networkAvailable;
    }

    public boolean isNetworkAvailable() {
        return isNetworkAvailable;
    }
}
