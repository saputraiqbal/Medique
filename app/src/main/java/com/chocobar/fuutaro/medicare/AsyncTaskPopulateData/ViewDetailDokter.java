package com.chocobar.fuutaro.medicare.AsyncTaskPopulateData;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.chocobar.fuutaro.medicare.AsyncTaskActivity;
import com.chocobar.fuutaro.medicare.DetailDokterActivity;
import com.chocobar.fuutaro.medicare.MainActivity;
import com.chocobar.fuutaro.medicare.STATIC_VALUES;
import com.chocobar.fuutaro.medicare.model.DetailDokter;

import org.json.JSONArray;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class ViewDetailDokter extends AsyncTask<Integer, Void, ArrayList<DetailDokter>> {
    private TextView namaDokter;
    private ImageView imgShowDokter;
    private ProgressDialog progress;
    private ArrayList<DetailDokter> arrDetailDokter;

    public ViewDetailDokter(DetailDokterActivity detailActivity) {
        this.namaDokter = DetailDokterActivity.namaDokter;
        this.imgShowDokter = DetailDokterActivity.imgShowDokter;
        this.progress = new ProgressDialog(detailActivity);
        this.arrDetailDokter = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        progress.setMessage("Tunggu sebentar...");
        progress.show();
    }

    @Override
    protected void onPostExecute(ArrayList<DetailDokter> detailDokters) {
        if(progress.isShowing())
            progress.dismiss();
        namaDokter.setText(detailDokters.get(0).getNama());
        String stringBase64 = detailDokters.get(0).getImgBase64().substring(detailDokters.get(0).getImgBase64().indexOf(",") + 1);
        byte[] avatarByte = Base64.decode(stringBase64, Base64.DEFAULT);
        Bitmap imgDecode = BitmapFactory.decodeByteArray(avatarByte, 0, avatarByte.length);
        imgShowDokter.setImageBitmap(imgDecode);
    }

    @Override
    protected ArrayList<DetailDokter> doInBackground(Integer... integers) {
        List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_DetailDokter", "intIDDokter#" + 1);
        SoapSerializationEnvelope env = (SoapSerializationEnvelope)dataReceived.get(0);
        HttpTransportSE httpTrans = (HttpTransportSE)dataReceived.get(1);

        try {
            httpTrans.call(STATIC_VALUES.SOAP_ACTION, env);
            SoapObject soapResponse = (SoapObject) env.bodyIn;

            if(soapResponse.toString().equals("CallSpExcecutionResponse{}") || soapResponse == null)
                return arrDetailDokter;
            else{
                String callSpExcecutionResult = soapResponse.getPropertyAsString("CallSpExcecutionResult");
                JSONArray jArray = new JSONArray(callSpExcecutionResult);
                DetailDokter detailDokter = new DetailDokter();
                detailDokter.setNama(jArray.getJSONObject(0).getString("txtNamaDokter"));
                detailDokter.setProfileDetail(jArray.getJSONObject(0).getString("txtProfile"));
                detailDokter.setImgBase64(jArray.getJSONObject(0).getString("imgAvatar"));
                arrDetailDokter.add(detailDokter);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return arrDetailDokter;
    }
}