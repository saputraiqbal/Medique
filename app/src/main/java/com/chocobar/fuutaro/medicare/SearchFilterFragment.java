package com.chocobar.fuutaro.medicare;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.TaskStackBuilder;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

        new PopulateSpinnerKota(getContext(), spinnerKota, new PopulateSpinnerKota.OnPopulateSpinnerFinished(){
            @Override
            public void onPopulateSpinnerFinished(ArrayList<Kota> dataKota) {
                arrListKota = new ArrayList<>(dataKota);
            }
        }).execute();


        new PopulateSpinnerSpesialis(getContext(), spinnerSpesialis).execute();

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
                String val = spinnerKota.getSelectedItem().toString();
                val = chooseSpinnerVal(val);
                Toast.makeText(getContext(), "Choose " + val, Toast.LENGTH_SHORT).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if(fm.getBackStackEntryCount() > 0)
                    fm.popBackStackImmediate();
            }
        });
    }

    private String chooseSpinnerVal(String valChosen){
        String v = null;
        for (Kota arrList : arrListKota){
            if(arrList.getNamaKota().equals(valChosen)){
                v = Integer.toString(arrList.getIdKota());
                break;
            }
        }
        return v;
    }

}