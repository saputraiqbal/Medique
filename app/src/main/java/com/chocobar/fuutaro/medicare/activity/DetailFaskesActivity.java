package com.chocobar.fuutaro.medicare.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chocobar.fuutaro.medicare.AsyncTasks.ViewDetailFaskes;
import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.adapter.AdapterDokterSchedule;
import com.chocobar.fuutaro.medicare.model.DetailFaskes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DetailFaskesActivity extends AppCompatActivity{
    //initate widget objects
    public static TextView namaFaskes, tglDaftar, linkUbahTgl, profile, seeProfile, txtAlamat, txtPelayanan;
    public static ImageView imgShowFaskes;
    public static RecyclerView rViewSchedule;
    private View beginDiv, endDiv;

    //initiate variable
    private String idPartner, alamat, pelayanan;
    private boolean isProfileShown;

    //initiate DatePickerDialog for showing DatePicker dialog
    private DatePickerDialog.OnDateSetListener mDateListener;

    //initiate ArrayLists
//    ArrayList<DokterSchedule>arrSchedule = new ArrayList<>();
    ArrayList<DetailFaskes> arrDetail = new ArrayList<>();

    //initiate adapter
    public static AdapterDokterSchedule adapterSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profil Faskes");

        /**initiate Bundle for receiving data from AdapterDokter applied at MainActivity_recs
           and store it to idPartner variable**/
        Bundle getBundle = getIntent().getExtras();
        idPartner = getBundle.getString("setIdPartner");
        alamat = getBundle.getString("setAlamat");
        pelayanan = getBundle.getString("setPelayanan");

        //connecting widget objects with widget layout
        imgShowFaskes = findViewById(R.id.imgProfile);
        namaFaskes = findViewById(R.id.txtProfileName);
        txtAlamat = findViewById(R.id.txtInfo1);
        txtPelayanan = findViewById(R.id.txtInfo2);
        tglDaftar = findViewById(R.id.txtViewDayReserve);
        linkUbahTgl = findViewById(R.id.txtViewChangeDate);
        rViewSchedule = findViewById(R.id.rViewShowDokterSchedule);
        beginDiv = findViewById(R.id.profileDivStart);
        endDiv = findViewById(R.id.profileDivEnd);
        profile = findViewById(R.id.txtProfile);
        seeProfile = findViewById(R.id.txtSeeDetail);

        namaFaskes.setSelected(true);
        txtAlamat.setText(alamat);
        txtAlamat.setSelected(true);
        txtPelayanan.setText(pelayanan);
        txtPelayanan.setSelected(true);

        isProfileShown = false;

        //call ViewDetailDokter AsyncTask to get data that will show at this activity
        new ViewDetailFaskes(DetailFaskesActivity.this).execute(idPartner);

        //set text
        tglDaftar.setText(getString(R.string.set_date_reserved) + " " + setDateToday().get(0));

        //set RecyclerView and populate it using ViewSchedule AsynTask
//        rViewSchedule.setLayoutManager(new LinearLayoutManager(this));
//        new ViewSchedule(DetailFaskesActivity.this, rViewSchedule, adapterSchedule).execute(idPartner, setDateToday().get(1), setDateToday().get(2));

        seeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isProfileShown){
                    profile.setMaxLines(Integer.MAX_VALUE);
                    endDiv.setVisibility(View.VISIBLE);
                    seeProfile.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_menu_up, 0);
                    isProfileShown = true;
                }else{
                    profile.setMaxLines(0);
                    endDiv.setVisibility(View.GONE);
                    seeProfile.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_menu_down, 0);
                    isProfileShown = false;
                }
            }
        });

        //set action when linkUbahTanggal is clicked
//        linkUbahTgl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //set Calendar
//                Calendar calDateOrder = Calendar.getInstance();
//                int year = calDateOrder.get(Calendar.YEAR);
//                int month = calDateOrder.get(Calendar.MONTH);
//                int date = calDateOrder.get(Calendar.DAY_OF_MONTH);
//
//                //showing DatePicker dialog
//                DatePickerDialog dialog = new DatePickerDialog(
//                        DetailFaskesActivity.this,
//                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateListener, year, month, date);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.setTitle("Ubah tanggal pemesanan layanan");
//                dialog.show();
//            }
//        });

        //apply change after set data at DatePicker dialog
//        mDateListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                tglDaftar.setText(getString(R.string.set_date_reserved) + " " + changeDate(year, month, dayOfMonth).get(0));
//                //clear teh ArrayList that used for RecyclerView
//                arrSchedule.clear();
//                new ViewSchedule(DetailFaskesActivity.this, rViewSchedule, adapterSchedule).execute("1", changeDate(year, month, dayOfMonth).get(1), changeDate(year, month, dayOfMonth).get(2));
//            }
//        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }

    //method for set ArrayList<String> changeDate that will used when date is changed by user
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

    //method for set ArrayList<String> setDateToday that will used when date is generated by system
    private ArrayList<String> setDateToday(){
        ArrayList<String> arrDate = new ArrayList<>();
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatView = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat formatSend = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar dayCal = Calendar.getInstance();
        int intToday = dayCal.get(dayCal.DAY_OF_WEEK);
        arrDate.add(formatView.format(today));
        arrDate.add(Integer.toString(intToday));
        arrDate.add(formatSend.format(today));
        return arrDate;
    }
}
