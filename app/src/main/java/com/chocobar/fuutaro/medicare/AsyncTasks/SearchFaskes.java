package com.chocobar.fuutaro.medicare.AsyncTasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import com.chocobar.fuutaro.medicare.AsyncTasks.core.AsyncTaskActivity;
import com.chocobar.fuutaro.medicare.STATIC_VALUES;
import com.chocobar.fuutaro.medicare.adapter.AdapterFaskes;
import com.chocobar.fuutaro.medicare.fragment.MainFaskesFragment;
import com.chocobar.fuutaro.medicare.model.Faskes;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class SearchFaskes extends AsyncTask<String, Void, ArrayList<Faskes>> {
    private RecyclerView rView;
    private AdapterFaskes adapter;
    ArrayList<Faskes> arrayList = new ArrayList<>();

    private Activity activity;

    public SearchFaskes(Activity activity) {
        this.activity = activity;
        this.rView = MainFaskesFragment.rView;
    }

    @Override
    protected void onPostExecute(ArrayList<Faskes> arrayList) {
        rView.addItemDecoration(new DividerItemDecoration(this.activity, DividerItemDecoration.VERTICAL));
        adapter = new AdapterFaskes(arrayList, this.activity);
        rView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected ArrayList doInBackground(String... strings) {
        //calling request to webservice process from AsyncTaskActivity then store the return value
        List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_SearchFaskes",
                "txtKeywords	#" + strings[0] + "~intIDKota#" + strings[1] + "~intIDJenisPelayanan#" + strings[2] +
                "~intIDJenisJamKes1#" + strings[3] + "~intIDJenisJamKes2#" + strings[4] + "~intIDJenisJamKes3#" + strings[5] +
                "~intIDJenisJamKes4#" + strings[6] + "~intIDJenisJamKes5#" + strings[7] + "~intIDJenisJamKes6#" + strings[8] +
                "~intIDJenisJamKes7#" + strings[9] + "~intIDJenisJamKes8#" + strings[10] + "~intIDJenisJamKes9#" + strings[11] +
                "~intIDJenisJamKes10#" + strings[12]);
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
