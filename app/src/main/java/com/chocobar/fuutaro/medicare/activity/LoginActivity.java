package com.chocobar.fuutaro.medicare.activity;

//import anything needed for this Activity
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Window;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.view.View;
import android.widget.Toast;

import com.chocobar.fuutaro.medicare.AsyncTasks.LoginAsyncTask;
import com.chocobar.fuutaro.medicare.AsyncTasks.core.AsyncTaskActivity;
import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.STATIC_VALUES;

import org.json.JSONArray;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //initialize widget objects app
    EditText inputUsername, inputPassword;
    Button btnLogin;
    TextView goToSignUpActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        //initialize object widgets to UI widget elements
        inputUsername = findViewById(R.id.loginUsername);
        inputPassword = findViewById(R.id.loginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        goToSignUpActivity = findViewById(R.id.txtToSignUp);

        goToSignUpActivity.setText(R.string.signUp_link, BufferType.SPANNABLE);
        Spannable span = (Spannable) goToSignUpActivity.getText();
        int start = getString(R.string.signUp_link).indexOf("?") + 2;
        int end = getString(R.string.signUp_link).length();
        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //initialize login button action
        btnLogin.setOnClickListener(this);
        //action takes when text linked to SignUpActivity is clicked
        goToSignUpActivity.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                if(!inputUsername.getText().toString().equals("") || !inputPassword.getText().toString().equals(""))
                    //call Login AsyncTask to process login
                    new LoginAsyncTask(LoginActivity.this).execute(inputUsername.getText().toString(), inputPassword.getText().toString());
                else
                    Toast.makeText(LoginActivity.this,"Kolom Login kosong. Harap isi terlebih dahulu", Toast.LENGTH_LONG).show();
                break;
            case R.id.txtToSignUp:
                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
                break;
        }
    }

}
