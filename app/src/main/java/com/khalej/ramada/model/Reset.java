package com.khalej.ramada.model;

import com.google.gson.annotations.SerializedName;

public class Reset {
    @SerializedName("status")
    boolean status;

    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
