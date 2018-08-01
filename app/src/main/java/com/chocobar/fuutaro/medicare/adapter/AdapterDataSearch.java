package com.chocobar.fuutaro.medicare.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chocobar.fuutaro.medicare.activity.DetailDokterActivity;
import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.model.Dokter;

import java.util.ArrayList;

public class AdapterDataSearch extends RecyclerView.Adapter<AdapterDataSearch.DokterHolder> {

    private LayoutInflater inflater;
    private Context ctx;
    private ArrayList<Dokter> mData;
    private Activity mACtivity;

    public AdapterDataSearch(ArrayList<Dokter> data, Activity activity) {
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
        if(mData.get(position).getImg().equals("null")){
            holder.viewAvatar.setBackgroundResource(R.drawable.ic_profile);
        }
        else {
            String stringBase64 = mData.get(position).getImg().substring(mData.get(position).getImg().indexOf(",") + 1);
            byte[] avatarByte = Base64.decode(stringBase64, Base64.DEFAULT);
            Bitmap imgDecode = BitmapFactory.decodeByteArray(avatarByte, 0, avatarByte.length);
            holder.viewAvatar.setImageBitmap(imgDecode);
        }

        holder.viewName.setText((mData.get(position)).getNama());
        holder.viewAddress.setText(mData.get(position).getAlamat()+", "+mData.get(position).getKota()+", "+mData.get(position).getProvinsi());
        holder.viewCallNum.setText(mData.get(position).getNoTelp());
        holder.viewSpecialist.setText(mData.get(position).getSpesialis());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context ctx = v.getContext();
                Intent sendIdDokter = new Intent(ctx, DetailDokterActivity.class);
                Bundle setBundle = new Bundle();
                setBundle.putString("setIdDokter", mData.get(position).getIdDokter());
                sendIdDokter.putExtras(setBundle);
                ctx.startActivity(sendIdDokter);
            }
        });
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
    }

    public void updateList(ArrayList<Dokter> newList){
        mData = new ArrayList<Dokter>();
        mData.addAll(newList);
        notifyDataSetChanged();
    }

}