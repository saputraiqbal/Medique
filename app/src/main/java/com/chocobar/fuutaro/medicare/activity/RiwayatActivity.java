package com.chocobar.fuutaro.medicare.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.chocobar.fuutaro.medicare.AsyncTasks.ViewRiwayat;
import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.adapter.AdapterRiwayat;
import com.chocobar.fuutaro.medicare.model.Riwayat;

import java.util.ArrayList;

public class RiwayatActivity extends AppCompatActivity {

    private RecyclerView rView;

    public static AdapterRiwayat adapterRiwayat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Riwayat Antrian");

        rView = findViewById(R.id.rv_riwayat);

        rView.setLayoutManager(new LinearLayoutManager(this));
        new ViewRiwayat(RiwayatActivity.this, rView, adapterRiwayat).execute("1");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }
}
