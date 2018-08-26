package com.chocobar.fuutaro.medicare.AsyncTasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import com.chocobar.fuutaro.medicare.AsyncTasks.core.AsyncTaskActivity;
import com.chocobar.fuutaro.medicare.adapter.AdapterDokterSchedule;
import com.chocobar.fuutaro.medicare.STATIC_VALUES;
import com.chocobar.fuutaro.medicare.model.DokterSchedule;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class ViewDokterSchedule extends AsyncTask<String, Void, ArrayList<DokterSchedule>> {
    //initiate some objects
    private RecyclerView rViewSchedule;
    private AdapterDokterSchedule adapterSchedule;
    private Activity activity;

    //declare ArrayList
    ArrayList<DokterSchedule> arrSchedule = new ArrayList<>();

    //declare constructor
    public ViewDokterSchedule(Activity activity, RecyclerView rViewSchedule, AdapterDokterSchedule adapterSchedule){
        this.activity = activity;
        this.rViewSchedule = rViewSchedule;
        this.adapterSchedule = adapterSchedule;
    }

    @Override
    protected void onPostExecute(ArrayList<DokterSchedule> dokterSchedules) {
        rViewSchedule.addItemDecoration(new DividerItemDecoration(this.activity, DividerItemDecoration.VERTICAL));
        adapterSchedule = new AdapterDokterSchedule(dokterSchedules, this.activity);
        rViewSchedule.setAdapter(adapterSchedule);
        adapterSchedule.notifyDataSetChanged();
    }

    @Override
    protected ArrayList<DokterSchedule> doInBackground(String... strings) {
        List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_DokterGetJadwalPraktek",
                "intIDDokter#"+ strings[0] + "~intDay#"+ strings[1] +"~dtAntrian#" + strings[2]);
        SoapSerializationEnvelope env = (SoapSerializationEnvelope)dataReceived.get(0);
        HttpTransportSE httpTrans = (HttpTransportSE)dataReceived.get(1);

        try{
            httpTrans.call(STATIC_VALUES.SOAP_ACTION, env);
            SoapObject soapResponse = (SoapObject)env.bodyIn;

            if(soapResponse.toString().equals("CallSpExcecutionResponse{}") || soapResponse == null)
                return arrSchedule;
            else{
                String callSpExcecutionResponse = soapResponse.getPropertyAsString("CallSpExcecutionResult");
                JSONArray jArray = new JSONArray(callSpExcecutionResponse);

                for (int i = 0; i < jArray.length(); i++){
                    DokterSchedule schedule = new DokterSchedule();

                    JSONObject dataSchedule = jArray.getJSONObject(i);
                    schedule.setIdPartner(dataSchedule.getInt("intIDPartner"));
                    schedule.setIdJenisPelayanan(dataSchedule.getInt("intIDJenisPelayanan"));
                    schedule.setIdJadwal(dataSchedule.getInt("intIDJadwalPraktek"));
                    schedule.setPartner(dataSchedule.getString("txtPartnerName"));
                    schedule.setJenisPelayanan(dataSchedule.getString("txtJenisPelayanan"));
                    schedule.setStartAt(dataSchedule.getString("dtJamMulai"));
                    schedule.setEndAt(dataSchedule.getString("dtJamSelesai"));
                    schedule.setKuota(dataSchedule.getInt("intKuota"));
                    schedule.setJumlahAntrian(dataSchedule.getInt("intJumlahAntrian"));
                    arrSchedule.add(schedule);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrSchedule;
    }
}
