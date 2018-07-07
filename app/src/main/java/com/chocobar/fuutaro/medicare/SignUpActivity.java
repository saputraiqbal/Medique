package com.chocobar.fuutaro.medicare;

//import anything needed for this Activity
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONArray;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    //initialize widget objects app
    EditText getFullname, getEmail, getPassword, getUsername, getConfirmPassword;
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

        //action takes when button is clicked
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //store sign up datas to the ArrayList
                setSignupForm.addAll(Arrays.asList(getFullname.getText().toString(), getEmail.getText().toString(), getUsername.getText().toString(), getPassword.getText().toString()));
                //checked if form is fulfilled
                if(isEditTxtEmpty(setSignupForm) && !getConfirmPassword.getText().toString().isEmpty()){
                    //checked whether confirmed password is equal by password entered by user or not
                    if (getConfirmPassword.getText().toString().equals(setSignupForm.get(3)))
                        //execute webservice using AsyncTask
                        new WebServiceSignup(SignUpActivity.this).execute(setSignupForm);
                    else
                        Toast.makeText(getApplicationContext(),"Harap ulangi password Anda dengan benar", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Kolom daftar kosong. Harap isi terlebih dahulu", Toast.LENGTH_LONG).show();
           }
        });
    }

    //method for check whether form data is fulfilled or not
    public static boolean isEditTxtEmpty(ArrayList<String> checkTxt){
        for (String check : checkTxt){
            if(check.isEmpty())
            return false;
        }
        return true;
    }

    //initialize AsyncTask class
    class WebServiceSignup extends AsyncTask<List<String>, Void, Integer> {
        //initialize ProgressDialog
        private ProgressDialog signupLoad;

        private WebServiceSignup(SignUpActivity signupActivity){
            signupLoad = new ProgressDialog(signupActivity);
        }

        //action takes before apps AsyncTask is working
        @Override
        protected void onPreExecute() {
            signupLoad.setMessage("Tunggu sebentar...");
            signupLoad.show();
        }

        //action takes when apps AsyncTask is done
        @Override
        protected void onPostExecute(Integer integer) {
            if(signupLoad.isShowing())
                signupLoad.dismiss();
            if(integer == 1){
                Toast.makeText(getApplicationContext(), "Sign up sukses!", Toast.LENGTH_LONG).show();
                finish();
            }
            else if (integer == 0)
                Toast.makeText(getApplicationContext(), "Sign up gagal. Silahkan coba lagi", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), "Maaf, ada kesalahan. Silakan laporkan pada pihak berwenang...", Toast.LENGTH_LONG).show();
        }

        //main operation of AsyncTask is work here
        @Override
        protected Integer doInBackground(List<String>... lists) {
            //initialize return value
            Integer bitSuccessConnect = -1;

            //initalize variable to store parameter value
            List<String> setSignup = lists[0];

            //calling request to webservice process from AsyncTaskActivity then store the return value
            List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_RegisterApp",
                    "txtFullName#" + setSignup.get(0)+
                    "~txtEmail#" + setSignup.get(1)+
                    "~txtUsername#" + setSignup.get(2)+
                    "~txtPassword#" + setSignup.get(3));
            //convert each List values with their match object type data
            SoapSerializationEnvelope env = (SoapSerializationEnvelope) dataReceived.get(0);
            HttpTransportSE httpTrans = (HttpTransportSE) dataReceived.get(1);

            //transaction for sending the request
            try {
                httpTrans.call(STATIC_VALUES.SOAP_ACTION, env);
                //processing the request
                SoapObject soapResponse = (SoapObject) env.bodyIn;

                //selection either SoapObject soapResponse retrieve the request or not
                if(soapResponse.toString().equals("CallSpExcecutionResponse{}") || soapResponse == null)
                    return bitSuccessConnect;
                    //if request has been retrieved by SoapObject soapResponse
                else{
                    String callSpExcecutionResult = soapResponse.getPropertyAsString("CallSpExcecutionResult");
                    JSONArray jArray = new JSONArray(callSpExcecutionResult);
                    String bitExeResult = jArray.getJSONObject(0).getString("bitSuccess");
                    bitSuccessConnect = Integer.parseInt(bitExeResult);
                }
                //if transaction failed
            }catch (Exception e){
                e.printStackTrace();
            }
            //operation will return the integer value for execute by onPostExecute method
            return bitSuccessConnect;
        }
    }
}
