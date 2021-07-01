package com.example.otlobna.Model.Object;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.otlobna.Server.MySingleTon;
import com.example.otlobna.Server.ServerDetails;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Tayar {

    @SerializedName("Id")
    String id;
    @SerializedName("Name")
    String name;
    @SerializedName("Email")
    String email;
    @SerializedName("Password")
    String password;
    @SerializedName("Department")
    String department;
    @SerializedName("Latitude")
    String latitude;
    @SerializedName("Longitude")
    String longitude;
    @SerializedName("Image")
    String image;
    @SerializedName("Address")
    String address;
    @SerializedName("PhoneOne")
    String phoneOne;
    @SerializedName("PhoneTwo")
    String phoneTwo;
    @SerializedName("TypeUser")
    String typeUser;
    @SerializedName("Available")
    String available;

    public Tayar() {
    }

    public Tayar(String s1, String preferencesString1, String sharedPreferencesString1, String string1, String s, String preferencesString, String sharedPreferencesString, String string, String id, String name, String email, String password, String department, String latitude) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.department = department;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.address = address;
        this.phoneOne = phoneOne;
        this.phoneTwo = phoneTwo;
        this.typeUser = typeUser;
        this.available = available;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDepartment() {
        return department;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getImage() {
        return image;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneOne() {
        return phoneOne;
    }

    public String getPhoneTwo() {
        return phoneTwo;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public String getAvailable() {
        return available;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneOne(String phoneOne) {
        this.phoneOne = phoneOne;
    }

    public void setPhoneTwo(String phoneTwo) {
        this.phoneTwo = phoneTwo;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    public void setAvailable(String available) {
        this.available = available;
    }
}
