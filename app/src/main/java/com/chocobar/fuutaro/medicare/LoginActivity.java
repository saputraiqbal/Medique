package com.chocobar.fuutaro.medicare;

//import anything needed for this Activity
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;


public class LoginActivity extends AppCompatActivity {

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

        //initialize login button action
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!inputUsername.getText().toString().equals("") || !inputPassword.getText().toString().equals(""))
                    //execute webservice using AsyncTask
                    new WebServiceLogin(LoginActivity.this).execute(inputUsername.getText().toString(), inputPassword.getText().toString());
                else
                    Toast.makeText(getApplicationContext(),"Kolom Login kosong. Harap isi terlebih dahulu", Toast.LENGTH_LONG).show();
            }
        });

        //action takes when text linked to SignUpActivity is clicked
        goToSignUpActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });
    }

    //initialize AsyncTask class
    class WebServiceLogin extends AsyncTask<String, Void, Integer> {
        //initialize ProgressDialog
        private ProgressDialog loginLoad;

        private WebServiceLogin (LoginActivity loginActivity){
            loginLoad = new ProgressDialog(loginActivity);
        }

        //action takes before apps AsyncTask is working
        @Override
        protected void onPreExecute() {
            loginLoad.setMessage("Tunggu sebentar...");
            loginLoad.show();
        }

        //initialize method when AsyncTask has been executed
        @Override
        protected void onPostExecute(Integer integer) {
            if(loginLoad.isShowing())
                loginLoad.dismiss();
            if(integer == 1)
                Toast.makeText(getApplicationContext(), "Login Sukses!", Toast.LENGTH_LONG).show();
            else if (integer == 0)
                Toast.makeText(getApplicationContext(), "Login Gagal", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), "Maaf, ada kesalahan. Silakan laporkan pada pihak berwenang...", Toast.LENGTH_LONG).show();
        }

        //initialize AsyncTask method
        @Override
        protected Integer doInBackground(String... strings) {
            //initialize return value
            Integer bitSuccessConnect = -1;

            //calling request to webservice process from AsyncTaskActivity then store the return value
            List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_Login", "txtUsername#"+strings[0]+"~txtPassword#"+strings[1]);
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
