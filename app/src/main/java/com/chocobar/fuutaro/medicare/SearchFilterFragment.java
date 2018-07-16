package com.chocobar.fuutaro.medicare;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.chocobar.fuutaro.medicare.AsyncTaskPopulateData.PopulateSpinnerKota;
import com.chocobar.fuutaro.medicare.AsyncTaskPopulateData.PopulateSpinnerSpesialis;

public class SearchFilterFragment extends Fragment {
    private Spinner spinnerKota, spinnerSpesialis;
    private RadioGroup rGroupGender;
    private Button getSearchFilter;
    private int chooseKota = 0, chooseSpesialis = 0, chooseGender = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewFragment = inflater.inflate(R.layout.fragment_search_filter, container, false);
        viewFragment.setBackgroundColor(Color.WHITE);

        spinnerKota = viewFragment.findViewById(R.id.spinnerKota);
        spinnerSpesialis = viewFragment.findViewById(R.id.spinnerSpesialis);
        rGroupGender = viewFragment.findViewById(R.id.radioGroup);
        getSearchFilter = viewFragment.findViewById(R.id.btnSearchFilter);

        new PopulateSpinnerKota(getContext(), spinnerKota).execute();
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
                Toast.makeText(getContext(), spinnerKota.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}