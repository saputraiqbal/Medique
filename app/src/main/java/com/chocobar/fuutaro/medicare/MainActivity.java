package com.chocobar.fuutaro.medicare;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText searchBox;
    private ImageButton btnBeginSearch, btnSearchFilter;
    private RecyclerView rView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBox = findViewById(R.id.txtSearchKeywords);
        btnBeginSearch = findViewById(R.id.btnSearch);
        btnSearchFilter = findViewById(R.id.btnFilter);
        rView = findViewById(R.id.listData);

        rView.setLayoutManager(new LinearLayoutManager(this));

        btnBeginSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSearchFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    class WebServiceSearch extends AsyncTask<String, Void, ArrayList>{
        @Override
        protected void onPostExecute(ArrayList arrayList) {
            super.onPostExecute(arrayList);
        }

        @Override
        protected ArrayList doInBackground(String... strings) {
            return null;
        }
    }
}
