package com.chocobar.fuutaro.medicare;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    EditText inputUsername, inputPassword;
    Button btnLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputUsername = findViewById(R.id.loginUsername);
        inputPassword = findViewById(R.id.loginPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new WebServiceLogin().execute(inputUsername.getText().toString(), inputPassword.getText().toString());
            }
        });
    }

    class WebServiceLogin extends AsyncTask<String, Void, Integer> {
        @Override
        protected void onPostExecute(Integer integer) {
            if(integer == 1)
                Toast.makeText(getApplicationContext(), "Login Sukses!", Toast.LENGTH_LONG).show();
            else if (integer == 0)
                Toast.makeText(getApplicationContext(), "Login Gagal", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), "Maaf, ada kesalahan. Silakan laporkan pada pihak berwenang...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            Integer bitSuccessConnect = -1;

            SoapObject reqsWebSvc = new SoapObject(STATIC_VALUES.NAMESPACE, "CallSpExcecution");

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

            SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            env.setOutputSoapObject(reqsWebSvc);
            env.dotNet = true;
            HttpTransportSE httpTrans = new HttpTransportSE(STATIC_VALUES.URL);

            try {
                httpTrans.call(STATIC_VALUES.SOAP_ACTION, env);
                SoapObject soapResponse = (SoapObject) env.bodyIn;

                if(soapResponse.toString().equals("CallSpExcecutionResponse{}") || soapResponse == null)
                    return bitSuccessConnect;
                else{
                    String callSpExcecutionResult = soapResponse.getPropertyAsString("CallSpExcecutionResult");
                    JSONArray jArray = new JSONArray(callSpExcecutionResult);
                    String bitExeResult = jArray.getJSONObject(0).getString("bitSuccess");
                    bitSuccessConnect = Integer.parseInt(bitExeResult);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return bitSuccessConnect;
        }
    }
}
