package com.chocobar.fuutaro.medicare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;

import com.chocobar.fuutaro.medicare.STATIC_VALUES;

import java.net.Proxy;
import java.util.List;

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
                ParamValueInfo.setValue("txtUsername#"+inputUsername.getText().toString()+"~txtPassword#"+inputPassword.getText().toString());
                reqsWebSvc.addProperty(ParamValueInfo);

                SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                env.setOutputSoapObject(reqsWebSvc);
                env.dotNet = true;

                try {
                    HttpTransportSE httpTrans = new HttpTransportSE(STATIC_VALUES.URL);
                    httpTrans.call(STATIC_VALUES.SOAP_ACTION, env);
                    SoapPrimitive result = (SoapPrimitive)env.getResponse();

                    if(result != null)
                        Toast.makeText(getApplicationContext(), "Login Sukses!", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Login Gagal", Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error :P", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
