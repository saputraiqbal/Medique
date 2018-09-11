package com.chocobar.fuutaro.medicare.AsyncTasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.chocobar.fuutaro.medicare.AsyncTasks.core.AsyncTaskActivity;
import com.chocobar.fuutaro.medicare.STATIC_VALUES;
import com.chocobar.fuutaro.medicare.adapter.AdapterDokterSchedule;
import com.chocobar.fuutaro.medicare.adapter.AdapterFaskesCategory;
import com.chocobar.fuutaro.medicare.adapter.AdapterFaskesSchedule;
import com.chocobar.fuutaro.medicare.model.DokterSchedule;
import com.chocobar.fuutaro.medicare.model.FaskesSchedule;
import com.chocobar.fuutaro.medicare.model.KategoriFaskes;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class ViewFaskesSchedule extends AsyncTask<String, Void, ArrayList<FaskesSchedule>> {
    //initiate some objects
    private RecyclerView rViewSchedule;
    private AdapterFaskesCategory adapterSchedule;
    private Activity activity;

    //declare ArrayList
    ArrayList<FaskesSchedule> arrSchedule = new ArrayList<>();
    ArrayList<KategoriFaskes> arrKategori = new ArrayList<>();
    ArrayList<Object> arrObject;

    //declare constructor
    public ViewFaskesSchedule(Activity activity, RecyclerView rViewSchedule, AdapterFaskesSchedule adapterSchedule){
        this.activity = activity;
        this.rViewSchedule = rViewSchedule;
        this.arrObject = new ArrayList<>();
    }

    @Override
    protected void onPostExecute(ArrayList<FaskesSchedule> faskesSchedules) {
        rViewSchedule.addItemDecoration(new DividerItemDecoration(this.activity, DividerItemDecoration.VERTICAL));
        arrObject.add(arrKategori);
        arrObject.add(arrSchedule);
        adapterSchedule = new AdapterFaskesCategory(arrObject, this.activity);
        rViewSchedule.setAdapter(adapterSchedule);
        adapterSchedule.notifyDataSetChanged();
    }

    @Override
    protected ArrayList<FaskesSchedule> doInBackground(String... strings) {
        List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_FaskesGetJadwalPraktek",
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
                    FaskesSchedule schedule = new FaskesSchedule();
                    KategoriFaskes kategori = new KategoriFaskes();

                    JSONObject dataSchedule = jArray.getJSONObject(i);
                    schedule.setIdPartner(dataSchedule.getInt("intIDPartner"));
                    schedule.setIdJenisPelayanan(dataSchedule.getInt("intIDJenisPelayanan"));
                    schedule.setIdJadwal(dataSchedule.getInt("intIDJadwalPraktek"));
                    schedule.setNamaPegawai(dataSchedule.getString("txtNamaPegawai"));
                    schedule.setJenisPelayanan(dataSchedule.getString("txtJenisPelayanan"));
                    schedule.setStartAt(dataSchedule.getString("dtJamMulai"));
                    schedule.setEndAt(dataSchedule.getString("dtJamSelesai"));
                    schedule.setKuota(dataSchedule.getInt("intKuota"));
                    schedule.setJumlahAntrian(dataSchedule.getInt("intJumlahAntrian"));
                    String kategoriFaskes = dataSchedule.getString("txtJenisPelayanan");
                    if (arrKategori.isEmpty()){
                        kategori.setKategori(kategoriFaskes);
                        arrKategori.add(kategori);
                    }else{
                        boolean isCategoySame = false;
                        for(int j = 0; j < arrKategori.size(); j++){
                            if(arrKategori.get(j).getKategori().equals(kategoriFaskes)){
                                isCategoySame = true;
                                break;
                            }else
                                isCategoySame = false;
                        }
                        if(!isCategoySame){
                            kategori.setKategori(kategoriFaskes);
                            arrKategori.add(kategori);
                        }
                    }
                    arrSchedule.add(schedule);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrSchedule;
    }
}
