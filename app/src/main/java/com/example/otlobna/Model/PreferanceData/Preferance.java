package com.example.otlobna.Model.PreferanceData;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.otlobna.Model.Object.Session;
import com.example.otlobna.Model.Object.Tayar;
import com.example.otlobna.Views.Login;

public class Preferance {

    public Preferance() {
    }

    public static boolean saveData(Tayar tayar, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Session.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Session.ID, tayar.getId());
        editor.putString(Session.NAME, tayar.getName());
        editor.putString(Session.EMAIL, tayar.getEmail());
        editor.putString(Session.PASSWORD, tayar.getPassword());
        editor.putString(Session.DEPARTMENT, tayar.getDepartment());
        editor.putString(Session.LONGITUDE, tayar.getLongitude());
        editor.putString(Session.LATITUDE, tayar.getLatitude());
        editor.putString(Session.IMAGE, tayar.getImage());
        editor.putString(Session.ADDRESS, tayar.getAddress());
        editor.putString(Session.PHONE1, tayar.getPhoneOne());
        editor.putString(Session.PHONE2, tayar.getPhoneTwo());
        editor.putString(Session.TYPE, tayar.getTypeUser());
        editor.putString(Session.AVAILABLE, tayar.getAvailable());
        editor.apply();
        return true;
    }

    public static String getID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Session.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Session.ID, "");
    }

    public static String getName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Session.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Session.NAME, "");
    }

    public static String getEmail(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Session.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Session.EMAIL, "");
    }

    public static String getPassword(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Session.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Session.PASSWORD, "");
    }

    public static String getDepartment(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Session.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Session.DEPARTMENT, "");
    }

    public static String getLatitiude(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Session.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Session.LATITUDE, "");
    }

    public static String getLONGITUDE(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Session.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Session.LONGITUDE, "");
    }

    public static String getImage(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Session.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Session.IMAGE, "");
    }

    public static String getAddress(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Session.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Session.ADDRESS, "");
    }

    public static String getPhoneOne(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Session.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Session.PHONE1, "");
    }

    public static String getPhoneTwo(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Session.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Session.PHONE2, "");
    }

    public static String getTypeUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Session.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Session.TYPE, "");
    }

    public static String getAvailable(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Session.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Session.AVAILABLE, "");
    }

    public static void logout(Context mCtx) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(Session.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx,Login.class));
    }
}
