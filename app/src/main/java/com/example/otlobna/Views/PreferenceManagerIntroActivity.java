package com.example.otlobna.Views;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.otlobna.R;


public class PreferenceManagerIntroActivity {

    private Context context;
    private SharedPreferences sharedPreferences;

    public PreferenceManagerIntroActivity(Context context) {
        this.context = context;
        getSharedPreferences();
    }

    public void getSharedPreferences() {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.my_preference), Context.MODE_PRIVATE);
    }

    public void WritePreference() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.my_preference_key), "INIT_OK");
        editor.commit();

    }

    public boolean CheckPreference() {
        boolean status = false;
        if (sharedPreferences.getString(context.getString(R.string.my_preference_key), "null").equals("null")) {
            status = false;
        } else {
            status = true;
        }
        return status;
    }

    public void ClearReference() {
        sharedPreferences.edit().clear().commit();
    }

}
