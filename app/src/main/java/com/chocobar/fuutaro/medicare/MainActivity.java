package com.chocobar.fuutaro.medicare;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.chocobar.fuutaro.medicare.model.Dokter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;


public class MainActivity extends AppCompatActivity {
    private EditText searchBox;
    private ImageButton btnBeginSearch, btnSearchFilter;
    private RecyclerView rView;
    private AdapterData adapter;
    ArrayList<Dokter> arrayList = new ArrayList<Dokter>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBox = findViewById(R.id.txtSearchKeywords);
        btnBeginSearch = findViewById(R.id.imageSearch);
        btnSearchFilter = findViewById(R.id.imageFilter);
        rView = findViewById(R.id.listData);


        rView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterData(arrayList, MainActivity.this);


        btnBeginSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new WebServiceSearch(MainActivity.this).execute(searchBox.getText().toString());
            }
        });

        btnSearchFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    class WebServiceSearch extends AsyncTask<String, Void, ArrayList>{
        //initialize ProgressDialog
        private ProgressDialog searchLoad;

        private WebServiceSearch (MainActivity loginActivity){
            searchLoad = new ProgressDialog(loginActivity);
        }

        @Override
        protected void onPreExecute() {
            searchLoad.setMessage("Tunggu sebentar...");
            searchLoad.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            if(searchLoad.isShowing())
                searchLoad.dismiss();

            //adapter ini yang tadinya di dalam try transaction di method doInBackground
            adapter = new AdapterData(arrayList, MainActivity.this);
            rView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected ArrayList doInBackground(String... strings) {
            //initialize return value

            //calling request to webservice process from AsyncTaskActivity then store the return value
            List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_SearchTop20Dokter", "txtKeywords#"+strings[0]+"~intIDKota#0"+"~intIDSpesialisDokter#0"+"~intIDJenisKelamin#0");
            //convert each List values with their match object type data
            SoapSerializationEnvelope env = (SoapSerializationEnvelope) dataReceived.get(0);
            HttpTransportSE httpTrans = (HttpTransportSE) dataReceived.get(1);

            //transaction for sending the request
            try {
                httpTrans.call(STATIC_VALUES.SOAP_ACTION, env);
                //processing the request
                SoapObject soapResponse = (SoapObject) env.bodyIn;

                //selection either SoapObject soapResponse retrieve the request or not
                if(soapResponse.toString().equals("CallSpExcecutionResponse{}") || soapResponse == null)
                    return arrayList;
                    //if request has been retrieved by SoapObject soapResponse
                else{
                    String callSpExcecutionResult = soapResponse.getPropertyAsString("CallSpExcecutionResult");
                    JSONArray jArray = new JSONArray(callSpExcecutionResult);

                    for (int i = 0; i < jArray.length(); i++){
                        Dokter dokter = new Dokter();

                        JSONObject dataDokter = jArray.getJSONObject(i);
                        dokter.setNama(dataDokter.getString("txtNamaDokter"));
                        dokter.setNoTelp(dataDokter.getString("txtNoHP"));
                        dokter.setAlamat(dataDokter.getString("txtAlamat"));
                        dokter.setProvinsi(dataDokter.getString("txtProvinsi"));
                        dokter.setKota(dataDokter.getString("txtKota"));
                        dokter.setSpesialis(dataDokter.getString("txtSpesialis"));
                        dokter.setImg(dataDokter.getString("imgAvatar"));

                        arrayList.add(dokter);
                    }

                    //Dokter dokter = new Dokter(nama, noTelp, alamat, kota, provinsi, spesialis, img);

                }
                //if transaction failed
            }catch (Exception e){
                e.printStackTrace();
            }
            //operation will return the integer value for execute by onPostExecute method
            return arrayList;
        }
    }
    class WebService extends AsyncTask<String, Void, ArrayList>{
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
