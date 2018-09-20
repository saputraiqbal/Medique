package com.chocobar.fuutaro.medicare.activity;

//import anything needed for this Activity
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.chocobar.fuutaro.medicare.AsyncTasks.SignUpAsyncTask;
import com.chocobar.fuutaro.medicare.AsyncTasks.core.AsyncTaskActivity;
import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.STATIC_VALUES;

import org.json.JSONArray;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    //initialize widget objects app
    EditText getFullname, getEmail, getPassword, getUsername, getConfirmPassword;
    TextView backToLogin;
    Button btnSignup;

    //initalize ArrayList for storing EditText values
    ArrayList<String> setSignupForm = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //receive Intent from LoginActivity
        final Bundle getSignUpIntent = getIntent().getExtras();

        //initialize object widgets to UI widget elements
        getFullname = findViewById(R.id.signUpFullName);
        getEmail = findViewById(R.id.signUpEmail);
        getUsername = findViewById(R.id.signUpUsername);
        getPassword = findViewById(R.id.signUpPassword);
        getConfirmPassword = findViewById(R.id.signUpConfirmPassword);
        btnSignup = findViewById(R.id.btnSignUp);
        backToLogin = findViewById(R.id.txtBackToLogin);

        //action takes when button is clicked
        btnSignup.setOnClickListener(this);
        //action takes when TextView is clicked
        backToLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignUp:
                //store sign up datas to the ArrayList
                setSignupForm.addAll(Arrays.asList(getFullname.getText().toString(), getEmail.getText().toString(), getUsername.getText().toString(), getPassword.getText().toString()));
                //checked if form is fulfilled
                if(isEditTxtEmpty(setSignupForm) && !getConfirmPassword.getText().toString().isEmpty()){
                    //checked whether confirmed password is equal by password entered by user or not
                    if (getConfirmPassword.getText().toString().equals(setSignupForm.get(3)))
                        //call SignUp AsyncTask for process signup
                        new SignUpAsyncTask(getApplicationContext()).execute(setSignupForm);
                    else
                        Toast.makeText(getApplicationContext(),"Harap ulangi password Anda dengan benar", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Kolom daftar kosong. Harap isi terlebih dahulu", Toast.LENGTH_LONG).show();
                break;
            case R.id.txtBackToLogin:
                Intent backToLogin = new Intent(SignUpActivity.this, LoginActivity.class);
                finish();
                break;
        }
    }

    //method for check whether form data is fulfilled or not
    public static boolean isEditTxtEmpty(ArrayList<String> checkTxt){
        for (String check : checkTxt){
            if(check.isEmpty())
            return false;
        }
        return true;
    }
}
