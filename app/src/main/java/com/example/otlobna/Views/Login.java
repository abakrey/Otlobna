package com.example.otlobna.Views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otlobna.Model.PreferanceData.Preferance;
import com.example.otlobna.Model.Response.GeneralResponse;
import com.example.otlobna.Model.Response.LoginResponse;
import com.example.otlobna.Model.Object.Tayar;
import com.example.otlobna.R;
import com.example.otlobna.network.ApiClient;
import com.example.otlobna.network.ApiInterface;
import com.google.gson.Gson;
import java.io.IOException;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    @BindView(R.id.email)
    EditText _emailText;
    @BindView(R.id.password)
    EditText _passwordText;
    @BindView(R.id.email_sign_in_button)
    Button _loginButton;
    @BindView(R.id.GoToSignUp)
    TextView GoToSignUp ;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        if (!Preferance.getID(Login.this).equals(""))
        {
            Intent intent = new Intent(Login.this  , Orders.class);
            finish();
            startActivity(intent);
        }
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        GoToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this , SingUp.class);
                startActivity(intent);
            }
        });


    }


    public void onLoginSuccess(Tayar tayar) {

        try {
            Log.e(">>>>>" , tayar.toString());
            Intent startPageIntent = new Intent(Login.this, Orders.class);
            startPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
            startActivity(startPageIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onLoginFailed() {
        Snackbar.make(_loginButton, R.string.rongInput, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString().trim();
        String password = _passwordText.getText().toString().trim();

        if (email.isEmpty()) {
            _emailText.setError(getString(R.string.validNumber));
            valid = false;
        } else {
            _emailText.setError(null);
        }
       if (password.isEmpty() || password.length() < 4 || password.length() > 20) {
            _passwordText.setError(getString(R.string.shouldChar));
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        return valid;
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        finish();
    }


    public void login() {
     /*   if (!validate()) {
            onLoginFailed();
            return;
        }*/
        final String email = _emailText.getText().toString().trim();
        final String password = _passwordText.getText().toString().trim();
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            progressDialog = new ProgressDialog(Login.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Auth...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<ResponseBody> call = apiService.getLoginTayar(email, password);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String Result = response.body().string();
                        Gson gson = new Gson();
                        GeneralResponse generalResponse = gson.fromJson(Result , GeneralResponse.class);
                        if (generalResponse.getError() == 0)
                        {
                            LoginResponse loginResponse = gson.fromJson(Result , LoginResponse.class);
                            Preferance.saveData(loginResponse.getData().get(0) , Login.this);
                            Toast.makeText(Login.this , "Mkan" , Toast.LENGTH_LONG).show();
                            onLoginSuccess(new Tayar());
                            progressDialog.dismiss();


                        }
                        else if (generalResponse.getError() == 3)
                        {
                            LoginResponse loginResponse = gson.fromJson(Result , LoginResponse.class);
                            Preferance.saveData(loginResponse.getData().get(0) , Login.this);
                            Toast.makeText(Login.this , "Tayar" , Toast.LENGTH_LONG).show();
                            onLoginSuccess(new Tayar());
                            progressDialog.dismiss();


                        }
                        else
                        {
                            Toast.makeText(Login.this , "الرقم السري غير صحيح" , Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();


                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(Login.this , t.getMessage() , Toast.LENGTH_LONG).show();

                }
            });


        } else {
            Snackbar.make(_loginButton, R.string.noInternet, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}

