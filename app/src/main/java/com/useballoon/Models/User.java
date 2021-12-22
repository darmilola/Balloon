package com.useballoon.Models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("firstname")
    private String firstname;

    @SerializedName("userid")
    private int userId;

    @SerializedName("email")
    private String email;

    @SerializedName("donereg")
    private int donereg;

    public User(String firstname, int userId, String email, int donereg){
           this.firstname = firstname;
           this.userId = userId;
           this.email = email;
           this.donereg = donereg;
    }

    public User(String email, int userId)
    {
        this.email = email;
        this.userId = userId;
    }

    public User(String email)
    {
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

    public int getUserId() {
        return userId;
    }

}
