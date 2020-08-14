package com.hav.cigar.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hav.cigar.R;
import com.hav.cigar.api.APIClient;
import com.hav.cigar.api.APIInterface;
import com.hav.cigar.startup.FrescoApplication;
import com.hav.cigar.startup.MainActivity;
import com.hav.cigar.utility.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    public AppCompatEditText email, passwrd;
    APIInterface apiInterface;
    AppCompatButton login, register, forgotpassword;
    private static String TAG = "LoginActivity";
    FrescoApplication frescoApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        frescoApplication=(FrescoApplication) getApplication();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Objects.requireNonNull(getSupportActionBar()).hide();
//        }
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        } else {
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
        }
        email = findViewById(R.id.enteremail);
        passwrd = findViewById(R.id.enterpassword);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        forgotpassword = findViewById(R.id.forgotpassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1 = email.getText().toString().trim();
                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (email.getText() != null && email.getText().toString().isEmpty()) {
                    Constant.showToast(LoginActivity.this, "Email id is empty");
                } else if (passwrd.getText() != null && passwrd.getText().toString().isEmpty()) {
                    Constant.showToast(LoginActivity.this, "Password is empty");
                } else {
                    if (email1.matches(emailPattern)) {
                        callLoginServices();
                      //  Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Enter a valid Email-ID", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
    }

    public void callLoginServices() {
        ProgressDialog dialog = ProgressDialog.show(LoginActivity.this, "Login", "Please wait...", true);
        JsonObject js = new JsonObject();
        js.addProperty("cusemail", email.getText().toString());
        js.addProperty("cuspassword", passwrd.getText().toString());
        js.addProperty("token",   frescoApplication.getPreferences().getToken(Constant.PREFERENCE_DEVICE_TOKEN,""));
        Log.d(TAG, "Login Services " + js.toString());
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<JsonObject> call = apiInterface.customerLogin(email.getText().toString(), passwrd.getText().toString(),frescoApplication.getPreferences().getToken(Constant.PREFERENCE_DEVICE_TOKEN,""));
        Log.d(TAG, "Login Url " + call.request().url());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                Log.d(TAG, " response" + response.body());
                JsonObject js = response.body();
                if (js != null) {
                    if (js.get("status").getAsInt() == 1) {
                       // Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedpreferences = getSharedPreferences(Constant.MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean("login", true);
                        editor.putInt("user_id", js.get("id").getAsInt());
                        String userId= String.valueOf(js.get("id").getAsInt());
                        frescoApplication.getPreferences().setToken("user_id",userId);
                        editor.apply();
                        if (js.getAsJsonPrimitive("status").getAsInt()==202)
                            Toast.makeText(LoginActivity.this, "Check the LoginCredential", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                            getCigarItem();
                        Log.d(TAG, js.getAsJsonObject("data").get("message").toString());
                       // finish();
                    } else {
                        SharedPreferences sharedpreferences = getSharedPreferences(Constant.MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean("login", true);
                        editor.putInt("user_id", 1);
                        editor.apply();
                        dialog.dismiss();
                        if (js.getAsJsonPrimitive("status").getAsInt()==201)
                        {
                            Toast.makeText(LoginActivity.this, "Check the LoginCredential", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            JsonObject js1 = response.body();
                            String userId= String.valueOf(js1.getAsJsonObject("data").get("id").getAsString());
                            frescoApplication.getPreferences().setToken("user_id",userId);
                            Log.e("==customer_id",""+userId);
                            Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                            getCigarItem();
                        }

                        Log.d(TAG, js.getAsJsonObject("data").get("message").toString());
                        //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                       // finish();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                dialog.dismiss();
                Toast.makeText(LoginActivity.this, "An Error Occured", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getCigarItem() {
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.alert_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        // set alert_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        final EditText userInput = (EditText) promptsView.findViewById(R.id.etUserInput);
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(), "Thank you for your Feedback", Toast.LENGTH_LONG).show();
                        callAnswer(userInput.getText().toString());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void callAnswer(String answer){
        apiInterface = APIClient.getClient().create(APIInterface.class);
        SharedPreferences sp = getSharedPreferences(Constant.MyPREFERENCES,Context.MODE_PRIVATE);
        int user_id = sp.getInt("user_id",0);
        String user_d = Integer.toString(user_id);
        Call<JsonObject> call = apiInterface.InAnswer(answer,user_d);
    }
}
