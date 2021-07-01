package com.example.otlobna.Model.Response;

import com.example.otlobna.Model.Object.Tayar;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoginResponse {
    @SerializedName("error")
    int error;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    ArrayList<Tayar>data ;


    public LoginResponse() {
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

    public ArrayList<Tayar> getData() {
        return data;
    }

    public void setData(ArrayList<Tayar> data) {
        this.data = data;
    }
}
