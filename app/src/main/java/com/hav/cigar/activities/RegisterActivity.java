package com.hav.cigar.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hav.cigar.R;
import com.hav.cigar.api.APIClient;
import com.hav.cigar.api.APIInterface;
import com.hav.cigar.utility.Constant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

public    AppCompatEditText fullName,email,pass,dob,Rpass,phone;
    AppCompatButton register,login,BeDriver,BePartner;
    APIInterface apiInterface;
    DatePickerDialog datePickerDialog;
    String str_age;
    int year=0;
    private static String TAG = "RegisterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        fullName = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        dob = findViewById(R.id.dob);
        phone = findViewById(R.id.mobileNumber);
        Rpass = findViewById(R.id.password1);
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        dob.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
               datePickerDialog = new DatePickerDialog(RegisterActivity.this,R.style.CustomDatePickerDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year1,
                                                  int monthOfYear, int dayOfMonth) {
                                year =year1;
                                dob.setText(year+"-"+String.format("%02d",monthOfYear+1)+"-"+String.format("%02d",dayOfMonth));
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                //Toast.makeText(RegisterActivity.this,"myear"+mYear+mDay+mMonth,Toast.LENGTH_LONG).show();
            }
            });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1 = email.getText().toString().trim();
                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(fullName.getText() != null && fullName.getText().toString().isEmpty()){
                    Constant.showToast(RegisterActivity.this,"Full Name is empty");
                }else if(email.getText() != null && email.getText().toString().isEmpty()) {
                    Constant.showToast(RegisterActivity.this,"Email id is empty");
                } else if(pass.getText() != null && pass.getText().toString().isEmpty()){
                    Constant.showToast(RegisterActivity.this,"Password is empty");
                }else if(dob.getText().toString().isEmpty() && dob.getText() != null) {
                    Toast.makeText(RegisterActivity.this,"Date of Birth is empty",Toast.LENGTH_LONG).show();
                }
                else if(phone.getText().toString().isEmpty() && phone.getText() != null||phone.getText().toString().length()!=10) {
                  Toast.makeText(RegisterActivity.this,"Enter a valid phone number",Toast.LENGTH_LONG).show();
                }else if(!Rpass.getText().toString().equals(pass.getText().toString())||pass.getText().toString().length()<4||pass.getText().toString().length()>8){
                    Toast.makeText(RegisterActivity.this,"Password Mismatch..Enter a password with minimum of 4 digits and maximum of 8 digits",Toast.LENGTH_LONG).show();
                }
                else
                    {
                    if ( email1.matches(emailPattern)) {
                        final Calendar c = Calendar.getInstance();
                        int cYear = c.get(Calendar.YEAR); // current year
                        int cMonth = c.get(Calendar.MONTH); // current month
                        int cDay = c.get(Calendar.DAY_OF_MONTH); // current day
                        int age = cYear - year;
                     //   Toast.makeText(RegisterActivity.this,age+"Enter a valid phone number",Toast.LENGTH_LONG).show();
                        if (age > 21) {
                            SignUpNewUser();
                            Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this, "Sorry your age is below the legal age limit for consumption of cigars", Toast.LENGTH_LONG).show();
                        }
                    }
                else {
                    Toast.makeText(RegisterActivity.this,"Enter a valid Email ID",Toast.LENGTH_LONG).show();}
                    }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
    }
    public void SignUpNewUser() {
        JsonObject js = new JsonObject();
        js.addProperty("phone",phone.getText().toString());
        js.addProperty("full_name", fullName.getText().toString());
        js.addProperty("email_id", email.getText().toString());
        js.addProperty("password", pass.getText().toString());
        js.addProperty("dob", dob.getText().toString());

        Log.d(TAG, "Sigup New User " + js.toString());

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<JsonObject> call = apiInterface.signUp(phone.getText().toString(),fullName.getText().toString(),email.getText().toString(),pass.getText().toString(),
                dob.getText().toString());
        Log.d(TAG, "Call " + call.request().url());
        call.enqueue(new retrofit2.Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "onResponse " + response.body());
                JsonObject js = response.body();
                String msg = js.get("data").getAsJsonObject().get("message").getAsString();
                Toast.makeText(RegisterActivity.this,msg,Toast.LENGTH_LONG).show();
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                Constant.hideDialog();

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure " + call.toString());

            }
        });
    }
    public void BecomeADriver(View view)
    {
        startActivity(new Intent(this,BecomeDriver.class));
    }
  public void BecomeAPartner(View view)
  {
      startActivity(new Intent(this,BecomePartner.class));
  }
}
