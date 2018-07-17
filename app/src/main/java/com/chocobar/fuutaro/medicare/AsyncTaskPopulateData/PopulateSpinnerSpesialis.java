package com.chocobar.fuutaro.medicare.AsyncTaskPopulateData;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.chocobar.fuutaro.medicare.AsyncTaskActivity;
import com.chocobar.fuutaro.medicare.STATIC_VALUES;
import com.chocobar.fuutaro.medicare.model.Kota;
import com.chocobar.fuutaro.medicare.model.Spesialis;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class PopulateSpinnerSpesialis extends AsyncTask<Void, Void, ArrayList<Spesialis>>{
    private Context ctx;
    Spinner spin;
    ArrayList<String> arrList = new ArrayList<String>();
    ArrayList<Spesialis>populateSpesialis = new ArrayList<>();
    public OnFinishedPopulate listener = null;

    public interface OnFinishedPopulate{
        void OnFinishedPopulate(ArrayList<Spesialis> dataSpesialis);
    }

    public PopulateSpinnerSpesialis(Context ctx, Spinner spin, OnFinishedPopulate listener) {
        this.ctx = ctx;
        this.spin = spin;
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(ArrayList<Spesialis> arrSpesialis) {
        this.arrList.add(0, "Semua spesialis");
        for (int i = 0; i < arrSpesialis.size(); i++){
            arrList.add(arrSpesialis.get(i).getSpesialis());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_item, arrList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(spinnerAdapter);
        listener.OnFinishedPopulate(arrSpesialis);
    }

    @Override
    protected ArrayList doInBackground(Void... voids) {
        //calling request to webservice process from AsyncTaskActivity then store the return value
        List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_getSpesialis", null);
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
                return populateSpesialis;
                //if request has been retrieved by SoapObject soapResponse
            else{
                String callSpExcecutionResult = soapResponse.getPropertyAsString("CallSpExcecutionResult");
                JSONArray jArray = new JSONArray(callSpExcecutionResult);

                for (int i = 0; i < jArray.length(); i++) {
                    Spesialis spesialis = new Spesialis();

                    JSONObject jsonKota = jArray.getJSONObject(i);
                    spesialis.setIdSpesialis(jsonKota.getInt("intIDSpesialisDokter"));
                    spesialis.setSpesialis(jsonKota.getString("txtSpesialis"));
                    populateSpesialis.add(spesialis);
                }
            }
            //if transaction failed
        }catch (Exception e){
            e.printStackTrace();
        }
        //operation will return the integer value for execute by onPostExecute method
        return populateSpesialis;
    }
}

