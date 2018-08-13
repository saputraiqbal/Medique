package com.chocobar.fuutaro.medicare.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chocobar.fuutaro.medicare.AsyncTasks.Top20Faskes;
import com.chocobar.fuutaro.medicare.R;

public class MainFaskesFragment extends Fragment{

    public static RecyclerView rView;

    public MainFaskesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);
        rView = rootview.findViewById(R.id.listDataMain);
        setHasOptionsMenu(true);
        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rView.setLayoutManager(new LinearLayoutManager(getActivity()));
        new Top20Faskes(getActivity()).execute("", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
    }
}
