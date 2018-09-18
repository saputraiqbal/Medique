package com.chocobar.fuutaro.medicare.AsyncTasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.chocobar.fuutaro.medicare.AsyncTasks.core.AsyncTaskActivity;
import com.chocobar.fuutaro.medicare.STATIC_VALUES;
import com.chocobar.fuutaro.medicare.adapter.AdapterRiwayat;
import com.chocobar.fuutaro.medicare.model.Riwayat;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class ViewRiwayat extends AsyncTask<String, Void, ArrayList<Riwayat>> {
    private RecyclerView rView;
    private AdapterRiwayat adapterRiwayat;
    private Activity activity;

    //declare arraylist
    ArrayList<Riwayat> arrRiwayat = new ArrayList<>();

    //declare constructor
    public ViewRiwayat(Activity activity, RecyclerView rView, AdapterRiwayat adapterRiwayat){
        this.activity = activity;
        this.rView = rView;
        this.adapterRiwayat = adapterRiwayat;
    }

    @Override
    protected void onPostExecute(ArrayList<Riwayat> riwayats) {
        adapterRiwayat = new AdapterRiwayat(riwayats, this.activity);
        rView.setAdapter(adapterRiwayat);
        adapterRiwayat.notifyDataSetChanged();
    }

    @Override
    protected ArrayList<Riwayat> doInBackground(String... strings) {
        List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_GetRiwayatAntrian",
                "intIDUser#"+strings[0]);
        SoapSerializationEnvelope env = (SoapSerializationEnvelope)dataReceived.get(0);
        HttpTransportSE httpTrans = (HttpTransportSE)dataReceived.get(1);

        try{
            httpTrans.call(STATIC_VALUES.SOAP_ACTION, env);
            SoapObject soapResponse = (SoapObject)env.bodyIn;

            if(soapResponse.toString().equals("CallSpExcecutionResponse{}") || soapResponse == null)
                return arrRiwayat;
            else{
                String callSpExcecutionResponse = soapResponse.getPropertyAsString("CallSpExcecutionResult");
                JSONArray jArray = new JSONArray(callSpExcecutionResponse);

                for (int i = 0; i < jArray.length(); i++){
                    Riwayat riwayat = new Riwayat();

                    JSONObject datariwayat = jArray.getJSONObject(i);
                    riwayat.setStatus(datariwayat.getString("intStatus"));
                    riwayat.setNamaRs(datariwayat.getString("txtPartnerName"));
                    riwayat.setTanggalAntri(datariwayat.getString("dtAntrian"));
                    riwayat.setNamaLoket(datariwayat.getString("txtLoket"));
                    riwayat.setNoALnow(datariwayat.getString("txtNoAntrianLoketSaatIni"));
                    riwayat.setNoAntrianLoket(datariwayat.getString("txtNoAntrianLoket"));
                    riwayat.setNoAntrianPoli(datariwayat.getString("txtNoAntrianPoli"));
                    riwayat.setJenisPelayanan(datariwayat.getString("txtJenisPelayanan"));
                    riwayat.setNoPnow(datariwayat.getString("txtNoAntrianPoliSaatIni"));
                    arrRiwayat.add(riwayat);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrRiwayat;
    }
}
