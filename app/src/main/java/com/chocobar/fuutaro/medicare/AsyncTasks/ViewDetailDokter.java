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
    private TextView namaDokter;
    private ImageView imgShowDokter;
    private ProgressDialog progress;
    private ArrayList<DetailDokter> arrDetailDokter;

    public OnProfileSet listener = null;

    public interface OnProfileSet{
        void onProfileSet(ArrayList<DetailDokter> detailDokters);
    }

    public ViewDetailDokter(DetailDokterActivity detailActivity, OnProfileSet listener) {
        this.namaDokter = DetailDokterActivity.namaDokter;
        this.imgShowDokter = DetailDokterActivity.imgShowDokter;
        this.progress = new ProgressDialog(detailActivity);
        this.arrDetailDokter = new ArrayList<>();
        this.listener = listener;
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
        if(detailDokters.get(0).getImgBase64().equals("null"))
            imgShowDokter.setBackgroundResource(R.drawable.ic_profile);
        else{
            String stringBase64 = detailDokters.get(0).getImgBase64().substring(detailDokters.get(0).getImgBase64().indexOf(",") + 1);
            byte[] avatarByte = Base64.decode(stringBase64, Base64.DEFAULT);
            Bitmap imgDecode = BitmapFactory.decodeByteArray(avatarByte, 0, avatarByte.length);
            imgShowDokter.setImageBitmap(imgDecode);
        }
        listener.onProfileSet(detailDokters);
    }

    @Override
    protected ArrayList<DetailDokter> doInBackground(String... strings) {
        List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_DetailDokter", "intIDDokter#" + strings[0]);
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