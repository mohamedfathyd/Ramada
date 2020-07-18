package com.khalej.ramada.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class contact_charges {
    @SerializedName("payload")
    shipments_requests payload;
    @SerializedName("status")
    boolean status;
    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;


    public shipments_requests getPayload() {
        return payload;
    }

    public void setPayload(shipments_requests payload) {
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

    public class shipments_requests {
        @SerializedName("shipments_requests")
        List<requests> requests;

        public List<shipments_requests.requests> getRequests() {
            return requests;
        }

        public void setRequests(List<shipments_requests.requests> requests) {
            this.requests = requests;
        }

        public class requests {
            @SerializedName("id")
            String id;
            @SerializedName("day")
            String day;
            @SerializedName("quantity")
            int quantity;
            @SerializedName("description")
            String description;
            @SerializedName("track_code")
            String track_code;
            @SerializedName("price")
            double price;
            @SerializedName("currency")
            String currency;

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getTrack_code() {
                return track_code;
            }

            public void setTrack_code(String track_code) {
                this.track_code = track_code;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
    }

}
