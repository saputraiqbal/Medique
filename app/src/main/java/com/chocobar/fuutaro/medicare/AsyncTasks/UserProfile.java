package com.chocobar.fuutaro.medicare.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.UnicodeSetSpanner;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.chocobar.fuutaro.medicare.AsyncTasks.core.AsyncTaskActivity;
import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.STATIC_VALUES;
import com.chocobar.fuutaro.medicare.activity.UserConfigurationActivity;
import com.chocobar.fuutaro.medicare.model.Jakes;
import com.chocobar.fuutaro.medicare.model.User;

import org.json.JSONArray;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class UserProfile extends AsyncTask<String, Void, ArrayList<User>> implements PopulateSpinnerJakes.OnFinishedPopulate {
    //initiate some objects
    ImageView profilePhoto;
    EditText changeName, changeKTP, changeBirthPlace, viewBirthDate, changeAddress, changeHPNum, changeJamkesNum;
    Spinner changeJamkesType;
    private ProgressDialog loadUser;
    private ArrayList<User> arrUser = new ArrayList<>();
    private UserConfigurationActivity userActivity;
    private Context ctx;
    final int SPINNER_USER_PROFILE = 1;

    public OnFinishedPopulate listener = null;

    public interface OnFinishedPopulate {
        void onFinishedPopulate(ArrayList<User> dataUser);
    }

    //declare constructor
    public UserProfile(UserConfigurationActivity userActivity, Context ctx, OnFinishedPopulate listener) {
        this.userActivity = userActivity;
        this.listener = listener;
        this.ctx = ctx;
        loadUser = new ProgressDialog(ctx);
        initiate();

    }

    @Override
    protected void onPreExecute() {
        loadUser.setMessage("Tunggu sebentar...");
        loadUser.show();
    }

    //declared for do some action when AsyncTask finished working
    @Override
    protected void onPostExecute(ArrayList<User> users) {
        if(loadUser.isShowing())
            loadUser.dismiss();
        //some widgets set the value here
        updateUI(users);
        //use interface method so that AsyncTask can send data to Fragment
        listener.onFinishedPopulate(users);
    }

    @Override
    public void onFinishedPopulate(ArrayList<Jakes> dataJakes) {
    }

    @Override
    protected ArrayList<User> doInBackground(String... strings) {
        //calling request to webservice process from AsyncTaskActivity then store the return value
        List<Object> dataReceived = AsyncTaskActivity.doAsyncTask("User_getDataProfile", "`#" + strings[0]);
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
                return arrUser;
            //if request has been retrieved by SoapObject soapResponse
            else{
                String callSpExcecutionResult = soapResponse.getPropertyAsString("CallSpExcecutionResult");
                JSONArray jArray = new JSONArray(callSpExcecutionResult);
                //set item values and store at ArrayList
                User user = new User();
                user.setNama(jArray.getJSONObject(0).getString("txtNamaUser"));
                user.setNoKTP(jArray.getJSONObject(0).getString("txtNoKTP"));
                user.setTempatLahir(jArray.getJSONObject(0).getString("txtTempatLahir"));
                String dtLahir = jArray.getJSONObject(0).getString("dtTanggalLahir");
                String[] splitDateBirthData = dtLahir.split("T");
                dtLahir = splitDateBirthData[0];
                user.setTanggalLahir(dtLahir);
                splitDateBirthData = dtLahir.split("-");
                user.setThn_Lahir(splitDateBirthData[0]);
                user.setBln_Lahir(splitDateBirthData[1]);
                user.setTgl_Lahir(splitDateBirthData[2]);
                user.setAlamat(jArray.getJSONObject(0).getString("txtAlamat"));
                user.setTelp(jArray.getJSONObject(0).getString("txtPhone"));
                user.setIdJamkes(jArray.getJSONObject(0).getInt("intIDJaminanKesehatan"));
                user.setJamkes(jArray.getJSONObject(0).getString("txtJenisJamKes"));
                user.setNoJamkes(jArray.getJSONObject(0).getString("txtNoJaminanKesehatan"));
                user.setTxtAvatar(jArray.getJSONObject(0).getString("txtAvatar"));
                arrUser.add(user);
            }
            //if transaction failed
        }catch (Exception e){
            e.printStackTrace();
        }
        //operation will return the integer value for execute by onPostExecute method
        return arrUser;
    }

    private void initiate() {
        profilePhoto = userActivity.profilePhoto;
        changeName = userActivity.changeName;
        changeKTP = userActivity.changeKTP;
        changeBirthPlace = userActivity.changeBirthPlace;
        viewBirthDate = userActivity.viewBirthDate;
        changeAddress = userActivity.changeAddress;
        changeHPNum = userActivity.changeHPNum;
        changeJamkesNum = userActivity.changeJamkesNum;
        changeJamkesType = userActivity.changeJamkesType;
    }

    private void updateUI(ArrayList<User> users) {
        changeName.setText(users.get(0).getNama());
        changeKTP.setText(users.get(0).getNoKTP());
        changeBirthPlace.setText(users.get(0).getTempatLahir());
        viewBirthDate.setText(dateSet(Integer.parseInt(arrUser.get(0).getThn_Lahir()), Integer.parseInt(users.get(0).getBln_Lahir()) - 1, Integer.parseInt(users.get(0).getTgl_Lahir())));
        changeAddress.setText(users.get(0).getAlamat());
        changeHPNum.setText(users.get(0).getTelp());
        changeJamkesNum.setText(users.get(0).getNoJamkes());
        if(users.get(0).getTxtAvatar().equals("") || users.get(0).getTxtAvatar().equals(null) || users.get(0).getTxtAvatar().equals("null"))
            profilePhoto.setBackgroundResource(R.drawable.ic_profile);
        else{
            String stringBase64 = users.get(0).getTxtAvatar().substring(users.get(0).getTxtAvatar().indexOf(",") + 1);
            byte[] avatarByte = Base64.decode(stringBase64, Base64.DEFAULT);
            Bitmap imgDecode = BitmapFactory.decodeByteArray(avatarByte, 0, avatarByte.length);
            profilePhoto.setImageBitmap(imgDecode);
        }
        new PopulateSpinnerJakes(ctx, changeJamkesType, SPINNER_USER_PROFILE, new PopulateSpinnerJakes.OnFinishedPopulate() {
            @Override
            public void onFinishedPopulate(ArrayList<Jakes> dataJakes) {
            }
        }).execute();
        changeJamkesType.setSelection(users.get(0).getIdJamkes());
    }

    private String dateSet(int year, int month, int dayOfMonth){
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, dayOfMonth);
        Date dateSet = cal.getTime();
        SimpleDateFormat formatView = new SimpleDateFormat("dd MMMM yyyy");
        String format = formatView.format(dateSet);
        return format;
    }
}