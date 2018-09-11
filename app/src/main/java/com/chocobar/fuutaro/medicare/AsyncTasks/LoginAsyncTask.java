package com.chocobar.fuutaro.medicare.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.chocobar.fuutaro.medicare.AsyncTasks.core.AsyncTaskActivity;
import com.chocobar.fuutaro.medicare.STATIC_VALUES;
import com.chocobar.fuutaro.medicare.activity.MainActivity;

import org.json.JSONArray;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;

import static com.chocobar.fuutaro.medicare.STATIC_VALUES.LOGIN_STATUS;
import static com.chocobar.fuutaro.medicare.STATIC_VALUES.USER_ID;

public class LoginAsyncTask extends AsyncTask<String, Void, Integer>{
    //initialize some objects
    private ProgressDialog loginLoad;
    private Context ctx;

    public LoginAsyncTask (Context ctx){
        loginLoad = new ProgressDialog(ctx);
        this.ctx = ctx;
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
        if(integer == 1){
            Toast.makeText(ctx, "Login Sukses!", Toast.LENGTH_LONG).show();
            Intent enterApps = new Intent(ctx, MainActivity.class);
            enterApps.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(enterApps);
        }
        else if (integer == 0)
            Toast.makeText(ctx, "Login Gagal", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(ctx, "Maaf, ada kesalahan. Silakan laporkan pada pihak berwenang...", Toast.LENGTH_LONG).show();
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
                LOGIN_STATUS = 1;
                USER_ID = Integer.toString(jArray.getJSONObject(0).getInt("intIDUser"));
            }
            //if transaction failed
        }catch (Exception e){
            e.printStackTrace();
        }
        //operation will return the integer value for execute by onPostExecute method
        return bitSuccessConnect;
    }
}
