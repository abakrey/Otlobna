package com.example.otlobna.Basics;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Validation {

    public static boolean isEmailValid(String email) {
        boolean result = false ;
        if (email.contains("@yahoo.com") ||email.contains("@gmail.com") || email.contains("@rocketmail.com") || email.contains("@hotmail.com") )
            result = true ;
        else
            result = false ;
        return  result;
    }
    public static boolean isPasswordEqual(String PasswordOne , String Passwordtwo) {
        boolean result = false;
        if (PasswordOne.equals(Passwordtwo)) {
            result = true;
        }
        return result;
    }
    public static boolean isPasswordLenght(String PasswordOne)
    {
        boolean result = false ;
        if (PasswordOne.length() >= 4 )
        {
            result = true ;
        }

        return  result;
    }
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

}
