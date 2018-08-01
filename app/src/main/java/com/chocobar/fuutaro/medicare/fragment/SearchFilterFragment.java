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
import com.chocobar.fuutaro.medicare.AsyncTasks.Search;
import com.chocobar.fuutaro.medicare.activity.MainActivity;
import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.model.Kota;
import com.chocobar.fuutaro.medicare.model.Spesialis;

import java.util.ArrayList;

public class SearchFilterFragment extends DialogFragment {
    private Spinner spinnerKota, spinnerSpesialis;
    private RadioGroup rGroupGender;
    private Button getSearchFilter;
    private int chooseGender = 0;

    private ArrayList<Kota> arrListKota = new ArrayList<>();
    private ArrayList<Spesialis> arrListSpesialis = new ArrayList<>();

    public SearchFilterFragment() {
    }

    public static SearchFilterFragment newInstance (){
        SearchFilterFragment searchFrag = new SearchFilterFragment();
        Bundle args = new Bundle();
        searchFrag.setArguments(args);
        return searchFrag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        spinnerKota = viewFragment.findViewById(R.id.spinnerKota);
//        spinnerSpesialis = viewFragment.findViewById(R.id.spinnerSpesialis);
//        rGroupGender = viewFragment.findViewById(R.id.radioGroup);
//        getSearchFilter = viewFragment.findViewById(R.id.btnSearchFilter);
//
//        new PopulateSpinnerKota(getContext(), spinnerKota, new PopulateSpinnerKota.OnFinishedPopulate(){
//            @Override
//            public void onFinishedPopulate(ArrayList<Kota> dataKota) {
//                arrListKota = new ArrayList<>(dataKota);
//            }
//        }).execute();
//        new PopulateSpinnerSpesialis(getContext(), spinnerSpesialis, new PopulateSpinnerSpesialis.OnFinishedPopulate() {
//            @Override
//            public void OnFinishedPopulate(ArrayList<Spesialis> dataSpesialis) {
//                arrListSpesialis = new ArrayList<>(dataSpesialis);
//            }
//        }).execute();
//
//        RadioButton rBtnDefault = viewFragment.findViewById(R.id.radioAllGender);
//        rBtnDefault.setChecked(true);
//        rGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch(checkedId){
//                    case (R.id.radioAllGender) :
//                        chooseGender = 0;
//                        break;
//                    case (R.id.radioGenderMale) :
//                        chooseGender = 1;
//                        break;
//                    case (R.id.radioGenderFemale) :
//                        chooseGender = 2;
//                        break;
//                };
//            }
//        });
        return inflater.inflate(R.layout.fragment_search_filter, container);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinnerKota = view.findViewById(R.id.spinnerKota);
        spinnerSpesialis = view.findViewById(R.id.spinnerSpesialis);
        rGroupGender = view.findViewById(R.id.radioGroup);
        getSearchFilter = view.findViewById(R.id.btnSearchFilter);

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
        getDialog().setTitle(null);
    }

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
                new Search(new MainActivity()).execute("", valKota, valSpesialis, valGender);
                getDialog().dismiss();
            }
        });
    }

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

    private String chooseSpesialisVal(String valChosen){
        String val = "";
        if(valChosen.equals("Semua spesialis")){
            return val = "0";
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