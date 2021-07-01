package com.example.otlobna.Model.Response;

import com.google.gson.annotations.SerializedName;

public class RequestOrderResponse {


    @SerializedName("error")
    int error;
    @SerializedName("message")
    String message;

    public RequestOrderResponse() {
    }

    public int getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

}
