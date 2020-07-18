package com.khalej.ramada.model;
import com.google.gson.annotations.SerializedName;


public class contact_general_user {
    @SerializedName("payload")
    contact_user payload;
    @SerializedName("status")
    boolean status;
    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;

    public contact_user getPayload() {
        return payload;
    }

    public void setPayload(contact_user payload) {
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
    public class contact_user {
        @SerializedName("id")
        String id;
        @SerializedName("token")
        String token;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

    }


}
