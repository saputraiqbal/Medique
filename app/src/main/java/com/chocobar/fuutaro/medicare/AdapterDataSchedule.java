package com.chocobar.fuutaro.medicare;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chocobar.fuutaro.medicare.model.DokterSchedule;

import java.util.ArrayList;
import java.util.List;

public class AdapterDataSchedule extends RecyclerView.Adapter<AdapterDataSchedule.DokterScheduleHolder>{
    private LayoutInflater inflater;
    private Context ctx;
    private ArrayList<DokterSchedule> mData;
    private Activity mActivity;

    public AdapterDataSchedule(ArrayList<DokterSchedule> data, Activity activity) {
        this.mData = data;
        this.mActivity = activity;
    }

    @Override
    public DokterScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data_jadwal_praktek, parent, false);
        return new DokterScheduleHolder(v);
    }

    @Override
    public void onBindViewHolder(DokterScheduleHolder holder, final int position) {
        holder.instance.setText((mData.get(position)).getPartner());
        holder.instanceType.setText((mData.get(position)).getJenisPelayanan());
        holder.viewTime.setText((mData.get(position)).getStartAt() + " - " + (mData.get(position)).getEndAt());
        String stringKuota = Integer.toString((mData.get(position)).getKuota()),
        stringAntrian = Integer.toString((mData.get(position)).getJumlahAntrian());
        holder.kuota.setText(stringKuota);
        holder.antrian.setText(stringAntrian);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class DokterScheduleHolder extends RecyclerView.ViewHolder{
        TextView instance, instanceType, viewTime, kuota, antrian;
        Button booking;

        public DokterScheduleHolder(View itemView) {
            super(itemView);
            instance = itemView.findViewById(R.id.txtViewInstance);
            instanceType = itemView.findViewById(R.id.txtViewInstanceType);
            viewTime = itemView.findViewById(R.id.txtViewTime);
            kuota = itemView.findViewById(R.id.txtViewDokterKuota);
            antrian = itemView.findViewById(R.id.txtViewDokterAntrian);
            booking = itemView.findViewById(R.id.btnBooking);
        }
    }
}
