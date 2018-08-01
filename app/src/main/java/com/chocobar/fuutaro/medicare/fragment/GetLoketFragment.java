package com.chocobar.fuutaro.medicare.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.chocobar.fuutaro.medicare.AsyncTasks.GetNomorAntrian;
import com.chocobar.fuutaro.medicare.AsyncTasks.PopulateLoketPelayanan;
import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.model.LoketPelayanan;

import java.util.ArrayList;

public class GetLoketFragment extends DialogFragment {
    private Spinner spinnerLoket;
    private Button getBooking;

    private String getIdPartner;
    private String getIdPelayanan;
    private String getIdJadwal;

    private ArrayList<LoketPelayanan> daftarLoket = new ArrayList<>();

    private String[] arrAntrian = new String[2];

    public GetLoketFragment() {
    }

    public static GetLoketFragment newInstance(String getIdPartner, String getIdPelayanan, String getIdJadwal){
        GetLoketFragment loketFrag = new GetLoketFragment();
        Bundle args = new Bundle();
        args.putString("getIdPartner", getIdPartner);
        args.putString("getIdPelayanan", getIdPelayanan);
        args.putString("getIdJadwal", getIdJadwal);
        loketFrag.setArguments(args);
        return loketFrag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Pilih Jenis Pelayanan");
        return inflater.inflate(R.layout.fragment_get_loket, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getIdPartner = getArguments().getString("getIdPartner");
        getIdPelayanan = getArguments().getString("getIdPelayanan");
        getIdJadwal = getArguments().getString("getIdJadwal");
        spinnerLoket = view.findViewById(R.id.spinnerJenisLoket);
        getBooking = view.findViewById(R.id.btnGetLoket);

        new PopulateLoketPelayanan(getActivity(), spinnerLoket, new PopulateLoketPelayanan.OnLoketData() {
            @Override
            public void onLoketData(ArrayList<LoketPelayanan> dataLoket) {
                daftarLoket = new ArrayList<>(dataLoket);
            }
        }).execute(getIdPartner);
    }

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
