package com.chocobar.fuutaro.medicare.activity;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.chocobar.fuutaro.medicare.AsyncTasks.ViewDetailDokter;
import com.chocobar.fuutaro.medicare.AsyncTasks.ViewSchedule;
import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.adapter.AdapterDataSchedule;
import com.chocobar.fuutaro.medicare.fragment.ViewProfileDokterFragment;
import com.chocobar.fuutaro.medicare.model.DetailDokter;
import com.chocobar.fuutaro.medicare.model.DokterSchedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DetailDokterActivity extends AppCompatActivity {
    //initate widget objects
    public static TextView namaDokter, linkProfile, tglDaftar, linkUbahTgl;
    public static ImageView imgShowDokter;
    public static RecyclerView rViewSchedule;

    //initiate variable
    private String idDokter;

    //initiate DatePickerDialog for showing DatePicker dialog
    private DatePickerDialog.OnDateSetListener mDateListener;

    //initiate ArrayLists
    ArrayList<DokterSchedule>arrSchedule = new ArrayList<>();
    ArrayList<DetailDokter> arrDetail = new ArrayList<>();

    //initiate adapter
    public static AdapterDataSchedule adapterSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dokter);

        /**initiate Bundle for receiving data from AdapterDataSearch applied at MainActivity
           and store it to idDokter variable**/
        Bundle getBundle = getIntent().getExtras();
        idDokter = getBundle.getString("setIdDokter");

        //connecting widget objects with widget layout
        imgShowDokter = findViewById(R.id.imgViewDokter);
        namaDokter = findViewById(R.id.txtViewDokterName);
        linkProfile = findViewById(R.id.txtViewDokterDetail);
        tglDaftar = findViewById(R.id.txtViewDayReserve);
        linkUbahTgl = findViewById(R.id.txtViewChangeDate);
        rViewSchedule = findViewById(R.id.rViewShowDokterSchedule);

        //call ViewDetailDokter AsyncTask to get data that will show at this activity
        new ViewDetailDokter(DetailDokterActivity.this, new ViewDetailDokter.OnProfileSet() {
            @Override
            public void onProfileSet(ArrayList<DetailDokter> arrDetailDokters) {
                arrDetail = new ArrayList<>(arrDetailDokters);
            }
        }).execute(idDokter);
        //set text
        tglDaftar.setText(getString(R.string.set_date_reserved) + " " + setDateToday().get(0));

        //set RecyclerView and populate it using ViewSchedule AsynTask
        rViewSchedule.setLayoutManager(new LinearLayoutManager(this));
        new ViewSchedule(DetailDokterActivity.this, rViewSchedule, adapterSchedule).execute(idDokter, setDateToday().get(1), setDateToday().get(2));

        //set action when linkProfile is clicked
        linkProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling ViewProfileDokterFragment to make a DialogFragment
                DetailDokterActivity activity = (DetailDokterActivity) v.getContext();
                ViewProfileDokterFragment profileFrag = ViewProfileDokterFragment.newInstance(
                        arrDetail.get(0).getImgBase64(), arrDetail.get(0).getNama(), arrDetail.get(0).getProfileDetail());
                profileFrag.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
                profileFrag.show(activity.getFragmentManager(), "fragment_dokter_profile");
            }
        });

        //set action when linkUbahTanggal is clicked
        linkUbahTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set Calendar
                Calendar calDateOrder = Calendar.getInstance();
                int year = calDateOrder.get(Calendar.YEAR);
                int month = calDateOrder.get(Calendar.MONTH);
                int date = calDateOrder.get(Calendar.DAY_OF_MONTH);

                //showing DatePicker dialog
                DatePickerDialog dialog = new DatePickerDialog(
                        DetailDokterActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateListener, year, month, date);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setTitle("Ubah tanggal pemesanan layanan");
                dialog.show();
            }
        });

        //apply change after set data at DatePicker dialog
        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tglDaftar.setText(getString(R.string.set_date_reserved) + " " + changeDate(year, month, dayOfMonth).get(0));
                //clear teh ArrayList that used for RecyclerView
                arrSchedule.clear();
                new ViewSchedule(DetailDokterActivity.this, rViewSchedule, adapterSchedule).execute("1", changeDate(year, month, dayOfMonth).get(1), changeDate(year, month, dayOfMonth).get(2));
            }
        };
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
