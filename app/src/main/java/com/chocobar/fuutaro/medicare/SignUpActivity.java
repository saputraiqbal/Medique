package com.chocobar.fuutaro.medicare;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    //initialize widget objects app
    EditText getFullname, getEmail, getPassword, getUsername, getConfirmPassword;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //receive Intent from LoginActivity
        Bundle getSignUpIntent = getIntent().getExtras();

        //initialize object widgets to UI widget elements
        getFullname = findViewById(R.id.signUpFullName);
        getEmail = findViewById(R.id.signUpEmail);
        getUsername = findViewById(R.id.signUpUsername);
        getPassword = findViewById(R.id.signUpPassword);
        getConfirmPassword = findViewById(R.id.signUpConfirmPassword);
        btnSignup = findViewById(R.id.btnSignUp);

        final List<EditText> getSignupForm = new ArrayList<EditText>();
        getSignupForm.add(getFullname);
        getSignupForm.add(getEmail);
        getSignupForm.add(getUsername);
        getSignupForm.add(getPassword);
        getSignupForm.add(getConfirmPassword);

        final boolean isEmptyTxt = isEmpty(getSignupForm);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmptyTxt)
                    new WebServiceSignup().execute(getSignupForm);
                else
                    Toast.makeText(getApplicationContext(),"Kolom daftar kosong. Harap isi terlebih dahulu", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean isEmpty(List<EditText> checkTxt){
        boolean boolIsEmpty = false;
        for (EditText isEmpty : checkTxt){
            if(isEmpty.getText().toString().trim().length() == 0)
                break;
            else
                boolIsEmpty = true;
        }
        return boolIsEmpty;
    }

    //initialize AsyncTask class

    class WebServiceSignup extends AsyncTask<List<EditText>, Void, Integer> {
        @Override
        protected void onPostExecute(Integer integer) {
            if(integer == 1){
                Toast.makeText(getApplicationContext(), "Sign up sukses!", Toast.LENGTH_LONG).show();
                finish();
            }
            else if (integer == 0)
                Toast.makeText(getApplicationContext(), "Sign up gagal. Silahkan coba lagi", Toast.LENGTH_LONG).show();
            else if (integer == 2)
                Toast.makeText(getApplicationContext(), "Ulangi password Anda dengan benar", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), "Maaf, ada kesalahan. Silakan laporkan pada pihak berwenang...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Integer doInBackground(List<EditText>... lists) {
            Integer bitSuccessConnect = -1;

            //calling request to webservice process from AsyncTaskActivity then store the return value
            List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_RegisterApp",
                    "txtFullName#" + lists[0] +
                    "~txtEmail#" + lists[1] +
                    "~txtUsername#" + lists[2] +
                    "~txtPassword#" + lists[3]);
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
                    if (lists[3].equals(lists[4])){
                        String callSpExcecutionResult = soapResponse.getPropertyAsString("CallSpExcecutionResult");
                        JSONArray jArray = new JSONArray(callSpExcecutionResult);
                        String bitExeResult = jArray.getJSONObject(0).getString("bitSuccess");
                        bitSuccessConnect = Integer.parseInt(bitExeResult);
                    }
                    else
                        bitSuccessConnect = 2;
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
