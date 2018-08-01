package com.chocobar.fuutaro.medicare.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Spinner;

import com.chocobar.fuutaro.medicare.AsyncTasks.GetNomorAntrian;
import com.chocobar.fuutaro.medicare.AsyncTasks.PopulateLoketPelayanan;
import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.model.LoketPelayanan;

import java.util.ArrayList;

public class GetLoketFragment extends DialogFragment {
    //initiate widget objects
    private Spinner spinnerLoket;
    private Button getBooking;

    //initiate some variables
    private String getIdPartner;
    private String getIdPelayanan;
    private String getIdJadwal;

    //initiate arrays
    private ArrayList<LoketPelayanan> daftarLoket = new ArrayList<>();

    private String[] arrAntrian = new String[2];

    //declare default constructor
    public GetLoketFragment() {
    }

    //declare newInstance to send data from source and will be used for DialogFragment
    public static GetLoketFragment newInstance(String getIdPartner, String getIdPelayanan, String getIdJadwal){
        GetLoketFragment loketFrag = new GetLoketFragment();
        Bundle args = new Bundle();
        args.putString("getIdPartner", getIdPartner);
        args.putString("getIdPelayanan", getIdPelayanan);
        args.putString("getIdJadwal", getIdJadwal);
        loketFrag.setArguments(args);
        return loketFrag;
    }

    //declare onCreateView to create view of DialogFragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Pilih Jenis Pelayanan");
        return inflater.inflate(R.layout.fragment_get_loket, container, false);
    }

    //declared to create Dialog of DialogFragment
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    //declare onResume
    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    //declare onViewCreated to view widgets after DialogFragment has been created
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getIdPartner = getArguments().getString("getIdPartner");
        getIdPelayanan = getArguments().getString("getIdPelayanan");
        getIdJadwal = getArguments().getString("getIdJadwal");
        spinnerLoket = view.findViewById(R.id.spinnerJenisLoket);
        getBooking = view.findViewById(R.id.btnGetLoket);

        //populate spinner using AsyncTask
        new PopulateLoketPelayanan(getActivity(), spinnerLoket, new PopulateLoketPelayanan.OnLoketData() {
            @Override
            public void onLoketData(ArrayList<LoketPelayanan> dataLoket) {
                daftarLoket = new ArrayList<>(dataLoket);
            }
        }).execute(getIdPartner);
    }

    //declared when activity of fragment has been created
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedLoket = spinnerLoket.getSelectedItem().toString();
                selectedLoket = chooseLoketVal(selectedLoket);
                new GetNomorAntrian(getActivity()).execute(getIdPartner, getIdPelayanan, "1" ,selectedLoket, getIdJadwal);
                getDialog().dismiss();
            }
        });
    }

    //declare to choose value chosen from spinner and store it to String variable type
    private String chooseLoketVal(String valChosen){
        String val = "";
        for(LoketPelayanan loket : daftarLoket){
            if(loket.getLoket().equals(valChosen)){
                val = Integer.toString(loket.getIdLoket());
                break;
            }
        }
        return val;
    }
}
