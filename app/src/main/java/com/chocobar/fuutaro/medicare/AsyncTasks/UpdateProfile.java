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

public class UpdateProfile extends AsyncTask<String, Void, Integer>{
    //initialize some objects
    private ProgressDialog updateLoad;
    private Context ctx;

    public UpdateProfile(Context ctx){
        updateLoad = new ProgressDialog(ctx);
        this.ctx = ctx;
    }

    //action takes before apps AsyncTask is working
    @Override
    protected void onPreExecute() {
        updateLoad.setMessage("Tunggu sebentar...");
        updateLoad.show();
    }

    //initialize method when AsyncTask has been executed
    @Override
    protected void onPostExecute(Integer integer) {
        if(updateLoad.isShowing())
            updateLoad.dismiss();
        if(integer == 1){
            Toast.makeText(ctx, "Profil Anda telah diubah", Toast.LENGTH_LONG).show();
//            Intent enterApps = new Intent(ctx, MainActivity.class);
//            enterApps.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            ctx.startActivity(enterApps);
        }
        else if (integer == 0)
            Toast.makeText(ctx, "Mohon maaf, profil Anda tidak dapat diubah. Coba lagi", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(ctx, "Mohon maaf, ada kesalahan saat mengubah profil Anda. Silakan coba lagi", Toast.LENGTH_LONG).show();
    }

    //initialize AsyncTask method
    @Override
    protected Integer doInBackground(String... strings) {
        //initialize return value
        Integer bitSuccessUpdate = -1;

        //calling request to webservice process from AsyncTaskActivity then store the return value
        List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_UpdDataProfile",
                "intIDUser#1~txtNamaUser#"+ strings[0] + "~txtNoKTP#" + strings[1]
                        + "~txtTempatLahir#"+ strings[2] +"~dtTanggalLahir#" + strings[3]
                        + "~txtAlamat#" + strings[4] + "~txtPhone#" + strings[5]+ "~intIDJaminanKesehatan#" + strings[6]
                        + "~txtNoJaminanKesehatan#" + strings[7] + "~txtAvatar#" + strings[8]);
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
                return bitSuccessUpdate;
                //if request has been retrieved by SoapObject soapResponse
            else{
                String callSpExcecutionResult = soapResponse.getPropertyAsString("CallSpExcecutionResult");
                JSONArray jArray = new JSONArray(callSpExcecutionResult);
                bitSuccessUpdate = jArray.getJSONObject(0).getInt("bitSuccess");
            }
            //if transaction failed
        }catch (Exception e){
            e.printStackTrace();
        }
        //operation will return the integer value for execute by onPostExecute method
        return bitSuccessUpdate;
    }
}
