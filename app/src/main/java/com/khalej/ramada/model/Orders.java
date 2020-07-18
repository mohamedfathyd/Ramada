package com.khalej.ramada.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Orders {
    @SerializedName("payload")
    List<Order_data> payload;
    @SerializedName("status")
    boolean status;
    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;


    public List<Order_data> getPayload() {
        return payload;
    }

    public void setPayload(List<Order_data> payload) {
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

    public class Order_data{
        @SerializedName("id")
        String id;
        @SerializedName("day")
        String day;
        @SerializedName("time")
        String time;
        @SerializedName("size")
        String size ;
        @SerializedName("quantity")
        int quantity;
        @SerializedName("weight")
        double weight;
        @SerializedName("description")
        String description;
        @SerializedName("currency")
        String currency;
        @SerializedName("price")
        String price;
        @SerializedName("address")
        String address;
        @SerializedName("receive_address")
        String receive_address;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getReceive_address() {
            return receive_address;
        }

        public void setReceive_address(String receive_address) {
            this.receive_address = receive_address;
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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

}
