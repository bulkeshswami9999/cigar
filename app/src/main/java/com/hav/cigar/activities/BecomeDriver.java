package com.hav.cigar.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hav.cigar.R;
import com.hav.cigar.api.APIClient;
import com.hav.cigar.api.APIInterface;
import com.hav.cigar.utility.Constant;

import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class BecomeDriver extends AppCompatActivity {
    AppCompatEditText license, address, city, state, pincode,name,mobilenumber,dob,email,pass,Rpass;
    AppCompatButton register;
    APIInterface apiInterface;
    DatePickerDialog datePickerDialog;
    private static String TAG = "BecomeDriver";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_driver);
        name = findViewById(R.id.dname);
        dob = findViewById(R.id.dob);
        email = findViewById(R.id.email);
        mobilenumber = findViewById(R.id.mobileNumber);
        pass = findViewById(R.id.password);
        Rpass = findViewById(R.id.password1);
        license = findViewById(R.id.license);
        register = findViewById(R.id.register);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        pincode = findViewById(R.id.pincode);
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        dob.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
                datePickerDialog = new DatePickerDialog(BecomeDriver.this,R.style.CustomDatePickerDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year1,
                                                  int monthOfYear, int dayOfMonth) {
                               // year =year1;
                                dob.setText(year1+"-"+String.format("%02d",monthOfYear+1)+"-"+String.format("%02d",dayOfMonth));
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                //Toast.makeText(RegisterActivity.this,"myear"+mYear+mDay+mMonth,Toast.LENGTH_LONG).show();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText()!=null&& name.getText().toString().isEmpty())
                    Constant.showToast(BecomeDriver.this,"Enter Your Name");
                else if(email.getText()!=null&& email.getText().toString().isEmpty())
                    Constant.showToast(BecomeDriver.this,"Enter Email Address");
                else if(mobilenumber.getText()!=null&& mobilenumber.getText().toString().isEmpty())
                    Constant.showToast(BecomeDriver.this,"Enter Mobile Number");
                else if(dob.getText()!=null&& dob.getText().toString().isEmpty())
                    Constant.showToast(BecomeDriver.this,"Enter Date of Birth");
               else if (license.getText() != null && license.getText().toString().isEmpty())
                    Constant.showToast(BecomeDriver.this, "Enter a license number");
                 else if (address.getText() != null && address.getText().toString().isEmpty())
                    Constant.showToast(BecomeDriver.this, "Address field is empty");
                 else if (city.getText() != null && city.getText().toString().isEmpty())
                    Constant.showToast(BecomeDriver.this, "Enter a city");
                 else if (state.getText() != null && state.getText().toString().isEmpty())
                    Constant.showToast(BecomeDriver.this, "Enter a State");
                 else if (pincode.getText() != null && pincode.getText().toString().isEmpty())
                    Constant.showToast(BecomeDriver.this, "Enter a pincode");
                else if(mobilenumber.getText().toString().isEmpty() && mobilenumber.getText() != null||mobilenumber.getText().toString().length()!=10) {
                    Toast.makeText(BecomeDriver.this,"Enter a valid phone number",Toast.LENGTH_LONG).show();
                }else if(!Rpass.getText().toString().equals(pass.getText().toString())||pass.getText().toString().length()<4||pass.getText().toString().length()>8){
                    Toast.makeText(BecomeDriver.this,"Password Mismatch..Enter a password with minimum of 4 digits and maximum of 8 digits",Toast.LENGTH_LONG).show();
                }
                 else {
                     SignUpNewDriver();
                    Constant.showToast(BecomeDriver.this,
                            "your Application sent for review. We will shortly inform you on Email-id and mobile number");
                }
            }
        });
    }

    public void SignUpNewDriver() {
        JsonObject js = new JsonObject();
        js.addProperty("phone",mobilenumber.getText().toString());
        js.addProperty("full_name", name.getText().toString());
        //js.addProperty("email_id", email.getText().toString());
        //js.addProperty("password", pass.getText().toString());
        js.addProperty("dob", dob.getText().toString());
        js.addProperty("address_1",address.getText().toString());
        //js.addProperty("address_2","");
        //js.addProperty("country", "");
        //js.addProperty("state",state.getText().toString());
        //js.addProperty("city", city.getText().toString());
       // js.addProperty("pincode",pincode.getText().toString());
        //js.addProperty("license",license.getText().toString());
        Log.d(TAG, "Sigup New User " + js.toString());

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<JsonObject> call = apiInterface.signUpDriver(mobilenumber.getText().toString(),name.getText().toString(),
                dob.getText().toString(),address.getText().toString(),"","",state.getText().toString(),city.getText().toString(),
                pincode.getText().toString(), Objects.requireNonNull(license.getText()).toString());
        Log.d(TAG, "Call " + call.request().url());
        call.enqueue(new retrofit2.Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "onResponse " + response.body());
                JsonObject js = response.body();
                String msg = js.get("data").getAsJsonObject().get("message").getAsString();
                Toast.makeText(BecomeDriver.this,msg,Toast.LENGTH_LONG).show();
                Constant.hideDialog();

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure " + call.toString());

            }
        });
    }
}
