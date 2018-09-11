package com.chocobar.fuutaro.medicare.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


import com.chocobar.fuutaro.medicare.AsyncTasks.PopulateSpinnerJakes;
import com.chocobar.fuutaro.medicare.AsyncTasks.PopulateSpinnerKlinik;
import com.chocobar.fuutaro.medicare.AsyncTasks.PopulateSpinnerKota;
import com.chocobar.fuutaro.medicare.AsyncTasks.PopulateSpinnerSpesialis;
import com.chocobar.fuutaro.medicare.AsyncTasks.SearchFaskes;
import com.chocobar.fuutaro.medicare.AsyncTasks.Top20Faskes;
import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.adapter.AdapterFaskes;
import com.chocobar.fuutaro.medicare.model.Jakes;
import com.chocobar.fuutaro.medicare.model.Kota;
import com.chocobar.fuutaro.medicare.model.Klinik;
import com.chocobar.fuutaro.medicare.model.Spesialis;

import java.util.ArrayList;

public class FaskesFilterFragment extends DialogFragment {
    //initiate widget objects
    private Spinner spinnerKota, spinnerKlinik, spinnerJakes;
    private RadioGroup rGroupJakes;
    private Button getSearchFilter;
    private String[] jakes = new String[10];

    private CheckBox cb_bpjs, cb_gratis, cb_askes, cb_jkn, cb_axa, cb_prudential;

    //initiate some other objects
    private int v_cb_bpjs=0, v_cb_gratis=0, v_cb_askes=0, v_cb_jkn=0, v_cb_axa=0, v_cb_prudential=0;
    private ArrayList<Kota> arrListKota = new ArrayList<>();
    private ArrayList<Klinik> arrListKlinik = new ArrayList<>();
    private ArrayList<Jakes> arrListJakes = new ArrayList<>();
    AdapterFaskes adapter;

    //declare default constructor
    public FaskesFilterFragment() {
    }

    public static FaskesFilterFragment newInstance(){
        FaskesFilterFragment faskesFilterFrag = new FaskesFilterFragment();
        return faskesFilterFrag;
    }

    //declare onCreateView to create view of DialogFragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_faskes_filter, container, false);

    }

    //declare onResume
    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    //declare onViewCreated to view widgets after DialogFragment has been created
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinnerKota = view.findViewById(R.id.spinnerKota);
        spinnerKlinik = view.findViewById(R.id.spinnerKlinik);
        spinnerJakes = view.findViewById(R.id.spinnerJakes);
        getSearchFilter = view.findViewById(R.id.btnSearchFilter);

        //populate spinner using AsyncTask
        new PopulateSpinnerKota(getContext(), spinnerKota, new PopulateSpinnerKota.OnFinishedPopulate() {
            @Override
            public void onFinishedPopulate(ArrayList<Kota> dataKota) {
                arrListKota = new ArrayList<>(dataKota);
            }
        }).execute();
        new PopulateSpinnerKlinik(getContext(), spinnerKlinik, new PopulateSpinnerKlinik.OnFinishedPopulate() {
            @Override
            public void onFinishedPopulate(ArrayList<Klinik> dataKlinik) {
                arrListKlinik = new ArrayList<>(dataKlinik);
            }
        }).execute();

        new PopulateSpinnerJakes(getContext(), spinnerJakes, new PopulateSpinnerJakes.OnFinishedPopulate() {
            @Override
            public void onFinishedPopulate(ArrayList<Jakes> dataJakes) {
                arrListJakes = new ArrayList<>(dataJakes);
            }
        }).execute();

        getDialog().setTitle(null);
    }

    //declared when activity of fragment has been created
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        cb_bpjs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked) {
//                    v_cb_bpjs = 1;
//                    Toast.makeText(getActivity(), "This is my Toast message!", Toast.LENGTH_LONG).show();
//                }
//                else{
//                    v_cb_bpjs = 0;}
//            }
//        });
//        cb_gratis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked){
//                    v_cb_gratis = 1;}
//                else{
//                    v_cb_gratis = 0;}
//            }
//        });
//        cb_askes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked)
//                    v_cb_askes = 1;
//                else
//                    v_cb_askes = 0;
//            }
//        });
//        cb_jkn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked)
//                    v_cb_jkn = 1;
//                else
//                    v_cb_jkn = 0;
//            }
//        });cb_axa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked)
//                    v_cb_axa = 1;
//                else
//                    v_cb_axa = 0;
//            }
//        });
//        cb_prudential.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked)
//                    v_cb_prudential = 1;
//                else
//                    v_cb_prudential = 0;
//            }
//        });

        getSearchFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valKota = spinnerKota.getSelectedItem().toString();
                String valKlinik = spinnerKlinik.getSelectedItem().toString();
                String valJakes = spinnerJakes.getSelectedItem().toString();


                //Change value from int to string
                String val_bpjs = Integer.toString(v_cb_bpjs);
                String val_gratis = Integer.toString(v_cb_gratis);
                String val_askes = Integer.toString(v_cb_askes);
                String val_jkn = Integer.toString(v_cb_jkn);
                String val_axa = Integer.toString(v_cb_axa);
                String val_prudential = Integer.toString(v_cb_prudential);

                valKota = chooseKotaVal(valKota);
                valKlinik = chooseKlinikVal(valKlinik);
                valJakes = chooseJakesVal(valJakes);
                int angJakes = Integer.parseInt(valJakes);

                for(int i = 0; i < jakes.length; i++){
                    jakes[i] = "0";
                }
                if(angJakes!=0){
                    jakes[angJakes]="1";
                }

                new SearchFaskes(getActivity()).execute("", valKota, valKlinik, jakes[0], jakes[1], jakes[2], jakes[3], jakes[4], jakes[5], jakes[6],jakes[7],jakes[8],jakes[9]);
                getDialog().dismiss();
            }
        });
    }
    //declare to choose value chosen from spinner and store it to String variable type
    private String chooseKotaVal(String valChosen){
        String val = "";
        if(valChosen.equals("Semua kota")){
            return val = "0";
        }
        else{
            for (Kota arrList : arrListKota){
                if(arrList.getNamaKota().equals(valChosen)){
                    val = Integer.toString(arrList.getIdKota());
                    break;
                }
            }
        }
        return val;
    }
    //declare to choose value chosen from spinner and store it to String variable type
    private String chooseKlinikVal(String valChosen){
        String val = "";
        if(valChosen.equals("Semua klinik")){
            //Toast.makeText(getActivity(), "klinik test!", Toast.LENGTH_LONG).show();
            return val = "0";
        }
        else{
            for (Klinik arrList : arrListKlinik){
                if(arrList.getNamaKlinik().equals(valChosen)){
                    val = Integer.toString(arrList.getIdKlinik());
                    break;
                }
            }
        }
        return val;
    }
    private String chooseJakesVal(String valChosen){
        String val = "";
        if(valChosen.equals("Semua jaminan kesehatan")){
            //Toast.makeText(getActivity(), "klinik test!", Toast.LENGTH_LONG).show();
            return val = "0";
        }
        else{
            for (Jakes arrList : arrListJakes){
                if(arrList.getNamaJakes().equals(valChosen)){
                    val = Integer.toString(arrList.getIdJakes());

                    break;
                }
            }
        }
        return val;
    }
}
