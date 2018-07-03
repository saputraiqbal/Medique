package com.chocobar.fuutaro.medicare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {

    EditText inputUsername, inputPassword;
    Button btnLogin;

    private static final String SOAP_ACTION = "http://tempuri.org/CallSpExcecution";
    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String METHOD_NAME = "CallSpExcecution" ;
    private static final String URL = "http://api.emedica.co.id:81/ApiEmedicaPublic/eMedicaAPI.asmx?WSDL";

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
                SoapObject reqsWebSvc = new SoapObject(NAMESPACE, METHOD_NAME);

                PropertyInfo pLoginUsername = new PropertyInfo();
                pLoginUsername.setNamespace(NAMESPACE);
                pLoginUsername.setType(PropertyInfo.STRING_CLASS);
                pLoginUsername.setName("txtSPName");
                pLoginUsername.setValue(inputUsername.getText().toString());

                PropertyInfo pLoginPassword = new PropertyInfo();
                pLoginPassword.setNamespace(NAMESPACE);
                pLoginPassword.setType(PropertyInfo.STRING_CLASS);
                pLoginPassword.setName("txtParamValue");
                pLoginPassword.setValue(inputPassword.getText().toString());

                SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                env.setOutputSoapObject(reqsWebSvc);
                env.dotNet = true;

                try {
                    HttpTransportSE httpTrans = new HttpTransportSE(URL);
                    httpTrans.call(SOAP_ACTION, env);
                    SoapObject result = (SoapObject)env.bodyIn;

                    if(result != null)
                        Toast.makeText(getApplicationContext(), "Login Sukses!", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Login Gagal", Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}
