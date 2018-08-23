package com.chocobar.fuutaro.medicare.AsyncTasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chocobar.fuutaro.medicare.AsyncTasks.core.AsyncTaskActivity;
import com.chocobar.fuutaro.medicare.STATIC_VALUES;
import com.chocobar.fuutaro.medicare.adapter.AdapterDokter;
import com.chocobar.fuutaro.medicare.fragment.MainDokterFragment;
import com.chocobar.fuutaro.medicare.fragment.SearchDokterFragment;
import com.chocobar.fuutaro.medicare.model.Dokter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class SearchDokter extends AsyncTask<String, Void, ArrayList<Dokter>> {
    private RecyclerView rView;
    private AdapterDokter adapter;
    ArrayList<Dokter> arrayList = new ArrayList<>();

    private Activity activity;

    public SearchDokter(Activity activity) {
        this.activity = activity;
        this.rView = SearchDokterFragment.rView;
    }

    @Override
    protected void onPostExecute(ArrayList<Dokter> arrayList) {
        rView.addItemDecoration(new DividerItemDecoration(this.activity, DividerItemDecoration.VERTICAL));
        adapter = new AdapterDokter(arrayList, this.activity);
        rView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected ArrayList doInBackground(String... strings) {
        //calling request to webservice process from AsyncTaskActivity then store the return value
        List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_SearchDokter", "txtKeywords#"+ strings[0] +"~intIDKota#"+ strings[1]
                +"~intIDSpesialisDokter#"+ strings[2] +"~intIDJenisKelamin#" + strings[3]);
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

                    //set item values and store at ArrayList
                    JSONObject dataDokter = jArray.getJSONObject(i);
                    dokter.setNama(dataDokter.getString("txtNamaDokter"));
                    dokter.setNoTelp(dataDokter.getString("txtNoHP"));
                    dokter.setAlamat(dataDokter.getString("txtAlamat"));
                    dokter.setProvinsi(dataDokter.getString("txtProvinsi"));
                    dokter.setKota(dataDokter.getString("txtKota"));
                    dokter.setSpesialis(dataDokter.getString("txtSpesialis"));
                    dokter.setImg(dataDokter.getString("imgAvatar"));
                    dokter.setIdDokter(Integer.toString(dataDokter.getInt("intIDDokter")));
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
