package com.useballoon.Models;

import com.google.gson.annotations.SerializedName;

public class Attachments {
    @SerializedName("type")
    private String type;
    @SerializedName("attachfile")
    private String attachmentUrl;
    @SerializedName("id")
    private int attachmentId;
    @SerializedName("mission")
    private String missionId;
    @SerializedName("missioncreator")
    private int missionCreator;

    public Attachments(String type, String attachmentUrl, int attachmentId, String missionId, int missionCreator){
           this.type = type;
           this.attachmentUrl = attachmentUrl;
           this.attachmentId  = attachmentId;
           this.missionId = missionId;
           this.missionCreator = missionCreator;
    }

    public String getType() {
        return type;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public int getAttachmentId() {
        return attachmentId;
    }

    public String getMissionId() {
        return missionId;
    }

    public int getMissionCreator() {
        return missionCreator;
    }
}
