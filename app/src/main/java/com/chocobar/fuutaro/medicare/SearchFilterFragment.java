package com.chocobar.fuutaro.medicare;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.RadioButton;

import com.chocobar.fuutaro.medicare.model.Dokter;

import java.util.ArrayList;


public class SearchFilterFragment extends Fragment {
    private Spinner chooseKota, chooseSpesialis;
    private RadioButton chooseAllGender, chooseMale, chooseFemale;
    private Button getSearchFilter;

    private RecyclerView rView;
    private AdapterData adapter;
    private ArrayList<Dokter> arrayList = new ArrayList<>();

    public SearchFilterFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewFragment = inflater.inflate(R.layout.fragment_search_filter, container, false);
        viewFragment.setBackgroundColor(Color.WHITE);

        chooseKota = viewFragment.findViewById(R.id.spinnerKota);
        chooseSpesialis = viewFragment.findViewById(R.id.spinnerSpesialis);
        chooseAllGender = viewFragment.findViewById(R.id.radioAllGender);
        chooseMale = viewFragment.findViewById(R.id.radioGenderMale);
        chooseFemale = viewFragment.findViewById(R.id.radioGenderFemale);
        getSearchFilter = viewFragment.findViewById(R.id.btnSearchFilter);

        return viewFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSearchFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
