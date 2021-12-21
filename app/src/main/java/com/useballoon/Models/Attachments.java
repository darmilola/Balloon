package com.useballoon.Models;

public class Attachments {
    private int type;
    private String attachmentUrl;

    public Attachments(int type, String attachmentUrl){
           this.type = type;
           this.attachmentUrl = attachmentUrl;
    }

    public int getType() {
        return type;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

}
