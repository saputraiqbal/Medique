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

import com.chocobar.fuutaro.medicare.activity.DetailDokterActivity;
import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.fragment.GetLoketFragment;
import com.chocobar.fuutaro.medicare.model.DokterSchedule;

import java.util.ArrayList;

public class AdapterDataSchedule extends RecyclerView.Adapter<AdapterDataSchedule.DokterScheduleHolder>{
    private LayoutInflater inflater;
    private Context ctx;
    private ArrayList<DokterSchedule> mData;
    private Activity mActivity;

    OnBookingClicked listener;

    public AdapterDataSchedule(ArrayList<DokterSchedule> data, Activity activity) {
        this.mData = data;
        this.mActivity = activity;
    }

    public interface OnBookingClicked{
        void sendData(String idPartner, String idPelayanan, String idJadwalPraktik);
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
        String stringKuota = Integer.toString((mData.get(position)).getKuota());
        String stringAntrian = Integer.toString((mData.get(position)).getJumlahAntrian());
        holder.kuota.setText(stringKuota);
        holder.antrian.setText(stringAntrian);
        final String idPartner = Integer.toString((mData.get(position)).getIdPartner());
        final String idPelayanan = Integer.toString((mData.get(position)).getIdJenisPelayanan());
        final String idJadwalPraktik = Integer.toString((mData.get(position)).getIdJadwal());
        holder.booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailDokterActivity activity = (DetailDokterActivity) v.getContext();
                GetLoketFragment loketFrag = GetLoketFragment.newInstance(idPartner, idPelayanan, idJadwalPraktik);
                loketFrag.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
                loketFrag.show(activity.getFragmentManager(), "fragment_get_loket");
            }
        });
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
