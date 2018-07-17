package com.chocobar.fuutaro.medicare;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.chocobar.fuutaro.medicare.AsyncTaskPopulateData.PopulateSpinnerKota;
import com.chocobar.fuutaro.medicare.AsyncTaskPopulateData.PopulateSpinnerSpesialis;
import com.chocobar.fuutaro.medicare.AsyncTaskPopulateData.Search;
import com.chocobar.fuutaro.medicare.model.Kota;
import com.chocobar.fuutaro.medicare.model.Spesialis;

import java.util.ArrayList;

public class SearchFilterFragment extends Fragment {
    private Spinner spinnerKota, spinnerSpesialis;
    private RadioGroup rGroupGender;
    private Button getSearchFilter;
    private int chooseKota = 0, chooseSpesialis = 0, chooseGender = -1;

    private ArrayList<Kota> arrListKota = new ArrayList<>();
    private ArrayList<Spesialis> arrListSpesialis = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewFragment = inflater.inflate(R.layout.fragment_search_filter, container, false);
        viewFragment.setBackgroundColor(Color.WHITE);

        spinnerKota = viewFragment.findViewById(R.id.spinnerKota);
        spinnerSpesialis = viewFragment.findViewById(R.id.spinnerSpesialis);
        rGroupGender = viewFragment.findViewById(R.id.radioGroup);
        getSearchFilter = viewFragment.findViewById(R.id.btnSearchFilter);

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
        return viewFragment;
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
                MainActivity.rView.setAdapter(MainActivity.adapter);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if(fm.getBackStackEntryCount() > 0)
                    fm.popBackStackImmediate();
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