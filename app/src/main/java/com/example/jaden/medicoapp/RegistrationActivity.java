package com.example.jaden.medicoapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.jaden.medicoapp.patientrecord.utils.VolleyController;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    //Declaring All Global views in the Layout
    EditText editTextRegFName, editTextRegLName, editTextRegMobile, editTextRegEmail,
            editTextRegPassword, editTextRegConfirmPassword;
    Button buttonSignUp;

    //declaring boolean values to validate each field
    boolean fName, lName, mobile, email, password, confirmPassword;

    //declaring variables to hold values
    String firstName, lastName, mobileNumber, emailAddress, passwordHolder, confirmPasswordHolder;

    //declaring request Quest for volley
    //and progress dialog to show progress
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    //Url for registration
    String url;

    //to initialize all global views and the layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //initialing global EditText views
        editTextRegFName = (EditText) findViewById(R.id.editTextRegFName);
        editTextRegLName = (EditText) findViewById(R.id.editTextRegLName);
        editTextRegMobile = (EditText) findViewById(R.id.editTextRegMobile);
        editTextRegEmail = (EditText) findViewById(R.id.editTextRegEmail);
        editTextRegPassword = (EditText) findViewById(R.id.editTextRegPassword);
        editTextRegConfirmPassword = (EditText) findViewById(R.id.editTextRegConfirmPassword);

        //initialize booleans
        fName = lName = mobile = email = password = confirmPassword = false;

        //onFocusChangeListeners

        //Patient First Name
        editTextRegFName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            boolean firstFocus = true;
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (firstFocus) {
                    firstFocus = false;
                    return;
                }
                if(editTextRegFName.getText().toString().equals(""))
                    return;
                if(editTextRegFName.getText().toString().length() < 2)
                {
                    Log.i("Abdul","got here 1");
                    Toast.makeText(getApplicationContext(), "First Name should atleast have 2 letters", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        editTextRegFName.setBackgroundTintList(getApplicationContext().getResources()
                                .getColorStateList(R.color.caution_red));
                        fName = false;
                    }
                }
                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Log.i("Abdul","got here 2");
                    editTextRegFName.setBackgroundTintList(getApplicationContext().getResources()
                            .getColorStateList(R.color.simply_white));
                    fName = true;
                    firstName = editTextRegFName.getText().toString();
                }
            }
        });

        //Patient Last Name
        editTextRegLName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            boolean firstFocus = true;
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (firstFocus) {
                    firstFocus = false;
                    return;
                }
                if(editTextRegLName.getText().toString().equals(""))
                    return;
                if(editTextRegLName.getText().toString().length() < 2)
                {
                    Toast.makeText(getApplicationContext(), "Last Name should atleast have 2 letters", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        editTextRegLName.setBackgroundTintList(getApplicationContext().getResources()
                                .getColorStateList(R.color.caution_red));
                        lName = false;
                    }
                }
                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    editTextRegLName.setBackgroundTintList(getApplicationContext().getResources()
                            .getColorStateList(R.color.simply_white));
                    lName = true;
                    lastName = editTextRegLName.getText().toString();
                }
            }
        });

        //Mobile Number
        editTextRegMobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            boolean firstFocus = true;
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (firstFocus) {
                    firstFocus = false;
                    return;
                }
                if(editTextRegMobile.getText().toString().equals(""))
                    return;
                if(!isValidPhoneNo(editTextRegMobile.getText().toString())|| editTextRegMobile.getText().toString().length()<10)
                {
                    Toast.makeText(getApplicationContext(), "Phone Number is invalid", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        editTextRegMobile.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.caution_red));
                        mobile = false;
                    }
                }
                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    editTextRegMobile.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.simply_white));
                    mobile = true;
                    mobileNumber = editTextRegMobile.getText().toString();
                }
            }
        });

        //Email Address
        editTextRegEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            boolean firstFocus = true;
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (firstFocus) {
                    firstFocus = false;
                    return;
                }
                if(editTextRegEmail.getText().toString().equals(""))
                    return;
                if(!isValidEmailAddress(editTextRegEmail.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Email is invalid", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        editTextRegEmail.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.caution_red));
                        email = false;
                    }
                }
                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    editTextRegEmail.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.simply_white));
                    email = true;
                    emailAddress = editTextRegEmail.getText().toString();
                }
            }
        });

        //password
        editTextRegPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            boolean firstFocus = true;
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (firstFocus) {
                    firstFocus = false;
                    return;
                }
                if(editTextRegPassword.getText().toString().equals(""))
                    return;
                if(editTextRegPassword.getText().toString().length() < 6)
                {
                    Toast.makeText(getApplicationContext(), "Password is of invalid length", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        editTextRegPassword.setBackgroundTintList(getApplicationContext().getResources()
                                .getColorStateList(R.color.caution_red));
                        password = false;
                    }
                }
                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    editTextRegPassword.setBackgroundTintList(getApplicationContext().getResources()
                            .getColorStateList(R.color.simply_white));
                    password = true;
                    passwordHolder = editTextRegPassword.getText().toString();
                }
            }
        });

        //confirm password
        editTextRegConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            boolean firstFocus = true;
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (firstFocus) {
                    firstFocus = false;
                    return;
                }
                if(editTextRegConfirmPassword.getText().toString().equals(""))
                    return;
                if(editTextRegPassword.getText().toString().compareTo(editTextRegConfirmPassword.getText().toString())!=0)
                {
                    Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        editTextRegConfirmPassword.setBackgroundTintList(getApplicationContext().getResources()
                                .getColorStateList(R.color.caution_red));
                        confirmPassword = false;
                    }
                }
                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    editTextRegConfirmPassword.setBackgroundTintList(getApplicationContext().getResources()
                            .getColorStateList(R.color.simply_white));
                    confirmPassword = true;
                    confirmPasswordHolder = editTextRegConfirmPassword.getText().toString();
                }
            }
        });

        //initializing the button
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);

        // Registering for OnClickListener
        buttonSignUp.setOnClickListener(this);
    }

    //validate phone number
    private boolean isValidPhoneNo(CharSequence phoneNo) {
        if (!TextUtils.isEmpty(phoneNo)) {
            return android.util.Patterns.PHONE.matcher(phoneNo).matches();
        }
        return false;
    }

    //Validate Email Address
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|" +
                "(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    public void onClick(View v) {
//        String firstName, lastName, mobileNumber, emailAddress, passwordHolder, confirmPasswordHolder;
        firstName = editTextRegFName.getText().toString();
        lastName = editTextRegLName.getText().toString();
        mobileNumber = editTextRegMobile.getText().toString();
        emailAddress = editTextRegEmail.getText().toString();
        passwordHolder = editTextRegPassword.getText().toString();
        confirmPasswordHolder = editTextRegConfirmPassword.getText().toString();
        Log.i("Abdul","URL"+url);
        Log.i("Abdul","fname"+String.valueOf(fName));
        Log.i("Abdul","lname"+String.valueOf(lName));
        Log.i("Abdul","mobile"+String.valueOf(mobile));
        Log.i("Abdul","email"+String.valueOf(email));
        Log.i("Abdul","password"+String.valueOf(password));
        Log.i("Abdul","confirmpassword"+String.valueOf(confirmPassword));

        if(fName && lName && mobile && email && password && confirmPassword ){
            url = "http://rjtmobile.com/medictto/patient_registration.php?&firstName=" + firstName +
                    "&lastName=" + lastName + "&email=" + emailAddress + "&number=" + mobileNumber + "&password=" + passwordHolder;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String result;
                            try {
                                result = response.getString("result");
                                Log.i("Abdul",result);
                                Intent loginIntent = new Intent(RegistrationActivity.this,LoginActivity.class);
                                startActivity(loginIntent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("Abdul","catch was called");
                                Toast.makeText(RegistrationActivity.this,"There was an error",Toast.LENGTH_SHORT);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Abdul","error:" + error.toString());
                            Toast.makeText(RegistrationActivity.this,"There was an error, Try again later",Toast.LENGTH_SHORT);
                        }
                    }
            );
            VolleyController.getInstance().addToRequestQueue(jsonObjectRequest);
        }else{
            Toast.makeText(this, "There is some error try again",Toast.LENGTH_SHORT).show();
        }
    }
}