package com.chocobar.fuutaro.medicare;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chocobar.fuutaro.medicare.model.DetailDokter;

import org.json.JSONArray;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DetailDokterActivity extends AppCompatActivity {
    TextView namaDokter, linkProfile, tglDaftar, linkUbahTgl;
    ImageView imgShowDokter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dokter);

        imgShowDokter = findViewById(R.id.imgViewDokter);
        namaDokter = findViewById(R.id.txtViewDokterName);
        linkProfile = findViewById(R.id.txtViewDokterDetail);
        tglDaftar = findViewById(R.id.txtViewDayReserve);
        linkUbahTgl = findViewById(R.id.txtViewChangeDate);
        new ViewDetailDokter().execute(1);
        setDate(tglDaftar);
    }

    protected void setDate(TextView txtView){
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        String date = format.format(today);
        txtView.setText(getString(R.string.set_date_reserved) + " " + date);
    }

    class ViewDetailDokter extends AsyncTask<Integer, Void, ArrayList<DetailDokter>>{
        @Override
        protected void onPostExecute(ArrayList<DetailDokter> detailDokters) {
            namaDokter.setText(detailDokters.get(0).getNama());
            String stringBase64 = detailDokters.get(0).getImgBase64().substring(detailDokters.get(0).getImgBase64().indexOf(",") + 1);
            byte[] avatarByte = Base64.decode(stringBase64, Base64.DEFAULT);
            Bitmap imgDecode = BitmapFactory.decodeByteArray(avatarByte, 0, avatarByte.length);
            imgShowDokter.setImageBitmap(imgDecode);
        }

        @Override
        protected ArrayList<DetailDokter> doInBackground(Integer... integers) {
            ArrayList<DetailDokter> arrDetailDokter = new ArrayList<>();

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
}
