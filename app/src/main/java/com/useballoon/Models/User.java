package com.useballoon.Models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("firstname")
    private String firstname;

    @SerializedName("userid")
    private String userId;

    @SerializedName("email")
    private String email;

    @SerializedName("donereg")
    private int donereg;

    public User(String firstname, String userId, String email, int donereg){
           this.firstname = firstname;
           this.userId = userId;
           this.email = email;
           this.donereg = donereg;
    }

    public User(String email){
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public int getDonereg() {
        return donereg;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

}
