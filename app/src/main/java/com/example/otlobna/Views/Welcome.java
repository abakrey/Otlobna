package com.example.otlobna.Views;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.otlobna.R;

public class Welcome extends AppCompatActivity {
    private final Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    public void LoginFunc(View view){
        Intent intent_login= new Intent(this, Login.class);
        startActivity(intent_login);
    }
    public void SignFunc(View view){
        Intent intent_singIn=new Intent(this, SingUp.class);
        startActivity(intent_singIn);
    }
}
