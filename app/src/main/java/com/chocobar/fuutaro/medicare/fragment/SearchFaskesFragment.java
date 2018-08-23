package com.chocobar.fuutaro.medicare.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chocobar.fuutaro.medicare.AsyncTasks.SearchFaskes;
import com.chocobar.fuutaro.medicare.R;

public class SearchFaskesFragment extends Fragment{

    public static RecyclerView rView;
    public String queryString = "";

    public SearchFaskesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_show_data, container, false);
        rView = rootview.findViewById(R.id.listData);
        setHasOptionsMenu(true);
        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_filter:
//                showFilterFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    public void faskesSearch(String query){
        queryString = query;
        new SearchFaskes(getActivity()).execute(query.toString(), "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
    }

    public void showFilterFragment(){

    }
}
