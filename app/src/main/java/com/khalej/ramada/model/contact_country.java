package com.khalej.ramada.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class contact_country {
    @SerializedName("payload")
    List<country> payload;
    @SerializedName("status")
    boolean status;
    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;


    public List<country> getPayload() {
        return payload;
    }

    public void setPayload(List<country> payload) {
        this.payload = payload;
    }

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

    public class country{
        @SerializedName("id")
        String id;
        @SerializedName("en_name")
        String en_name;
        @SerializedName("ar_name")
        String ar_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEn_name() {
            return en_name;
        }

        public void setEn_name(String en_name) {
            this.en_name = en_name;
        }

        public String getAr_name() {
            return ar_name;
        }

        public void setAr_name(String ar_name) {
            this.ar_name = ar_name;
        }
    }

}
