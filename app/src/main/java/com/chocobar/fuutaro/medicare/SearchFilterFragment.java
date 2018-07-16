package com.chocobar.fuutaro.medicare;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.RadioButton;

import com.chocobar.fuutaro.medicare.AsyncTaskPopulateData.PopulateSpinnerKota;
import com.chocobar.fuutaro.medicare.model.Kota;
import com.chocobar.fuutaro.medicare.model.Spesialis;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;


public class SearchFilterFragment extends Fragment {
    private Spinner chooseKota, chooseSpesialis;
    private RadioButton chooseAllGender, chooseMale, chooseFemale;
    private Button getSearchFilter;

    private RecyclerView rView;
    private AdapterData adapter;
    private ArrayList<Kota> arrListKota = new ArrayList<>();
    private ArrayList<Spesialis> arrListSpesialis = new ArrayList<>();

    public SearchFilterFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewFragment = inflater.inflate(R.layout.fragment_search_filter, container, false);
        viewFragment.setBackgroundColor(Color.WHITE);

        chooseKota = viewFragment.findViewById(R.id.spinnerKota);
        chooseSpesialis = viewFragment.findViewById(R.id.spinnerSpesialis);
        chooseAllGender = viewFragment.findViewById(R.id.radioAllGender);
        chooseMale = viewFragment.findViewById(R.id.radioGenderMale);
        chooseFemale = viewFragment.findViewById(R.id.radioGenderFemale);
        getSearchFilter = viewFragment.findViewById(R.id.btnSearchFilter);

        new PopulateSpinnerKota(getContext(), chooseKota).execute();

        return viewFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSearchFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

//    class AsyncTaskPopulateSpinnerKota extends AsyncTask<Void, Void, ArrayList<Kota>>{
//
//        @Override
//        protected void onPostExecute(ArrayList<Kota> arrayList) {
//            ArrayList<String>arrList = new ArrayList<String>();
//            for (int i = 0; i < arrayList.size(); i++){
//                arrList.add(arrayList.get(i).getNamaKota());
//            }
//            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrList);
//            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            chooseKota.setAdapter(spinnerAdapter);
//        }
//
//        @Override
//        protected ArrayList doInBackground(Void... voids) {
//            //calling request to webservice process from AsyncTaskActivity then store the return value
//            List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_getKota", null);
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
//                    return arrListKota;
//                    //if request has been retrieved by SoapObject soapResponse
//                else{
//                    String callSpExcecutionResult = soapResponse.getPropertyAsString("CallSpExcecutionResult");
//                    JSONArray jArray = new JSONArray(callSpExcecutionResult);
//
//                    for (int i = 0; i < jArray.length(); i++) {
//                        Kota kota = new Kota();
//
//                        JSONObject jsonKota = jArray.getJSONObject(i);
//                        kota.setIdKota(jsonKota.getInt("intIDKota"));
//                        kota.setNamaKota(jsonKota.getString("txtKota"));
//                        arrListKota.add(kota);
//                    }
//                }
//                //if transaction failed
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            //operation will return the integer value for execute by onPostExecute method
//            return arrListKota;
//        }
//    }

}
