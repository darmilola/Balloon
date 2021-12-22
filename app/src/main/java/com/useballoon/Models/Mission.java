package com.useballoon.Models;

import com.google.gson.annotations.SerializedName;

public class Mission {
    @SerializedName("subject")
    private String subject;

    @SerializedName("musicfile")
    private String musicOrVideoUrl;

    @SerializedName("step1")
    private String step1;

    @SerializedName("step2")
    private String step2;

    @SerializedName("step3")
    private String step3;

    @SerializedName("step4")
    private String step4;

    @SerializedName("step5")
    private String step5;

    @SerializedName("evangelistno")
    private String envangelistCount;

    @SerializedName("evangelistprice")
    private String compensationFee;

    @SerializedName("proof")
    private String proofOfExecution;

    @SerializedName("codedid")
    private String missionId;

    @SerializedName("creatorid")
    private int creatorId;

    private transient boolean isLoading = false;

    private transient boolean isNetworkAvailable = true;

    private transient boolean isError = false;

    private transient boolean isSaved = false;


    public Mission(String missionId, int creatorId){
           this.missionId = missionId;
           this.creatorId = creatorId;
    }

    public Mission(String subject, String musicOrVideoUrl, String missionId, int mCreatorid){
           this.missionId = missionId;
           this.creatorId = mCreatorid;
           this.subject = subject;
           this.musicOrVideoUrl = musicOrVideoUrl;

    }

    public Mission(String step1, String step2, String step3, String step4, String step5, String missionId, int creatorId){
                  this.step1 = step1;
                  this.step2 = step2;
                  this.step3 = step3;
                  this.step4 = step4;
                  this.step5 = step5;
                  this.missionId = missionId;
                  this.creatorId = creatorId;;
    }

    public Mission(String envangelistCount, String compensationFee, String proofOfExecution, String missionId, int creatorId){
                  this.envangelistCount = envangelistCount;
                  this.compensationFee = compensationFee;
                  this.proofOfExecution = proofOfExecution;
                  this.missionId = missionId;
                  this.creatorId = creatorId;
    }


    public void setError(boolean error) {
        isError = error;
    }

    public void setNetworkAvailable(boolean networkAvailable) {
        isNetworkAvailable = networkAvailable;
    }

    public void setLoading(boolean isLoading){
        this.isLoading = isLoading;
    }

    public boolean isNetworkAvailable() {
        return isNetworkAvailable;
    }

    public boolean isError() {
        return isError;
    }

    public boolean isSaved() {
        return isSaved;
    }
    public void setIsSaved(boolean isSaved){
         this.isSaved = isSaved;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public String getCompensationFee() {
        return compensationFee;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public String getEnvangelistCount() {
        return envangelistCount;
    }

    public String getMissionId() {
        return missionId;
    }

    public String getMusicOrVideoUrl() {
        return musicOrVideoUrl;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public String getProofOfExecution() {
        return proofOfExecution;
    }

    public String getStep1() {
        return step1;
    }

    public String getStep2() {
        return step2;
    }

    public String getStep3() {
        return step3;
    }

    public String getStep4() {
        return step4;
    }

    public String getStep5() {
        return step5;
    }
    public String getSubject() {
        return subject;
    }

}
