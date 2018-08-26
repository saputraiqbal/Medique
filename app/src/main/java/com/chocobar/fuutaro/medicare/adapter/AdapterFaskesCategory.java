package com.chocobar.fuutaro.medicare.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.model.FaskesSchedule;
import com.chocobar.fuutaro.medicare.model.KategoriFaskes;

import java.util.ArrayList;

public class AdapterFaskesCategory extends RecyclerView.Adapter<AdapterFaskesCategory.ScheduleHolder>{
    //initiate some objects
    private LayoutInflater inflater;
    private Context ctx;
    private ArrayList<Object> arrObject;
    private ArrayList<KategoriFaskes> mData;
    private Activity mActivity;

    private ArrayList<FaskesSchedule> rawSchedule;
    private ArrayList<FaskesSchedule> mSchedule;

    //declare constructor
    public AdapterFaskesCategory(ArrayList<Object> data, Activity activity) {
        this.arrObject = data;
        this.mActivity = activity;
        this.mData = (ArrayList<KategoriFaskes>) arrObject.get(0);
        this.rawSchedule = (ArrayList<FaskesSchedule>) arrObject.get(1);
    }

    //declare onCreateViewHolder to create a ViewHolder on RecyclerView
    @Override
    public ScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data_category, parent, false);
        return new ScheduleHolder(v);
    }

    /**declare onBindViewHolder to set data on specicfied position
     * that will displayed at RecyclerView**/
    @Override
    public void onBindViewHolder(ScheduleHolder holder, final int position) {
        holder.category.setText(mData.get(position).getKategori());
        showSchedule(holder, mData.get(position).getKategori());
    }

    private void showSchedule(ScheduleHolder holder, String categoryHolder){
        String category = categoryHolder;
        mSchedule = new ArrayList<>();
        for(int i = 0; i < rawSchedule.size(); i++){
            if(rawSchedule.get(i).getJenisPelayanan().equals(category)){
                FaskesSchedule schedule = new FaskesSchedule();
                schedule.setIdPartner(rawSchedule.get(i).getIdPartner());
                schedule.setIdJenisPelayanan(rawSchedule.get(i).getIdJenisPelayanan());
                schedule.setIdJadwal(rawSchedule.get(i).getIdJadwal());
                schedule.setNamaPegawai(rawSchedule.get(i).getNamaPegawai());
                schedule.setJenisPelayanan(rawSchedule.get(i).getJenisPelayanan());
                schedule.setStartAt(rawSchedule.get(i).getStartAt());
                schedule.setEndAt(rawSchedule.get(i).getEndAt());
                schedule.setKuota(rawSchedule.get(i).getKuota());
                schedule.setJumlahAntrian(rawSchedule.get(i).getJumlahAntrian());
                mSchedule.add(schedule);
            }
        }
        AdapterFaskesSchedule adapterSchedule = new AdapterFaskesSchedule(mSchedule);
        holder.rViewSchedule.setLayoutManager(new LinearLayoutManager(mActivity));
        holder.rViewSchedule.setAdapter(adapterSchedule);
    }

    //declare getItemCount to get item amount that mounted to RecyclerView
    @Override
    public int getItemCount() {
        return mData.size();
    }

    //declare Holder class inside adapter class
    public class ScheduleHolder extends RecyclerView.ViewHolder{
        //declare widget objects
        TextView category;
        RecyclerView rViewSchedule;

        //declare constructor and apply widget objects to connected to the widgets at layout
        public ScheduleHolder(View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.txtCategory);
            rViewSchedule = itemView.findViewById(R.id.rViewFaskesSchedule);
        }
    }
}