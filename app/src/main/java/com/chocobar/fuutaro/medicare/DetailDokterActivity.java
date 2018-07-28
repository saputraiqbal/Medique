package com.chocobar.fuutaro.medicare;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.chocobar.fuutaro.medicare.AsyncTaskPopulateData.ViewDetailDokter;
import com.chocobar.fuutaro.medicare.model.DetailDokter;
import com.chocobar.fuutaro.medicare.model.DokterSchedule;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


public class DetailDokterActivity extends AppCompatActivity {
    public static TextView namaDokter, linkProfile, tglDaftar, linkUbahTgl;
    public static ImageView imgShowDokter;
    public static RecyclerView rViewSchedule;

    private DatePickerDialog.OnDateSetListener mDateListener;

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
        tglDaftar.setText(getString(R.string.set_date_reserved) + " " + setDateToday().get(0));

        rViewSchedule.setLayoutManager(new LinearLayoutManager(this));
        new ViewSchedule().execute("1", "6", setDateToday().get(1));

        linkUbahTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calDateOrder = Calendar.getInstance();
                int year = calDateOrder.get(Calendar.YEAR);
                int month = calDateOrder.get(Calendar.MONTH);
                int date = calDateOrder.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        DetailDokterActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateListener, year, month, date);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tglDaftar.setText(getString(R.string.set_date_reserved) + " " + changeDate(year, month, dayOfMonth).get(0));
                arrSchedule.clear();
                new ViewSchedule().execute("1", changeDate(year, month, dayOfMonth).get(1), changeDate(year, month, dayOfMonth).get(2));
            }
        };
    }

    private static ArrayList<String> changeDate(int year, int month, int dayOfMonth){
        ArrayList<String> arrDateSet = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, dayOfMonth);
        Date dateSet = cal.getTime();
        SimpleDateFormat formatView = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat formatSend = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        GregorianCalendar dayCal = new GregorianCalendar(year, month, dayOfMonth);
        int dayOfWeek = dayCal.get(dayCal.DAY_OF_WEEK);
        arrDateSet.add(formatView.format(dateSet));
        arrDateSet.add(Integer.toString(dayOfWeek));
        arrDateSet.add(formatSend.format(dateSet));
        return arrDateSet;
    }

    private ArrayList<String> setDateToday(){
        ArrayList<String> arrDate = new ArrayList<>();
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatView = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat formatSend = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        arrDate.add(formatView.format(today));
        arrDate.add(formatSend.format(today));
        return arrDate;
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
