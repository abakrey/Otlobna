package com.example.otlobna.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> getLoginTayar(@Field("Email") String email, @Field("Password") String pass);

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> userSignUp(@Field("Name") String name,
                                  @Field("Email") String email,
                                  @Field("Password") String password,
                                  @Field("Department") String department,
                                  @Field("Latitude") float latitude,
                                  @Field("Longitude") float longitude,
                                  @Field("Image") String image,
                                  @Field("Address") String address,
                                  @Field("PhoneOne") String phoneOne,
                                  @Field("PhoneTwo") String phoneTwo,
                                  @Field("TypeUser") String typeUser,
                                  @Field("Available") String available);

    @FormUrlEncoded
    @POST("showMtlopOrder_tayar")
    Call<ResponseBody> NewOrderDelivery(@Field("id_tayar") int id_tayar);


    @FormUrlEncoded
    @POST("showMtlopOrder_Mkan")
    Call<ResponseBody> NewOrderPlace(@Field("id_mkan") int id_mkan);

    @FormUrlEncoded
    @POST("showWaitingOrder_tayar")
    Call<ResponseBody> ProcessOrderDelivery(@Field("id_tayar") int id_tayar);


    @FormUrlEncoded
    @POST("showWaitingOrder_Mkan")
    Call<ResponseBody> ProcessOrderPlace(@Field("id_mkan") int id_mkan);

    @FormUrlEncoded
    @POST("showEndOrder_tayar")
    Call<ResponseBody> EndOrderDelivery(@Field("id_tayar") int id_tayar);


    @FormUrlEncoded
    @POST("showEndOrder_Mkan")
    Call<ResponseBody> EndOrderPlace(@Field("id_mkan") int id_mkan);

    @FormUrlEncoded
    @POST("updateType")
    Call<ResponseBody> UpdateType(@Field("Id") int Id, @Field("Type") String Type);


    @FormUrlEncoded
    @POST("updateTrackAmaken")
    Call<ResponseBody> UpdateLocation(@Field("Id") int Id, @Field("Latitude") float Latitude, @Field("Longitude") float Longitude);

    @FormUrlEncoded
    @POST("showProfile")
    Call<ResponseBody> showProfile(@Field("id") int id);

    @GET("showAvaliableTayar")
    Call<ResponseBody> availableTayar();

    @FormUrlEncoded
    @POST("mkanRequestOrder")
    Call<ResponseBody> requestOrder(@Field("Name_Place") String Name_Place,
                                    @Field("Address_Place") String Address_Place,
                                    @Field("Phone_Place") String Phone_Place,
                                    @Field("longitude_Place") float longitude_Place,
                                    @Field("Latitude_Place") float Latitude_Place,
                                    @Field("Price_Place") String Price_Place,
                                    @Field("Name_Customer") String Name_Customer,
                                    @Field("Address_Cutsomer") String Address_Cutsomer,
                                    @Field("Phone_Customer") String Phone_Customer,
                                    @Field("longitude_Customer") float longitude_Customer,
                                    @Field("Latitude_Customer") float Latitude_Customer,
                                    @Field("id_tayar") int id_tayar,
                                    @Field("Name_Tayar") String Name_Tayar,
                                    @Field("Phone_Tayar") String Phone_Tayar,
                                    @Field("Price_Tayar") String Price_Tayar,
                                    @Field("Details") String Details,
                                    @Field("CurrentLon") float CurrentLon,
                                    @Field("CurrentLat") float CurrentLat,
                                    @Field("id_mkan") int id_mkan);
    //deleteOrderById
    //    @DELETE("deleteOrderById/{id}/{id_tayar}")

    @DELETE("deleteOrderById")
    Call<ResponseBody> deleteOrderById(@Query("id") int id, @Query("id_tayar") int id_tayar);

    @FormUrlEncoded
    @POST("updateOrderById")
    Call<ResponseBody> updateOrderById(@Field("id") int id,
                                       @Field("Name_Place") String Name_Place,
                                       @Field("Address_Place") String Address_Place,
                                       @Field("Phone_Place") String Phone_Place,
                                       @Field("longitude_Place") float longitude_Place,
                                       @Field("Latitude_Place") float Latitude_Place,
                                       @Field("Price_Place") float Price_Place,
                                       @Field("Name_Customer") String Name_Customer,
                                       @Field("Address_Cutsomer") String Address_Cutsomer,
                                       @Field("Phone_Customer") String Phone_Customer,
                                       @Field("longitude_Customer") float longitude_Customer,
                                       @Field("Latitude_Customer") float Latitude_Customer,
                                       @Field("id_tayar") int id_tayar,
                                       @Field("Name_Tayar") String Name_Tayar,
                                       @Field("Phone_Tayar") String Phone_Tayar,
                                       @Field("Price_Tayar") float Price_Tayar,
                                       @Field("Details") String Details,
                                       @Field("CurrentLon") float CurrentLon,
                                       @Field("CurrentLat") float CurrentLat,
                                       @Field("id_mkan") int id_mkan);

}
