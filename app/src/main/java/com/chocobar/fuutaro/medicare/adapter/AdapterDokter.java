package com.chocobar.fuutaro.medicare.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chocobar.fuutaro.medicare.activity.DetailDokterActivity;
import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.activity.LoginActivity;
import com.chocobar.fuutaro.medicare.model.Dokter;

import java.util.ArrayList;

import static com.chocobar.fuutaro.medicare.STATIC_VALUES.LOGIN_STATUS;

public class AdapterDokter extends RecyclerView.Adapter<AdapterDokter.DokterHolder> {
    //initiate some objects
    private LayoutInflater inflater;
    private Context ctx;
    private ArrayList<Dokter> mData;
    private Activity mACtivity;

    //declare adapter constructor
    public AdapterDokter(ArrayList<Dokter> data, Activity activity) {
        this.mData = data;
        this.mACtivity = activity;
    }

    //declare onCreateViewHolder to create a ViewHolder on RecyclerView
    @Override
    public DokterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data, parent, false);
        return new DokterHolder(view);
    }

    /**declare onBindViewHolder to set data on specicfied position
     * that will displayed at RecyclerView**/
    @Override
    public void onBindViewHolder(final DokterHolder holder, final int position) {
        if(!mData.get(position).getImg().equals("null")){
            String stringBase64 = mData.get(position).getImg().substring(mData.get(position).getImg().indexOf(",") + 1);
            byte[] avatarByte = Base64.decode(stringBase64, Base64.DEFAULT);
            Bitmap imgDecode = BitmapFactory.decodeByteArray(avatarByte, 0, avatarByte.length);
            holder.viewAvatar.setImageBitmap(imgDecode);
        }
        holder.viewName.setText((mData.get(position)).getNama());
        holder.viewAddress.setText(mData.get(position).getAlamat()+", "+mData.get(position).getKota()+", "+mData.get(position).getProvinsi());
        holder.viewCallNum.setText(mData.get(position).getNoTelp());
        holder.viewSpecialist.setText(mData.get(position).getSpesialis());
        //action taken when one of items is clicked
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context ctx = v.getContext();
                if(LOGIN_STATUS == 0){
                    AlertDialog alert = new AlertDialog.Builder(ctx).create();
                    alert.setTitle("Mohon maaf");
                    alert.setMessage("Silakan login terlebih dahulu untuk dapat mengakses ini");
                    alert.setButton(AlertDialog.BUTTON_POSITIVE, "Login", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ctx, LoginActivity.class);
                            ctx.startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alert.show();
                }else{
                    /**set Intent and Bundle to initiate which activity will be activated
                     * and bundle some datas that will bring to another activity**/
                    Intent intent = new Intent(ctx, DetailDokterActivity.class);
                    Bundle setBundle = new Bundle();
                    setBundle.putString("setIdDokter", mData.get(position).getIdDokter());
                    setBundle.putString("setAlamat", holder.viewAddress.getText().toString());
                    setBundle.putString("setTelp", holder.viewCallNum.getText().toString());
                    intent.putExtras(setBundle);
                    ctx.startActivity(intent);
                }
            }
        });
    }

    //declare getItemCount to get item amount that mounted to RecyclerView
    @Override
    public int getItemCount() {
        return mData.size();
    }

    //declare Holder class inside adapter class
    public class DokterHolder extends RecyclerView.ViewHolder{
        //declare widget objects
        ImageView viewAvatar;
        TextView viewName, viewAddress, viewCallNum, viewSpecialist;

        //declare constructor and apply widget objects to connected to the widgets at layout
        public DokterHolder(View itemView) {
            super(itemView);
            viewAvatar = itemView.findViewById(R.id.vImage);
            viewName = itemView.findViewById(R.id.txtName);
            viewAddress = itemView.findViewById(R.id.txtAddress);
            viewCallNum = itemView.findViewById(R.id.txtInfoRow1);
            viewSpecialist = itemView.findViewById(R.id.txtInfoRow2);
        }
    }

    //declare method that used to change item list
    public void updateList(ArrayList<Dokter> newList){
        mData = new ArrayList<Dokter>();
        mData.addAll(newList);
        notifyDataSetChanged();
    }
}