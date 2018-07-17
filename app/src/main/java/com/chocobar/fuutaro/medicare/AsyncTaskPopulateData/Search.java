package com.chocobar.fuutaro.medicare.AsyncTaskPopulateData;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.chocobar.fuutaro.medicare.AdapterData;
import com.chocobar.fuutaro.medicare.AsyncTaskActivity;
import com.chocobar.fuutaro.medicare.STATIC_VALUES;
import com.chocobar.fuutaro.medicare.model.Dokter;
import com.chocobar.fuutaro.medicare.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class Search extends AsyncTask<String, Void, ArrayList<Dokter>> {
    private RecyclerView rView;
    private AdapterData adapter;
    ArrayList<Dokter> arrayList = new ArrayList<>();

    private Activity activity;

    public OnSearchFinished listener;

    public interface OnSearchFinished{
        void OnSearchFinished(ArrayList<Dokter> dataDokter);
    }

    public Search(Activity activity) {
        this.activity = activity;
        this.rView = MainActivity.rView;
        this.adapter = MainActivity.adapter;
    }

    @Override
    protected void onPostExecute(ArrayList<Dokter> arrayList) {
        adapter = new AdapterData(arrayList, this.activity);
        rView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected ArrayList doInBackground(String... strings) {
        //calling request to webservice process from AsyncTaskActivity then store the return value
        List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_SearchTop20Dokter", "txtKeywords#"+ strings[0] +"~intIDKota#"+ strings[1] +"~intIDSpesialisDokter#0"+"~intIDJenisKelamin#0");
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
                return arrayList;
                //if request has been retrieved by SoapObject soapResponse
            else{
                String callSpExcecutionResult = soapResponse.getPropertyAsString("CallSpExcecutionResult");
                JSONArray jArray = new JSONArray(callSpExcecutionResult);

                for (int i = 0; i < jArray.length(); i++){
                    Dokter dokter = new Dokter();

                    JSONObject dataDokter = jArray.getJSONObject(i);
                    dokter.setNama(dataDokter.getString("txtNamaDokter"));
                    dokter.setNoTelp(dataDokter.getString("txtNoHP"));
                    dokter.setAlamat(dataDokter.getString("txtAlamat"));
                    dokter.setProvinsi(dataDokter.getString("txtProvinsi"));
                    dokter.setKota(dataDokter.getString("txtKota"));
                    dokter.setSpesialis(dataDokter.getString("txtSpesialis"));
                    dokter.setImg(dataDokter.getString("imgAvatar"));
                    arrayList.add(dokter);
                }
            }
            //if transaction failed
        }catch (Exception e){
            e.printStackTrace();
        }
        //operation will return the integer value for execute by onPostExecute method
        return arrayList;
    }
}
