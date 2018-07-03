package com.chocobar.fuutaro.medicare;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.app.ProgressDialog;
import android.view.View;



public class MainActivity extends AppCompatActivity {

    private static final String SOAP_ACTION = "http://tempuri.org/CallSpExcecution";
    private static final String NAMESPACE = "CallSpExcecution";
    private static final String METHOD_NAME = "http://tempuri.org/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

class myAsyncTask extends AsyncTask<Void, Void, Void>{
    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }
}
