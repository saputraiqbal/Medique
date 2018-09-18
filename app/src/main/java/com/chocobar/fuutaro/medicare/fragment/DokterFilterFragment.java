package com.chocobar.fuutaro.medicare.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.chocobar.fuutaro.medicare.AsyncTasks.PopulateSpinnerKota;
import com.chocobar.fuutaro.medicare.AsyncTasks.PopulateSpinnerSpesialis;
import com.chocobar.fuutaro.medicare.AsyncTasks.SearchDokter;
import com.chocobar.fuutaro.medicare.AsyncTasks.Top20Dokter;
import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.adapter.AdapterDokter;
import com.chocobar.fuutaro.medicare.model.Kota;
import com.chocobar.fuutaro.medicare.model.Spesialis;

import java.util.ArrayList;

public class DokterFilterFragment extends DialogFragment {
    //initiate widget objects
    private Spinner spinnerKota, spinnerSpesialis;
    private RadioGroup rGroupGender;
    private Button getSearchFilter;

    //initiate some other objects
    private int chooseGender = 0;
    private ArrayList<Kota> arrListKota = new ArrayList<>();
    private ArrayList<Spesialis> arrListSpesialis = new ArrayList<>();

    //declare default constructor
    public DokterFilterFragment() {
    }

    public static DokterFilterFragment newInstance(){
        DokterFilterFragment dokterFilterFrag = new DokterFilterFragment();
        return dokterFilterFrag;
    }

    //declare onCreateView to create view of DialogFragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dokter_filter, container, false);
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
        spinnerSpesialis = view.findViewById(R.id.spinnerSpesialis);
        rGroupGender = view.findViewById(R.id.radioGroup);
        getSearchFilter = view.findViewById(R.id.btnSearchFilter);

        //populate spinner using AsyncTask
        new PopulateSpinnerKota(getContext(), spinnerKota, new PopulateSpinnerKota.OnFinishedPopulate(){
            @Override
            public void onFinishedPopulate(ArrayList<Kota> dataKota) {
                arrListKota = new ArrayList<>(dataKota);
            }
        }).execute();
        new PopulateSpinnerSpesialis(getContext(), spinnerSpesialis, new PopulateSpinnerSpesialis.OnFinishedPopulate() {
            @Override
            public void OnFinishedPopulate(ArrayList<Spesialis> dataSpesialis) {
                arrListSpesialis = new ArrayList<>(dataSpesialis);
            }
        }).execute();

        //declare RadioButton, set default checked value, and  set value when one of choices has been chosen
        RadioButton rBtnDefault = view.findViewById(R.id.radioAllGender);
        rBtnDefault.setChecked(true);
        rGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case (R.id.radioAllGender) :
                        chooseGender = 0;
                        break;
                    case (R.id.radioGenderMale) :
                        chooseGender = 1;
                        break;
                    case (R.id.radioGenderFemale) :
                        chooseGender = 2;
                        break;
                };
            }
        });
        //set dialog title become null (hiding)
        getDialog().setTitle(null);
    }

    //declared when activity of fragment has been created
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSearchFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valKota = spinnerKota.getSelectedItem().toString();
                String valSpesialis = spinnerSpesialis.getSelectedItem().toString();
                String valGender = Integer.toString(chooseGender);
                valKota = chooseKotaVal(valKota);
                valSpesialis = chooseSpesialisVal(valSpesialis);
                new SearchDokter(getActivity()).execute("", valKota, valSpesialis, valGender);
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
    private String chooseSpesialisVal(String valChosen){
        String val = "";
        if(valChosen.equals("Semua spesialis")){
            return "0";
        }
        else{
            for (Spesialis arrList : arrListSpesialis){
                if(arrList.getSpesialis().equals(valChosen)){
                    val = Integer.toString(arrList.getIdSpesialis());
                    break;
                }
            }
        }
        return val;
    }
}