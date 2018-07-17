package com.chocobar.fuutaro.medicare;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.chocobar.fuutaro.medicare.AsyncTaskPopulateData.Search;
import com.chocobar.fuutaro.medicare.model.Dokter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    public static RecyclerView rView;
    public static AdapterData adapter;
    private ArrayList<Dokter> arrayList = new ArrayList<Dokter>();

    private MenuItem search, filter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rView = findViewById(R.id.listData);

        rView.setLayoutManager(new LinearLayoutManager(this));
//        new WebServiceSearch(MainActivity.this).execute(null, null);
        new Search(MainActivity.this).execute("", "0");
        adapter = new AdapterData(arrayList, MainActivity.this);
        rView.setAdapter(adapter);
    }

//    class WebServiceSearch extends AsyncTask<String, Void, ArrayList>{
//        //initialize ProgressDialog
//        private ProgressDialog searchLoad;
//
//        private WebServiceSearch (MainActivity mainActivity){
//            searchLoad = new ProgressDialog(mainActivity);
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList arrayList) {
//            if(searchLoad.isShowing())
//                searchLoad.dismiss();
//
//            adapter = new AdapterData(arrayList, MainActivity.this);
//            rView.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//        }
//
//        @Override
//        protected ArrayList doInBackground(String... strings) {
//            //calling request to webservice process from AsyncTaskActivity then store the return value
//            List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_SearchTop20Dokter", "txtKeywords#"+strings[0]+"~intIDKota#0~intIDSpesialisDokter#0"+"~intIDJenisKelamin#0");
//            //convert each List values with their match object type data
//            SoapSerializationEnvelope env = (SoapSerializationEnvelope) dataReceived.get(0);
//            HttpTransportSE httpTrans = (HttpTransportSE) dataReceived.get(1);
//
//            //transaction for sending the request
//            try {
//                httpTrans.call(STATIC_VALUES.SOAP_ACTION, env);
//                //processing the request
//                SoapObject soapResponse = (SoapObject) env.bodyIn;
//
//                //selection either SoapObject soapResponse retrieve the request or not
//                if(soapResponse.toString().equals("CallSpExcecutionResponse{}") || soapResponse == null)
//                    return arrayList;
//                    //if request has been retrieved by SoapObject soapResponse
//                else{
//                    String callSpExcecutionResult = soapResponse.getPropertyAsString("CallSpExcecutionResult");
//                    JSONArray jArray = new JSONArray(callSpExcecutionResult);
//
//                    for (int i = 0; i < jArray.length(); i++){
//                        Dokter dokter = new Dokter();
//
//                        JSONObject dataDokter = jArray.getJSONObject(i);
//                        dokter.setNama(dataDokter.getString("txtNamaDokter"));
//                        dokter.setNoTelp(dataDokter.getString("txtNoHP"));
//                        dokter.setAlamat(dataDokter.getString("txtAlamat"));
//                        dokter.setProvinsi(dataDokter.getString("txtProvinsi"));
//                        dokter.setKota(dataDokter.getString("txtKota"));
//                        dokter.setSpesialis(dataDokter.getString("txtSpesialis"));
//                        dokter.setImg(dataDokter.getString("imgAvatar"));
//                        arrayList.add(dokter);
//                    }
//                }
//                //if transaction failed
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            //operation will return the integer value for execute by onPostExecute method
//            return arrayList;
//        }
//    }

//    class WebService extends AsyncTask<String, Void, ArrayList>{
//        @Override
//        protected void onPostExecute(ArrayList arrayList) {
//            super.onPostExecute(arrayList);
//        }
//
//        @Override
//        protected ArrayList doInBackground(String... strings) {
//            return null;
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        search = menu.findItem(R.id.action_search);
        filter = menu.findItem(R.id.action_filter);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(this);
        filter.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction filterTransaction = fragmentManager.beginTransaction();

                final SearchFilterFragment searchFilterFrag = new SearchFilterFragment();
                filterTransaction.add(R.id.activity_main, searchFilterFrag);
                filterTransaction.addToBackStack(null);
                filterTransaction.commit();

                return true;
            }
        });
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
//        if(userInput.isEmpty()){
//            arrayList.clear();
//            new WebServiceSearch(MainActivity.this).execute("0");
//
//        }
//        else
            arrayList.clear();
            new Search(MainActivity.this).execute(userInput, "0");
            rView.setAdapter(adapter);

//        Dokter dokter = new Dokter();
//        for(String name : names){
//            if(name.toLowerCase().contains(userInput)){
//
//            }
//
//        }//            new WebServiceSearch(MainActivity.this).execute(userInput);
        return false;
    }
}
