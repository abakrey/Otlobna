package com.example.otlobna.Views;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otlobna.Basics.Validation;
import com.example.otlobna.Model.Response.GeneralResponse;
import com.example.otlobna.R;
import com.example.otlobna.network.ApiClient;
import com.example.otlobna.network.ApiInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingUp extends Activity {
    private EditText Name , Email , Password , ConfirmPassword , Address , Phone ;
    private Spinner  Department ;
    private Button SignUp ;
    private TextView SignIn ;
    private FusedLocationProviderClient client;
    private DataLocation Location ;
    private String type ,type1 ,available ;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing__up);

        Init();
    }

    private void Init()
    {
        Name = findViewById(R.id.edName);
        Email= findViewById(R.id.edEmail);
        Phone = findViewById(R.id.edPhone);
        Password = findViewById(R.id.edPass);
        ConfirmPassword = findViewById(R.id.edPassConfirm);
        Address= findViewById(R.id.address);
        Department = findViewById(R.id.department);
        SignUp = findViewById(R.id.sign_up);
        SignIn = findViewById(R.id.GoToSignIn);
        pDialog = new ProgressDialog(SingUp.this) ;
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingUp.this , Login.class);
                startActivity(intent);
            }
        });

        List<String> spinnerDep =  new ArrayList<>();
        spinnerDep.add("مطاعم");
        spinnerDep.add("صيدليات");
        spinnerDep.add("طيار");
        spinnerDep.add("شحن");
        spinnerDep.add("اخري");
        ArrayAdapter<String> adapterDep = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerDep);
        adapterDep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Department.setAdapter(adapterDep);

        RequestPermissions();
        Location = new DataLocation() ;
        client = LocationServices.getFusedLocationProviderClient(SingUp.this);
        GetCurrentLocation();
        SetCurrentLocation();
        Department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getSelectedItem().toString()=="مطاعم"){
                    type ="مطعم";
                    type1 ="مكان";
                }else if(parent.getSelectedItem().toString()=="صيدليات"){
                    type ="صيدلية";
                    type1 ="مكان";

                }else if(parent.getSelectedItem().toString()=="شحن"){
                    type ="شحن";
                    type1 ="مكان";

                }else if(parent.getSelectedItem().toString()=="طيار"){
                    type ="طيار";
                    type1 ="طيار";
                    available = "on";
                }else{
                    type ="اخري";
                    type1 ="مكان";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpFunction();
            }
        });
    }


    private void SignUpFunction() {
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Creating Account...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);

        String name = Name.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String confirmPassword = ConfirmPassword.getText().toString().trim();
        String addresss = Address.getText().toString().trim();
        String phone = Phone.getText().toString().trim();
        float latitude = Location.getLatitude();
        float longitude = Location.getLongitude();

        boolean cancel = false;
        View focusView = null;

        Name.setError(null);
        Email.setError(null);
        Password.setError(null);
        Address.setError(null);
        ConfirmPassword.setError(null);
        Phone.setError(null);
        if (TextUtils.isEmpty(name)) {
            Name.setError("هذة الخانه متطلبه");
            focusView = Name;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            Email.setError("هذة الخانه متطلبه");
            focusView = Email;
            cancel = true;
        } else if (!Validation.isEmailValid(email)) {
            Email.setError("هذة البيانات غير صحيحه");
            focusView = Email;
            cancel = true;
        }
        if (TextUtils.isEmpty(password)) {
            Password.setError("هذة الخانه متطلبه");
            focusView = Password;
            cancel = true;
        } else if (!Validation.isPasswordLenght(password)) {
            Password.setError("هذه كلمه السر صغيرة");
            focusView = Password;
            cancel = true;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            ConfirmPassword.setError("هذة الخانه متطلبه");
            focusView = ConfirmPassword;
            cancel = true;
        } else if (!Validation.isPasswordLenght(confirmPassword)) {
            ConfirmPassword.setError("هذه كلمه السر صغيرة");
            focusView = ConfirmPassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(addresss)) {
            Address.setError("هذة الخانه متطلبه");
            focusView = Address;
            cancel = true;
        }
        if (!Validation.isPasswordEqual(password, confirmPassword)) {
            ConfirmPassword.setError("كلمه السر غير متطابقه");
            focusView = ConfirmPassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(phone)) {
            Phone.setError("هذة الخانه متطلبه");
            focusView = Phone;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            if (Validation.isOnline(SingUp.this)) {
                pDialog.setTitle("جارى انشاء حساب جديد ...");
                pDialog.setMessage("من فضلك انتظر حتى نقوم ب انشاء الحساب الخاص بك .");
                pDialog.show();
                pDialog.setCanceledOnTouchOutside(false);
                pDialog.setCancelable(false);
                SignUpFunctionProcess(name, email, password,type, latitude,longitude,addresss,phone,type1,available);
            } else {
                Toast.makeText(SingUp.this, "من فضلك اتصل بالانترنت", Toast.LENGTH_LONG).show();
            }
        }


    }
    private void EndSignUpFunction(String message)
    {
        Name.setText("");
        Email.setText("");
        Password.setText("");
        ConfirmPassword.setText("");
        Phone.setText("");
        Address.setText("");
        pDialog.dismiss();
        Toast.makeText(SingUp.this, message, Toast.LENGTH_LONG).show();
    }

    public void SignUpFunctionProcess(String name,String email,String password,String department,float latitude,float longitude,String address,String phone,String type,String available){

        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = service.userSignUp(name,email, password, department,latitude,longitude,null,address,phone,null,type,available);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String Respone = response.body().string();
                    Gson gson = new Gson();
                    GeneralResponse generalResponse = gson.fromJson(Respone , GeneralResponse.class);

                    if (generalResponse.getError() == 0)
                    {
                        EndSignUpFunction("تم انشاء الحساب بنجاح");
                    }
                    else
                    {
                        EndSignUpFunction(generalResponse.getMessage());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    private void GetCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(SingUp.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<android.location.Location>() {
            @Override
            public void onSuccess(android.location.Location location) {
                if (location != null) {
                    Location.setLatitude((float) location.getLatitude());
                    Location.setLongitude((float) location.getLongitude());
                }
            }
        });
    }

    private void RequestPermissions() {
        ActivityCompat.requestPermissions(SingUp.this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION} , 1);
    }
    private void SetCurrentLocation() {
        if (Location.getLatitude() == 0 || Location.getLongitude() == 0) {
            Location.setLatitude(30.0444f);
            Location.setLongitude(31.2357f);
        }
    }
}
