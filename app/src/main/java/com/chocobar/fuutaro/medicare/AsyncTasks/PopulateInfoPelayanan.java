package com.chocobar.fuutaro.medicare.AsyncTasks;

import android.os.AsyncTask;

import com.chocobar.fuutaro.medicare.AsyncTasks.core.AsyncTaskActivity;
import com.chocobar.fuutaro.medicare.STATIC_VALUES;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;

public class PopulateInfoPelayanan extends AsyncTask<String, Void, String>{
   public interface ListPelayananInfo{
        void onPopulateList(String populated);
    }

    public ListPelayananInfo listener = null;

    public PopulateInfoPelayanan(ListPelayananInfo listener){
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(String arrPelayananInfo) {
        listener.onPopulateList(arrPelayananInfo);
    }

    @Override
    protected String doInBackground(String... strings) {
        String info = "";
        //calling request to webservice process from AsyncTaskActivity then store the return value
        List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_getListPelayananInPartner", "intIDPartner#" + strings[0]);
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
                return null;
                //if request has been retrieved by SoapObject soapResponse
            else{
                String callSpExcecutionResult = soapResponse.getPropertyAsString("CallSpExcecutionResult");
                JSONArray jArray = new JSONArray(callSpExcecutionResult);

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonInfo = jArray.getJSONObject(i);
                    if(i == (jArray.length() - 1))
                        info = info + jsonInfo.getString("txtJenisPelayanan");
                    else
                        info = info + jsonInfo.getString("txtJenisPelayanan") + ", ";
                }
            }
            //if transaction failed
        }catch (Exception e){
            e.printStackTrace();
        }
        //operation will return the integer value for execute by onPostExecute method
        return info;
    }
}

