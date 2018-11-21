package com.chocobar.fuutaro.medicare.AsyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.chocobar.fuutaro.medicare.AsyncTasks.core.AsyncTaskActivity;
import com.chocobar.fuutaro.medicare.STATIC_VALUES;
import com.chocobar.fuutaro.medicare.activity.LoginActivity;
import com.chocobar.fuutaro.medicare.activity.MainActivity;
import com.chocobar.fuutaro.medicare.activity.SignUpActivity;

import org.json.JSONArray;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class SignUpAsyncTask extends AsyncTask<String, Void, Integer>{
    //initialize some objects
    private ProgressDialog signupLoad;
    private Context ctx;
    SignUpActivity activity;

    public SignUpAsyncTask(Context ctx){
        signupLoad = new ProgressDialog(ctx);
        this.ctx = ctx;
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
            Toast.makeText(ctx, "Sign up sukses!", Toast.LENGTH_LONG).show();
            Intent toMainActivity = new Intent(ctx, MainActivity.class);
            toMainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(toMainActivity);
//            activity.finish();
        }
        else if (integer == 0)
            Toast.makeText(ctx, "Sign up gagal. Silahkan coba lagi", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(ctx, "Maaf, ada kesalahan. Silakan laporkan pada pihak berwenang...", Toast.LENGTH_LONG).show();
    }

    //main operation of AsyncTask is work here
    @Override
    protected Integer doInBackground(String... strings) {
        //initialize return value
        Integer bitSuccessConnect = -1;
//
//        //initalize variable to store parameter value
//        List<String> setSignup = lists[0];

        //calling request to webservice process from AsyncTaskActivity then store the return value
        List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_RegisterApp",
                "txtFullName#" + strings[0]+
                        "~txtEmail#" + strings[1]+
                        "~txtUsername#" + strings[2]+
                        "~txtPassword#" + strings[3]);
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
