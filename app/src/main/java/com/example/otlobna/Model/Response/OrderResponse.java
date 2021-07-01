package com.example.otlobna.Model.Response;

import com.example.otlobna.Model.Object.Order;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderResponse {
    @SerializedName("error")
    int error;
    @SerializedName("message")
    String message;
    @SerializedName("Data")
    ArrayList<Order> Data ;

    public OrderResponse() {
    }

    public OrderResponse(int error, String message, ArrayList<Order> data) {
        this.error = error;
        this.message = message;
        Data = data;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Order> getData() {
        return Data;
    }

    public void setData(ArrayList<Order> data) {
        Data = data;
    }
}
