package com.chocobar.fuutaro.medicare.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.chocobar.fuutaro.medicare.AsyncTasks.core.AsyncTaskActivity;
import com.chocobar.fuutaro.medicare.STATIC_VALUES;
import com.chocobar.fuutaro.medicare.model.Kota;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class PopulateSpinnerKota extends AsyncTask<Void, Void, ArrayList<Kota>> {
    private Context ctx;
    Spinner spin;
    ArrayList<String>arrList = new ArrayList<String>();
    ArrayList<Kota>populateKota = new ArrayList<>();
    public OnFinishedPopulate listener = null;

    public interface OnFinishedPopulate {
        void onFinishedPopulate(ArrayList<Kota> dataKota);
    }

    public PopulateSpinnerKota(Context ctx, Spinner spin, OnFinishedPopulate listener) {
        this.ctx = ctx;
        this.spin = spin;
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(ArrayList<Kota> arrKota) {
        this.arrList.add(0, "Semua kota");
        for (int i = 0; i < arrKota.size(); i++){
            this.arrList.add(arrKota.get(i).getNamaKota());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_item, arrList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(spinnerAdapter);
        listener.onFinishedPopulate(arrKota);
    }

    @Override
    protected ArrayList doInBackground(Void... voids) {
        //calling request to webservice process from AsyncTaskActivity then store the return value
        List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_getKota", null);
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
                return populateKota;
                //if request has been retrieved by SoapObject soapResponse
            else{
                String callSpExcecutionResult = soapResponse.getPropertyAsString("CallSpExcecutionResult");
                JSONArray jArray = new JSONArray(callSpExcecutionResult);

                for (int i = 0; i < jArray.length(); i++) {
                    Kota kota = new Kota();

                    JSONObject jsonKota = jArray.getJSONObject(i);
                    kota.setIdKota(jsonKota.getInt("intIDKota"));
                    kota.setNamaKota(jsonKota.getString("txtKota"));
                    populateKota.add(kota);
                }
            }
            //if transaction failed
        }catch (Exception e){
            e.printStackTrace();
        }
        //operation will return the integer value for execute by onPostExecute method
        return populateKota;
    }
}
