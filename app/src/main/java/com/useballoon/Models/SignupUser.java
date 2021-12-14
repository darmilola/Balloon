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

    private transient String confirmPassword;

    @SerializedName("phonenumber")
    private String phonenumber;

    private transient boolean isLoading = false;

    private transient boolean signupStatus = false;

    public SignupUser(String firstname, String lastname, String email, String password, String phonenumber){
          this.firstname = firstname;
          this.lastname = lastname;
          this.email = email;
          this.password = password;
          this.phonenumber = phonenumber;
    }

    public SignupUser(String firstname, String lastname, String email, String password, String confirmPassword, String phonenumber){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phonenumber = phonenumber;
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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public boolean isPasswordMatch(){
           if(password.equalsIgnoreCase(confirmPassword)) return true;
           return false;
    }
}
