package com.khalej.ramada.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class contact_address {
    @SerializedName("payload")
    List<Address> payload;
    @SerializedName("status")
    boolean status;
    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;


    public List<Address> getPayload() {
        return payload;
    }

    public void setPayload(List<Address> payload) {
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

    public class Address {
        @SerializedName("id")
        String id;
        @SerializedName("address")
        String address;
        @SerializedName("type")
        String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
