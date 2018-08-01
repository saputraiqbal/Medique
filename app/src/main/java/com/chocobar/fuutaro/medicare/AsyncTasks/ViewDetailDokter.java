package com.chocobar.fuutaro.medicare.AsyncTasks;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.chocobar.fuutaro.medicare.AsyncTasks.core.AsyncTaskActivity;
import com.chocobar.fuutaro.medicare.activity.DetailDokterActivity;
import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.STATIC_VALUES;
import com.chocobar.fuutaro.medicare.model.DetailDokter;

import org.json.JSONArray;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class ViewDetailDokter extends AsyncTask<String, Void, ArrayList<DetailDokter>> {
    //initiate some objects
    private TextView namaDokter;
    private ImageView imgShowDokter;
    private ProgressDialog progress;
    private ArrayList<DetailDokter> arrDetailDokter;

    public OnProfileSet listener = null;

    //declare interface to send data from AsyncTask to Fragment
    public interface OnProfileSet{
        void onProfileSet(ArrayList<DetailDokter> detailDokters);
    }

    //declare constructor
    public ViewDetailDokter(DetailDokterActivity detailActivity, OnProfileSet listener) {
        this.namaDokter = DetailDokterActivity.namaDokter;
        this.imgShowDokter = DetailDokterActivity.imgShowDokter;
        this.progress = new ProgressDialog(detailActivity);
        this.arrDetailDokter = new ArrayList<>();
        this.listener = listener;
    }

    //declared for do some action before AsyncTask is work
    @Override
    protected void onPreExecute() {
        progress.setMessage("Tunggu sebentar...");
        progress.show();
    }

    //declared for do some action when AsyncTask finished working
    @Override
    protected void onPostExecute(ArrayList<DetailDokter> detailDokters) {
        if(progress.isShowing())
            progress.dismiss();
        //some widgets set the value here
        namaDokter.setText(detailDokters.get(0).getNama());
        if(detailDokters.get(0).getImgBase64().equals("null"))
            imgShowDokter.setBackgroundResource(R.drawable.ic_profile);
        else{
            String stringBase64 = detailDokters.get(0).getImgBase64().substring(detailDokters.get(0).getImgBase64().indexOf(",") + 1);
            byte[] avatarByte = Base64.decode(stringBase64, Base64.DEFAULT);
            Bitmap imgDecode = BitmapFactory.decodeByteArray(avatarByte, 0, avatarByte.length);
            imgShowDokter.setImageBitmap(imgDecode);
        }
        //use interface method so that AsyncTask can send data to Fragment
        listener.onProfileSet(detailDokters);
    }

    @Override
    protected ArrayList<DetailDokter> doInBackground(String... strings) {
        //calling request to webservice process from AsyncTaskActivity then store the return value
        List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_DetailDokter", "intIDDokter#" + strings[0]);
        //convert each List values with their match object type data
        SoapSerializationEnvelope env = (SoapSerializationEnvelope)dataReceived.get(0);
        HttpTransportSE httpTrans = (HttpTransportSE)dataReceived.get(1);

        //transaction for sending the request
        try {
            httpTrans.call(STATIC_VALUES.SOAP_ACTION, env);
            //processing the request
            SoapObject soapResponse = (SoapObject) env.bodyIn;

            //selection either SoapObject soapResponse retrieve the request or not
            if(soapResponse.toString().equals("CallSpExcecutionResponse{}") || soapResponse == null)
                return arrDetailDokter;
            //if request has been retrieved by SoapObject soapResponse
            else{
                String callSpExcecutionResult = soapResponse.getPropertyAsString("CallSpExcecutionResult");
                JSONArray jArray = new JSONArray(callSpExcecutionResult);
                //set item values and store at ArrayList
                DetailDokter detailDokter = new DetailDokter();
                detailDokter.setNama(jArray.getJSONObject(0).getString("txtNamaDokter"));
                detailDokter.setProfileDetail(jArray.getJSONObject(0).getString("txtProfile"));
                detailDokter.setImgBase64(jArray.getJSONObject(0).getString("imgAvatar"));
                arrDetailDokter.add(detailDokter);
            }
            //if transaction failed
        }catch (Exception e){
            e.printStackTrace();
        }
        //operation will return the integer value for execute by onPostExecute method
        return arrDetailDokter;
    }
}