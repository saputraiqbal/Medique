package com.chocobar.fuutaro.medicare;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chocobar.fuutaro.medicare.AsyncTaskPopulateData.ViewDetailDokter;
import com.chocobar.fuutaro.medicare.model.DetailDokter;
import com.chocobar.fuutaro.medicare.model.DokterSchedule;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DetailDokterActivity extends AppCompatActivity {
    public static TextView namaDokter, linkProfile, tglDaftar, linkUbahTgl;
    public static ImageView imgShowDokter;
    public static RecyclerView rViewSchedule;
    ArrayList<DokterSchedule>arrSchedule = new ArrayList<>();
    AdapterDataSchedule adapterSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dokter);

        imgShowDokter = findViewById(R.id.imgViewDokter);
        namaDokter = findViewById(R.id.txtViewDokterName);
        linkProfile = findViewById(R.id.txtViewDokterDetail);
        tglDaftar = findViewById(R.id.txtViewDayReserve);
        linkUbahTgl = findViewById(R.id.txtViewChangeDate);
        rViewSchedule = findViewById(R.id.rViewShowDokterSchedule);

        new ViewDetailDokter(DetailDokterActivity.this).execute(0);
        tglDaftar.setText(getString(R.string.set_date_reserved) + " " + setDate());

        rViewSchedule.setLayoutManager(new LinearLayoutManager(this));
        Date dayOrder = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        String stringDay = format.format(dayOrder);
        new ViewSchedule().execute("1", "6", stringDay);
    }

    protected String setDate(){
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
        return format.format(today);
    }

    class ViewSchedule extends AsyncTask<String, Void, ArrayList<DokterSchedule>>{
        @Override
        protected void onPostExecute(ArrayList<DokterSchedule> dokterSchedules) {
            adapterSchedule = new AdapterDataSchedule(dokterSchedules, DetailDokterActivity.this);
            rViewSchedule.setAdapter(adapterSchedule);
            adapterSchedule.notifyDataSetChanged();
        }

        @Override
        protected ArrayList<DokterSchedule> doInBackground(String... strings) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.ENGLISH);
            String parseDate = strings[2];
            Date myDate = null;
            try {
                myDate = format.parse(parseDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_DokterGetJadwalPraktek",
                    "intIDDokter#1~intDay#6~dtAntrian#2018-07-28 00:00:00");
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

}
