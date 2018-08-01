package com.chocobar.fuutaro.medicare.AsyncTasks;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.chocobar.fuutaro.medicare.AsyncTasks.core.AsyncTaskActivity;
import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.STATIC_VALUES;

import org.json.JSONArray;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GetNomorAntrian extends AsyncTask<String, Void, String[]> {
    private String[] arrString = new String[2];
    private Context ctx;
    private Activity activity;


    public GetNomorAntrian(Context ctx){
        this.ctx = ctx;
        this.activity = (Activity)ctx;
    }

    @Override
    protected void onPostExecute(String[] strings) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(ctx.getString(R.string.antrian_popup)  + " " + arrString[0] + ". " + ctx.getString(R.string.loket_popup) + " " + arrString[1])
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                }).create().show();
    }

    @Override
    protected String[] doInBackground(String... strings) {
        //calling request to webservice process from AsyncTaskActivity then store the return value
        List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_GenerateNoAntrianFromApp",
                "intIDPartner#"+ strings[0] +"~intIDJenisPelayanan#"+ strings[1]
                        +"~intIDUser#"+ strings[2] +"~intIDLoket#"+ strings[3]
                        +"~intIDJadwalPraktek#"+ strings[4] +"~dtAntrian#" + setDateToday());
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
                return arrString;
                //if request has been retrieved by SoapObject soapResponse
            else{
                String callSpExcecutionResult = soapResponse.getPropertyAsString("CallSpExcecutionResult");
                JSONArray jArray = new JSONArray(callSpExcecutionResult);
                String nomorLoket = jArray.getJSONObject(0).getString("txtNoAntrianLoket");
                String nomorPoli = jArray.getJSONObject(0).getString("txtNoAntrianPoli");
                arrString[0] = nomorLoket;
                arrString[1] = nomorPoli;
            }
        }catch (Exception e){
                e.printStackTrace();
            }
        return arrString;
    }

    private String setDateToday(){
        String todayOrder = "";
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatSend = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        todayOrder = formatSend.format(today);
        return todayOrder;
    }
}
