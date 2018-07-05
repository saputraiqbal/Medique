package com.chocobar.fuutaro.medicare;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;


public class MainActivity extends AppCompatActivity {

    //initialize widget objects app
    EditText inputUsername, inputPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize object widgets to UI widget elements
        inputUsername = findViewById(R.id.loginUsername);
        inputPassword = findViewById(R.id.loginPassword);
        btnLogin = findViewById(R.id.btnLogin);

        //initialize login button action
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //execute webservice using AsyncTask
                new WebServiceLogin().execute(inputUsername.getText().toString(), inputPassword.getText().toString());
            }
        });
    }

    //initialize AsyncTask class
    class WebServiceLogin extends AsyncTask<String, Void, Integer> {

        //initialize method when AsyncTask has been executed
        @Override
        protected void onPostExecute(Integer integer) {
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
            Integer bitSuccessConnect = -1;

            //initializing the process of request first
            SoapObject reqsWebSvc = new SoapObject(STATIC_VALUES.NAMESPACE, "CallSpExcecution");

            //configuring the envelope before creating it
            PropertyInfo SPNameInfo = new PropertyInfo();
            SPNameInfo.setNamespace(STATIC_VALUES.NAMESPACE);
            SPNameInfo.setType(PropertyInfo.STRING_CLASS);
            SPNameInfo.setName("txtSPName");
            SPNameInfo.setValue("User_Login");
            reqsWebSvc.addProperty(SPNameInfo);

            PropertyInfo ParamValueInfo = new PropertyInfo();
            ParamValueInfo.setNamespace(STATIC_VALUES.NAMESPACE);
            ParamValueInfo.setType(PropertyInfo.STRING_CLASS);
            ParamValueInfo.setName("txtParamValue");
            ParamValueInfo.setValue("txtUsername#"+strings[0]+"~txtPassword#"+strings[1]);
            reqsWebSvc.addProperty(ParamValueInfo);

            //creating an envelope
            SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            env.setOutputSoapObject(reqsWebSvc);
            env.dotNet = true;

            //creating the request
            HttpTransportSE httpTrans = new HttpTransportSE(STATIC_VALUES.URL);

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
