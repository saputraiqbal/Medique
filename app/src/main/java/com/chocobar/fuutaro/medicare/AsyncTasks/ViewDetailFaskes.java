package com.chocobar.fuutaro.medicare.AsyncTasks;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.chocobar.fuutaro.medicare.AsyncTasks.core.AsyncTaskActivity;
import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.STATIC_VALUES;
import com.chocobar.fuutaro.medicare.activity.DetailFaskesActivity;
import com.chocobar.fuutaro.medicare.model.DetailFaskes;

import org.json.JSONArray;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class ViewDetailFaskes extends AsyncTask<String, Void, ArrayList<DetailFaskes>> {
    //initiate some objects
    private ImageView imgShowDokter;
    private TextView name, profile;
    private ProgressDialog progress;
    private ArrayList<DetailFaskes>arrDetailDokter;

    //declare constructor
    public ViewDetailFaskes(DetailFaskesActivity detailActivity) {
        this.name = DetailFaskesActivity.namaFaskes;
        this.profile = DetailFaskesActivity.profile;
        this.imgShowDokter = DetailFaskesActivity.imgShowFaskes;
        this.progress = new ProgressDialog(detailActivity);
        this.arrDetailDokter = new ArrayList<>();
    }

    //declared for do some action before AsyncTask is work
    @Override
    protected void onPreExecute() {
        progress.setMessage("Tunggu sebentar...");
        progress.show();
    }

    //declared for do some action when AsyncTask finished working
    @Override
    protected void onPostExecute(ArrayList<DetailFaskes> detailFaskes) {
        if(progress.isShowing())
            progress.dismiss();
        //some widgets set the value here
        name.setText(detailFaskes.get(0).getNamaFaskes());
        profile.setText(detailFaskes.get(0).getProfileDetail());
        if(!detailFaskes.get(0).getImgBase64().equals("null"))
            {
            String stringBase64 = detailFaskes.get(0).getImgBase64().substring(detailFaskes.get(0).getImgBase64().indexOf(",") + 1);
            byte[] avatarByte = Base64.decode(stringBase64, Base64.DEFAULT);
            Bitmap imgDecode = BitmapFactory.decodeByteArray(avatarByte, 0, avatarByte.length);
            imgShowDokter.setImageBitmap(imgDecode);
        }
    }

    @Override
    protected ArrayList<DetailFaskes> doInBackground(String... strings) {
        //calling request to webservice process from AsyncTaskActivity then store the return value
        List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_DetailFaskes", "intIDPartner#" + strings[0]);
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
                DetailFaskes detailFaskes = new DetailFaskes();
                detailFaskes.setNamaFaskes(jArray.getJSONObject(0).getString("txtPartnerName"));
                detailFaskes.setProfileDetail(jArray.getJSONObject(0).getString("txtProfileInfo"));
                detailFaskes.setImgBase64(jArray.getJSONObject(0).getString("imgAvatar"));
                arrDetailDokter.add(detailFaskes);
            }
            //if transaction failed
        }catch (Exception e){
            e.printStackTrace();
        }
        //operation will return the integer value for execute by onPostExecute method
        return arrDetailDokter;
    }
}