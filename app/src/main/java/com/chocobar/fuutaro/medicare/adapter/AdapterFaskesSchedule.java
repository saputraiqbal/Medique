package com.chocobar.fuutaro.medicare.adapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.activity.DetailDokterActivity;
import com.chocobar.fuutaro.medicare.activity.DetailFaskesActivity;
import com.chocobar.fuutaro.medicare.fragment.GetLoketFragment;
import com.chocobar.fuutaro.medicare.model.FaskesSchedule;

import java.util.ArrayList;

public class AdapterFaskesSchedule extends RecyclerView.Adapter<AdapterFaskesSchedule.FaskesScheduleHolder>{
    //initiate some objects
    private LayoutInflater inflater;
    private Context ctx;
    private ArrayList<FaskesSchedule> mData;
    private Activity mActivity;

    //declare constructor
    public AdapterFaskesSchedule(ArrayList<FaskesSchedule> data) {
        this.mData = data;
    }

    //declare onCreateViewHolder to create a ViewHolder on RecyclerView
    @Override
    public FaskesScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data_jadwal, parent, false);
        return new FaskesScheduleHolder(v);
    }

    /**declare onBindViewHolder to set data on specicfied position
     * that will displayed at RecyclerView**/
    @Override
    public void onBindViewHolder(FaskesScheduleHolder holder, final int position) {
        holder.namaPegawai.setSelected(true);
        holder.namaPegawai.setText((mData.get(position)).getNamaPegawai());
        holder.pelayanan.setText((mData.get(position)).getJenisPelayanan());
        holder.viewTime.setText((mData.get(position)).getStartAt() + " - " + (mData.get(position)).getEndAt());
        String stringKuota = Integer.toString((mData.get(position)).getKuota());
        String stringAntrian = Integer.toString((mData.get(position)).getJumlahAntrian());
        holder.kuota.setText(stringKuota);
        holder.antrian.setText(stringAntrian);
        final String idPartner = Integer.toString((mData.get(position)).getIdPartner());
        final String idPelayanan = Integer.toString((mData.get(position)).getIdJenisPelayanan());
        final String idJadwalPraktik = Integer.toString((mData.get(position)).getIdJadwal());
        //action taken when one of items is clicked
        holder.booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set DialogFragment
                DetailFaskesActivity activity = (DetailFaskesActivity) v.getContext();
                GetLoketFragment loketFrag = GetLoketFragment.newInstance(idPartner, idPelayanan, idJadwalPraktik);
                loketFrag.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
                loketFrag.show(activity.getFragmentManager(), "fragment_get_loket");
            }
        });
    }

    //declare getItemCount to get item amount that mounted to RecyclerView
    @Override
    public int getItemCount() {
        return mData.size();
    }

    //declare Holder class inside adapter class
    public class FaskesScheduleHolder extends RecyclerView.ViewHolder{
        //declare widget objects
        TextView namaPegawai, pelayanan, viewTime, kuota, antrian;
        Button booking;

        //declare constructor and apply widget objects to connected to the widgets at layout
        public FaskesScheduleHolder(View itemView) {
            super(itemView);
            namaPegawai = itemView.findViewById(R.id.txtMainInfo);
            pelayanan = itemView.findViewById(R.id.txtDetailInfo);
            viewTime = itemView.findViewById(R.id.txtViewTime);
            kuota = itemView.findViewById(R.id.txtViewDokterKuota);
            antrian = itemView.findViewById(R.id.txtViewDokterAntrian);
            booking = itemView.findViewById(R.id.btnBooking);
        }
    }
}