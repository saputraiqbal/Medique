package com.chocobar.fuutaro.medicare.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.chocobar.fuutaro.medicare.AsyncTasks.Top20Dokter;
import com.chocobar.fuutaro.medicare.R;

public class MainDokterFragment extends Fragment {

    public static RecyclerView rView;

    public MainDokterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);
        rView = rootview.findViewById(R.id.listDataMain);
        return rootview;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rView.setLayoutManager(new LinearLayoutManager(getActivity()));
        new Top20Dokter(getActivity()).execute("", "0", "0", "0");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_filter:
                showFilterFragment();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFilterFragment(){
        DialogFragment filterFrag = new DokterFilterFragment();
        filterFrag.show(getFragmentManager(), "fragment_dokter_filter");
    }
}
