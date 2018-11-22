package com.chocobar.fuutaro.medicare.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.model.Riwayat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AdapterRiwayat extends RecyclerView.Adapter<AdapterRiwayat.RiwayatHolder> {
    //initiate some objects
    private LayoutInflater inflater;
    private Context ctx;
    private ArrayList<Riwayat> mData;
    private Activity mACtivity;

    //declare adapter constructor
    public AdapterRiwayat(ArrayList<Riwayat> data, Activity activity) {
        this.mData = data;
        this.mACtivity = activity;
    }
    //declare onCreateViewHolder to create a ViewHolder on RecyclerView
    @Override
    public RiwayatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data_riwayat, parent, false);
        RiwayatHolder riwayatHolder = new RiwayatHolder(view);
        return riwayatHolder;
    }

    /**declare onBindViewHolder to set data on specicfied position
     * that will displayed at RecyclerView**/
    @Override
    public void onBindViewHolder(RiwayatHolder holder, final int position) {
        holder.tv_rs.setText((mData.get(position)).getNamaRs());
        String[] tglAntri = splitDateQueue((mData.get(position)).getTanggalAntri());
        holder.tv_tanggal.setText(dateSet(Integer.parseInt(tglAntri[0]), Integer.parseInt(tglAntri[1]), Integer.parseInt(tglAntri[2])));
        holder.tv_antrian_loket.setText((mData.get(position)).getNoAntrianLoket());
        holder.tv_loket_jamkes.setText("Loket "+ (mData.get(position)).getNamaLoket());
        holder.tv_loket_now.setText("Antrian Saat Ini : "+ (mData.get(position)).getNoALnow());
        holder.tv_antrian_pelayanan.setText((mData.get(position)).getNoAntrianPoli());
        holder.tv_pelayanan_jamkes.setText((mData.get(position)).getJenisPelayanan());
        holder.tv_pelayanan_now.setText("Antrian Saat Ini : "+ (mData.get(position)).getNoPnow());

        if(mData.get(position).getStatus().equals("1")){
            holder.notif.setBackgroundResource(R.color.colorGreen);
        }
        else if(mData.get(position).getStatus().equals("0")){
            holder.notif.setBackgroundResource(R.color.colorGray);
        }

    }
    //declare getItemCount to get item amount that mounted to RecyclerView
    @Override
    public int getItemCount() {
        return mData.size();
    }

    private String[] splitDateQueue(String string){
        String stringDtQueue = string;
        String[] dtQueue = stringDtQueue.split("T");
        stringDtQueue = dtQueue[0];
        dtQueue = stringDtQueue.split("-");
        return dtQueue;
    }

    private String dateSet(int year, int month, int dayOfMonth){
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, dayOfMonth);
        Date dateSet = cal.getTime();
        SimpleDateFormat formatView = new SimpleDateFormat("dd MMMM yyyy");
        String format = formatView.format(dateSet);
        return format;
    }

    //declare Holder class inside adapter class
    public class RiwayatHolder extends RecyclerView.ViewHolder{
        //declare widget objects
        LinearLayout notif;
        TextView tv_rs, tv_tanggal, tv_antrian_loket, tv_loket_jamkes, tv_loket_now, tv_antrian_pelayanan, tv_pelayanan_jamkes, tv_pelayanan_now;

        //declare constructor and apply widget objects to connected to the widgets at layout
        public RiwayatHolder(View itemView) {
            super(itemView);

            notif = itemView.findViewById(R.id.notif);

            tv_rs = itemView.findViewById(R.id.tv_rumahsakit);
            tv_tanggal = itemView.findViewById(R.id.tv_tanggal);
            tv_antrian_loket = itemView.findViewById(R.id.tv_antrian_loket);
            tv_loket_jamkes = itemView.findViewById(R.id.tv_loket_jamkes);
            tv_loket_now = itemView.findViewById(R.id.tv_loket_now);
            tv_antrian_pelayanan = itemView.findViewById(R.id.tv_antrian_pelayanan);
            tv_pelayanan_jamkes = itemView.findViewById(R.id.tv_pelayanan_jamkes);
            tv_pelayanan_now = itemView.findViewById(R.id.tv_pelayanan_now);
        }
    }

    //declare method that used to change item list
    public void updateList(ArrayList<Riwayat> newList){
        mData = new ArrayList<Riwayat>();
        mData.addAll(newList);
        notifyDataSetChanged();
    }
}
