package com.chocobar.fuutaro.medicare.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chocobar.fuutaro.medicare.AsyncTasks.PopulateInfoJamkes;
import com.chocobar.fuutaro.medicare.AsyncTasks.PopulateInfoPelayanan;
import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.activity.DetailFaskesActivity;
import com.chocobar.fuutaro.medicare.activity.LoginActivity;
import com.chocobar.fuutaro.medicare.model.Faskes;

import java.util.ArrayList;

import static com.chocobar.fuutaro.medicare.STATIC_VALUES.LOGIN_STATUS;

public class AdapterFaskes extends RecyclerView.Adapter<AdapterFaskes.FaskesHolder> {
    //initiate some objects
    private LayoutInflater inflater;
    private Context ctx;
    private ArrayList<Faskes> mData;
    private Activity mACtivity;

    private String infoPelayanan;

    //declare adapter constructor
    public AdapterFaskes(ArrayList<Faskes> data, Activity activity) {
        this.mData = data;
        this.mACtivity = activity;
    }

    //declare onCreateViewHolder to create a ViewHolder on RecyclerView
    @Override
    public FaskesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data, parent, false);
        return new FaskesHolder(view);
    }

    /**declare onBindViewHolder to set data on specicfied position
     * that will displayed at RecyclerView**/
    @Override
    public void onBindViewHolder(final FaskesHolder holder, final int position) {
        if(!mData.get(position).getImgFaskes().equals("null")){
            String stringBase64 = mData.get(position).getImgFaskes().substring(mData.get(position).getImgFaskes().indexOf(",") + 1);
            byte[] avatarByte = Base64.decode(stringBase64, Base64.DEFAULT);
            Bitmap imgDecode = BitmapFactory.decodeByteArray(avatarByte, 0, avatarByte.length);
            holder.viewAvatar.setImageBitmap(imgDecode);
        }
        holder.viewName.setText((mData.get(position)).getNamaFaskes());
        holder.viewAddress.setText(mData.get(position).getAlamat()+", "+mData.get(position).getKota()+", "+mData.get(position).getProvinsi());

        new PopulateInfoPelayanan(new PopulateInfoPelayanan.ListPelayananInfo() {
            @Override
            public void onPopulateList(String populated) {
                holder.viewPelayananInfo.setText(populated);
            }
        }).execute(mData.get(position).getId());

        new PopulateInfoJamkes(new PopulateInfoJamkes.ListPelayananInfo() {
            @Override
            public void onPopulateList(String populated) {
                holder.viewJamkesInfo.setText(populated);
            }
        }).execute(mData.get(position).getId());
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
                Intent intent = new Intent(ctx, DetailFaskesActivity.class);
                Bundle setBundle = new Bundle();
                setBundle.putString("setIdPartner", mData.get(position).getId());
                setBundle.putString("setAlamat", holder.viewAddress.getText().toString());
                setBundle.putString("setPelayanan", holder.viewPelayananInfo.getText().toString());
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
    public class FaskesHolder extends RecyclerView.ViewHolder{
        //declare widget objects
        ImageView viewAvatar;
        TextView viewName, viewAddress, viewPelayananInfo, viewJamkesInfo;

        //declare constructor and apply widget objects to connected to the widgets at layout
        public FaskesHolder(View itemView) {
            super(itemView);
            viewAvatar = itemView.findViewById(R.id.vImage);
            viewName = itemView.findViewById(R.id.txtName);
            viewAddress = itemView.findViewById(R.id.txtAddress);
            viewPelayananInfo = itemView.findViewById(R.id.txtInfoRow1);
            viewJamkesInfo = itemView.findViewById(R.id.txtInfoRow2);
        }
    }

    //declare method that used to change item list
    public void updateList(ArrayList<Faskes> newList){
        mData = new ArrayList<Faskes>();
        mData.addAll(newList);
        notifyDataSetChanged();
    }
}