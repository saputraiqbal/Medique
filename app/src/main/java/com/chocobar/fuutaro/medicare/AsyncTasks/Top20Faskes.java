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
import com.chocobar.fuutaro.medicare.adapter.AdapterFaskes;
import com.chocobar.fuutaro.medicare.fragment.MainDokterFragment;
import com.chocobar.fuutaro.medicare.fragment.MainFaskesFragment;
import com.chocobar.fuutaro.medicare.model.Faskes;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class Top20Faskes extends AsyncTask<String, Void, ArrayList<Faskes>> {
    private RecyclerView rView;
    private AdapterFaskes adapter;
    private TextView txtLoad;
    private ProgressBar loadBar;
    ArrayList<Faskes> arrayList = new ArrayList<>();

    private Activity activity;

    public Top20Faskes(Activity activity) {
        this.activity = activity;
        this.rView = MainFaskesFragment.rView;
        this.txtLoad = MainDokterFragment.load;
        this.loadBar = MainDokterFragment.loadBar;
        this.txtLoad.setVisibility(View.GONE);
        this.loadBar.setVisibility(View.GONE);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadBar.setVisibility(View.VISIBLE);
        txtLoad.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(ArrayList<Faskes> arrayList) {
        loadBar.setVisibility(View.GONE);
        txtLoad.setVisibility(View.GONE);
        rView.addItemDecoration(new DividerItemDecoration(this.activity, DividerItemDecoration.VERTICAL));
        adapter = new AdapterFaskes(arrayList, this.activity);
        rView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected ArrayList doInBackground(String... strings) {
        //calling request to webservice process from AsyncTaskActivity then store the return value
        List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_SearchTop20Faskes",
                "txtKeywords	#~intIDKota#~intIDJenisPelayanan#~intIDJenisJamKes1#"
                        + "~intIDJenisJamKes2#~intIDJenisJamKes3#~intIDJenisJamKes4#~intIDJenisJamKes5#" +
                        "~intIDJenisJamKes6#~intIDJenisJamKes7#~intIDJenisJamKes8#~intIDJenisJamKes9#~intIDJenisJamKes10#");
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
                    Faskes faskes = new Faskes();

                    //set item values and store at ArrayList
                    JSONObject dataFaskes = jArray.getJSONObject(i);
                    faskes.setId(Integer.toString(dataFaskes.getInt("intIDPartner")));
                    faskes.setNamaFaskes(dataFaskes.getString("txtPartnerName"));
                    faskes.setAlamat(dataFaskes.getString("txtAlamat"));
                    faskes.setKota(dataFaskes.getString("txtKota"));
                    faskes.setProvinsi(dataFaskes.getString("txtProvinsi"));
                    faskes.setImgFaskes(dataFaskes.getString("imgAvatar"));
                    arrayList.add(faskes);
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
