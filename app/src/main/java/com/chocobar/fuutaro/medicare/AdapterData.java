package com.chocobar.fuutaro.medicare;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.List;

public class AdapterData extends RecyclerView.Adapter<AdapterData.CustomViewHolder> {

    private LayoutInflater inflater;
    private Context ctx;

    public AdapterData(Context ctx){

    }

    @Override
    public AdapterData.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder( final CustomViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        public CustomViewHolder(View itemView) {
            super(itemView);
        }
    }

}
