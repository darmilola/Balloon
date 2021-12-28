package com.useballoon.Models;

import com.google.gson.annotations.SerializedName;

public class AttachmentResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private Attachments attachments;

    public AttachmentResponse(String status, Attachments attachments){
           this.status = status;
           this.attachments = attachments;
    }

    public Attachments getAttachments() {
        return attachments;
    }

    public String getStatus() {
        return status;
    }
}
