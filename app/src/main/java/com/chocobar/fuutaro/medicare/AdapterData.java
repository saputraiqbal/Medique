package com.chocobar.fuutaro.medicare;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chocobar.fuutaro.medicare.model.Dokter;

import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.List;
import com.squareup.picasso.Picasso;

public class AdapterData extends RecyclerView.Adapter<AdapterData.DokterHolder> {

    private LayoutInflater inflater;
    private Context ctx;
    private ArrayList<Dokter> mData;
    private Activity mACtivity;

    public AdapterData(ArrayList<Dokter> data, Activity activity) {
        this.mData = data;
        this.mACtivity = activity;
    }

    @Override
    public DokterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data, parent, false);
        return new DokterHolder(view);
    }

    @Override
    public void onBindViewHolder(DokterHolder holder, final int position) {
        //baris ini awalnya di bagian manggil method with(), parameternya diisi ctx, sedangkan ctx sendiri isinya kosong
        Picasso.with(mACtivity).load(mData.get(position).getImg()).into(holder.viewAvatar);

        holder.viewName.setText((mData.get(position)).getNama());
        holder.viewAddress.setText(mData.get(position).getAlamat()+", "+mData.get(position).getKota()+", "+mData.get(position).getProvinsi());
        holder.viewCallNum.setText(mData.get(position).getNoTelp());
        holder.viewSpecialist.setText(mData.get(position).getSpesialis());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class DokterHolder extends RecyclerView.ViewHolder{
        ImageView viewAvatar;
        TextView viewName, viewAddress, viewCallNum, viewSpecialist;

        public DokterHolder(View itemView) {
            super(itemView);
            viewAvatar = itemView.findViewById(R.id.viewPersonPhoto);
            viewName = itemView.findViewById(R.id.txtPersonName);
            viewAddress = itemView.findViewById(R.id.txtPersonAddress);
            viewCallNum = itemView.findViewById(R.id.txtPersonCallNum);
            viewSpecialist = itemView.findViewById(R.id.txtPersonSpecialist);
        }

        //public void setName(String name){
            //viewName.setText(name);
        //}

        //public void setAddress(String alamat, String provinsi, String kota){
            //viewAddress.setText(alamat + ", " + kota + ", " + provinsi);
        //}

        //public void setCallNum(String callNum){
           // viewCallNum.setText(callNum);
        //}

        //public void setSpecialist(String specialist){
            //viewSpecialist.setText(specialist);
        //}

    }

}
