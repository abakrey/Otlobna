package com.example.otlobna.Model.Object;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order implements Serializable {
    @SerializedName("Id")
    private String Id;

    @SerializedName("id_tayar")
    private String id_tayar;

    @SerializedName("id_mkan")
    private String id_mkan;

    @SerializedName("Name_Place")
    private String Name_Place;

    @SerializedName("Address_Place")
    private String Address_Place;

    @SerializedName("Phone_Place")
    private String Phone_Place;

    @SerializedName("Latitude_Place")
    private String Latitude_Place;

    @SerializedName("longitude_Place")
    private String longitude_Place;

    @SerializedName("Price_Place")
    private String Price_Place;

    @SerializedName("Name_Customer")
    private String Name_Customer;

    @SerializedName("Address_Cutsomer")
    private String Address_Cutsomer;

    @SerializedName("Phone_Customer")
    private String Phone_Customer;

    @SerializedName("longitude_Customer")
    private String longitude_Customer;

    @SerializedName("Latitude_Customer")
    private String Latitude_Customer;

    @SerializedName("Name_Tayar")
    private String Name_Tayar;

    @SerializedName("Phone_Tayar")
    private String Phone_Tayar;

    @SerializedName("Price_Tayar")
    private String Price_Tayar;

    @SerializedName("Start_Time")
    private String Start_Time;

    @SerializedName("Take_Time")
    private String Take_Time;

    @SerializedName("End_Time")
    private String End_Time;

    @SerializedName("Details")
    private String Details;

    @SerializedName("CurrentLon")
    private String CurrentLon;

    @SerializedName("CurrentLat")
    private String CurrentLat;

    @SerializedName("Type")
    private String Type;


    public Order() {
    }

    public Order(String id, String id_tayar, String id_mkan, String name_Place, String address_Place, String phone_Place, String latitude_Place, String longitude_Place, String price_Place, String name_Customer, String address_Cutsomer, String phone_Customer, String longitude_Customer, String latitude_Customer, String name_Tayar, String phone_Tayar, String price_Tayar, String start_Time, String take_Time, String end_Time, String details, String currentLon, String currentLat, String type) {
        Id = id;
        this.id_tayar = id_tayar;
        this.id_mkan = id_mkan;
        Name_Place = name_Place;
        Address_Place = address_Place;
        Phone_Place = phone_Place;
        Latitude_Place = latitude_Place;
        this.longitude_Place = longitude_Place;
        Price_Place = price_Place;
        Name_Customer = name_Customer;
        Address_Cutsomer = address_Cutsomer;
        Phone_Customer = phone_Customer;
        this.longitude_Customer = longitude_Customer;
        Latitude_Customer = latitude_Customer;
        Name_Tayar = name_Tayar;
        Phone_Tayar = phone_Tayar;
        Price_Tayar = price_Tayar;
        Start_Time = start_Time;
        Take_Time = take_Time;
        End_Time = end_Time;
        Details = details;
        CurrentLon = currentLon;
        CurrentLat = currentLat;
        Type = type;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getId_tayar() {
        return id_tayar;
    }

    public void setId_tayar(String id_tayar) {
        this.id_tayar = id_tayar;
    }

    public String getId_mkan() {
        return id_mkan;
    }

    public void setId_mkan(String id_mkan) {
        this.id_mkan = id_mkan;
    }

    public String getName_Place() {
        return Name_Place;
    }

    public void setName_Place(String name_Place) {
        Name_Place = name_Place;
    }

    public String getAddress_Place() {
        return Address_Place;
    }

    public void setAddress_Place(String address_Place) {
        Address_Place = address_Place;
    }

    public String getPhone_Place() {
        return Phone_Place;
    }

    public void setPhone_Place(String phone_Place) {
        Phone_Place = phone_Place;
    }

    public String getLatitude_Place() {
        return Latitude_Place;
    }

    public void setLatitude_Place(String latitude_Place) {
        Latitude_Place = latitude_Place;
    }

    public String getLongitude_Place() {
        return longitude_Place;
    }

    public void setLongitude_Place(String longitude_Place) {
        this.longitude_Place = longitude_Place;
    }

    public String getPrice_Place() {
        return Price_Place;
    }

    public void setPrice_Place(String price_Place) {
        Price_Place = price_Place;
    }

    public String getName_Customer() {
        return Name_Customer;
    }

    public void setName_Customer(String name_Customer) {
        Name_Customer = name_Customer;
    }

    public String getAddress_Cutsomer() {
        return Address_Cutsomer;
    }

    public void setAddress_Cutsomer(String address_Cutsomer) {
        Address_Cutsomer = address_Cutsomer;
    }

    public String getPhone_Customer() {
        return Phone_Customer;
    }

    public void setPhone_Customer(String phone_Customer) {
        Phone_Customer = phone_Customer;
    }

    public String getLongitude_Customer() {
        return longitude_Customer;
    }

    public void setLongitude_Customer(String longitude_Customer) {
        this.longitude_Customer = longitude_Customer;
    }

    public String getLatitude_Customer() {
        return Latitude_Customer;
    }

    public void setLatitude_Customer(String latitude_Customer) {
        Latitude_Customer = latitude_Customer;
    }

    public String getName_Tayar() {
        return Name_Tayar;
    }

    public void setName_Tayar(String name_Tayar) {
        Name_Tayar = name_Tayar;
    }

    public String getPhone_Tayar() {
        return Phone_Tayar;
    }

    public void setPhone_Tayar(String phone_Tayar) {
        Phone_Tayar = phone_Tayar;
    }

    public String getPrice_Tayar() {
        return Price_Tayar;
    }

    public void setPrice_Tayar(String price_Tayar) {
        Price_Tayar = price_Tayar;
    }

    public String getStart_Time() {
        return Start_Time;
    }

    public void setStart_Time(String start_Time) {
        Start_Time = start_Time;
    }

    public String getTake_Time() {
        return Take_Time;
    }

    public void setTake_Time(String take_Time) {
        Take_Time = take_Time;
    }

    public String getEnd_Time() {
        return End_Time;
    }

    public void setEnd_Time(String end_Time) {
        End_Time = end_Time;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getCurrentLon() {
        return CurrentLon;
    }

    public void setCurrentLon(String currentLon) {
        CurrentLon = currentLon;
    }

    public String getCurrentLat() {
        return CurrentLat;
    }

    public void setCurrentLat(String currentLat) {
        CurrentLat = currentLat;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
