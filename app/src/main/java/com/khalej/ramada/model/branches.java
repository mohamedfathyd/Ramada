package com.khalej.ramada.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class branches {
    @SerializedName("payload")
    List<pranches> payload;
    @SerializedName("status")
    boolean status;
    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;


    public List<pranches> getPayload() {
        return payload;
    }

    public void setPayload(List<pranches> payload) {
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

    public class pranches{
        @SerializedName("en_name")
        String en_name;

        @SerializedName("ar_name")
        String ar_name;
        @SerializedName("address")
        String address;
        @SerializedName("latitude")
        String latitude;
        @SerializedName("longitude")
        String longitude;
        @SerializedName("phone")
        String phone;

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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

}
