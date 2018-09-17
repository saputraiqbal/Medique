package com.chocobar.fuutaro.medicare.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.chocobar.fuutaro.medicare.AsyncTasks.core.AsyncTaskActivity;
import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.STATIC_VALUES;
import com.chocobar.fuutaro.medicare.model.LoketPelayanan;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class PopulateLoketPelayanan extends AsyncTask<String, Void, ArrayList<LoketPelayanan>> {
    private Context ctx;
    Spinner spin;
    ArrayList<String>arrList = new ArrayList<>();
    ArrayList<LoketPelayanan>populateLoket = new ArrayList<>();
    public OnLoketData listener = null;

    public interface OnLoketData{
        void onLoketData(ArrayList<LoketPelayanan> dataLoket);
    }

    public PopulateLoketPelayanan(Context ctx, Spinner spin, OnLoketData listener){
        this.ctx = ctx;
        this.spin = spin;
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(ArrayList<LoketPelayanan> arrLoket) {
        for (int i = 0; i < arrLoket.size(); i++){
            this.arrList.add(arrLoket.get(i).getLoket());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(ctx, R.layout.spinner_item, arrList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(spinnerAdapter);
        listener.onLoketData(arrLoket);
    }

    @Override
    protected ArrayList<LoketPelayanan> doInBackground(String... strings) {
        //calling request to webservice process from AsyncTaskActivity then store the return value
        List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_GetJenisLoketPelayanan", "intIDPartner#" + strings[0]);
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
                return populateLoket;
                //if request has been retrieved by SoapObject soapResponse
            else{
                String callSpExcecutionResult = soapResponse.getPropertyAsString("CallSpExcecutionResult");
                JSONArray jArray = new JSONArray(callSpExcecutionResult);

                for (int i = 0; i < jArray.length(); i++) {
                    LoketPelayanan loket = new LoketPelayanan();

                    JSONObject jsonLoket = jArray.getJSONObject(i);
                    loket.setIdLoket(jsonLoket.getInt("intIDLoket"));
                    loket.setLoket(jsonLoket.getString("txtLoket"));
                    populateLoket.add(loket);
                }
            }
            //if transaction failed

        }catch (Exception e){
            e.printStackTrace();
        }
        //operation will return the integer value for execute by onPostExecute method
        return populateLoket;
    }
}
