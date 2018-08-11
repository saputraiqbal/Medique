package com.chocobar.fuutaro.medicare.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.chocobar.fuutaro.medicare.AsyncTasks.SearchDokter;
import com.chocobar.fuutaro.medicare.AsyncTasks.SearchFaskes;
import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.activity.MainActivity;

public class DokterFragment extends Fragment {

    public static RecyclerView rView;

    public DokterFragment() {
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
        new SearchDokter(getActivity()).execute("", "0", "0", "0");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_filter:
                showSearchFilter();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSearchFilter(){
        //call DokterFilterFragment to show DialogFragment
        DialogFragment searchFrag = new DokterFilterFragment();
        searchFrag.show(getFragmentManager(), "fragment_search_filter");
    }

    public void beginSearch(String query){
        new SearchDokter(getActivity()).execute(query, "0", "0", "0");
    }
}
