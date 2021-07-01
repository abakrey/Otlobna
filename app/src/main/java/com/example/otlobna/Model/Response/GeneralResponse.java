package com.example.otlobna.Model.Response;

import com.google.gson.annotations.SerializedName;

public class GeneralResponse {

    @SerializedName("error")
    int error;
    @SerializedName("message")
    String message;


    public GeneralResponse() {
    }

    public GeneralResponse(int error, String message) {
        this.error = error;
        this.message = message;
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
}
