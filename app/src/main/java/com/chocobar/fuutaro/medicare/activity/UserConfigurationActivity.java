package com.chocobar.fuutaro.medicare.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.UnicodeSetSpanner;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chocobar.fuutaro.medicare.AsyncTasks.UpdateProfile;
import com.chocobar.fuutaro.medicare.AsyncTasks.UserProfile;
import com.chocobar.fuutaro.medicare.R;

import com.chocobar.fuutaro.medicare.model.Jakes;
import com.chocobar.fuutaro.medicare.model.User;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UserConfigurationActivity extends AppCompatActivity implements View.OnClickListener, UserProfile.OnFinishedPopulate{

    public ImageView profilePhoto;
    public EditText changeName, changeKTP, changeBirthPlace, viewBirthDate, changeAddress, changeHPNum, changeJamkesNum;
    public Spinner changeJamkesType;
    ImageView changeBirthDate;
    TextView txtChangePhoto;

    private ArrayList<User> arrUser = new ArrayList<>();

    String dateBirth, imgBase64;
    int thnLahir, blnLahir, tglLahir;
    private Uri mCropPhoto;

    private DatePickerDialog.OnDateSetListener mDateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_configuration);

        setupUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_configuration, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_save_config:
                new UpdateProfile(UserConfigurationActivity.this).execute(
                        changeName.getText().toString(), changeKTP.getText().toString(),
                        changeBirthPlace.getText().toString(),dateBirth, changeAddress.getText().toString(),
                        changeHPNum.getText().toString(), Integer.toString(changeJamkesType.getSelectedItemPosition()),
                        changeJamkesNum.getText().toString(), imgBase64);
                return true;
        }
        return false;
    }

    @Override
    public void onFinishedPopulate(ArrayList<User> dataUser) {
        arrUser = new ArrayList<>(dataUser);
        imgBase64 = arrUser.get(0).getTxtAvatar();
        dateBirth = arrUser.get(0).getTanggalLahir();
        thnLahir = Integer.parseInt(arrUser.get(0).getThn_Lahir());
        blnLahir = Integer.parseInt(arrUser.get(0).getBln_Lahir()) - 1;
        tglLahir = Integer.parseInt(arrUser.get(0).getTgl_Lahir());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtChangePhoto:
                pickImage(v);
                break;
            case R.id.imgChangeBirthDate:
                //showing DatePicker dialog
                DatePickerDialog dialog = new DatePickerDialog(UserConfigurationActivity.this,
                        android.R.style.Theme_Holo_Dialog, mDateListener, thnLahir, blnLahir, tglLahir);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setTitle("Ubah tanggal lahir Anda");
                dialog.show();
                break;
        }
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri imgUri = CropImage.getPickImageResultUri(this, data);
            if(CropImage.isReadExternalStoragePermissionsRequired(this, imgUri)){
                mCropPhoto = imgUri;
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            }else{
                startCropImg(imgUri);
            }
        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri imgResultUri = result.getUri();
                InputStream imgStream = null;
                try{
                    imgStream = this.getContentResolver().openInputStream(imgResultUri);
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }
                Bitmap imgBitmap = BitmapFactory.decodeStream(imgStream);
                imgBase64 = encodeToBase64(imgBitmap);
                profilePhoto.setImageURI(imgResultUri);
            }else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Toast.makeText(this, "Mohon maaf, ada kesalahan. Silakan coba lagi...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE){
            if(mCropPhoto != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startCropImg(mCropPhoto);
            }else{
                Toast.makeText(this, "Mohon maaf, ada kesalahan. Silakan coba lagi...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setupUI() {
        getSupportActionBar().setTitle("Pengaturan Profil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profilePhoto = findViewById(R.id.imgUserPhoto);
        txtChangePhoto = findViewById(R.id.txtChangePhoto);
        changeName = findViewById(R.id.editName);
        changeKTP = findViewById(R.id.editKTP);
        changeBirthPlace = findViewById(R.id.editBirthPlace);
        viewBirthDate = findViewById(R.id.editBirthDate);
        changeBirthDate = findViewById(R.id.imgChangeBirthDate);
        changeAddress = findViewById(R.id.editAddress);
        changeHPNum = findViewById(R.id.editHPNum);
        changeJamkesType = findViewById(R.id.spinnerChangeJamkesType);
        changeJamkesNum = findViewById(R.id.editJamkesNum);

        viewBirthDate.setKeyListener(null);

        new UserProfile(UserConfigurationActivity.this, UserConfigurationActivity.this, this).execute("1");

        txtChangePhoto.setOnClickListener(this);

        changeBirthDate.setOnClickListener(this);

        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                thnLahir = year;
                blnLahir = month;
                tglLahir = dayOfMonth;
                dateBirth = setDateBirth(year, month, dayOfMonth).get(0);
                viewBirthDate.setText(setDateBirth(year, month, dayOfMonth).get(1));
            }
        };
    }

    //method for set ArrayList<String> setDateToday that will used when date is generated by system
    private ArrayList<String> setDateBirth(int year, int month, int dayOfMonth){
        ArrayList<String> arrDateBirth = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, dayOfMonth);
        Date dateSet = cal.getTime();
        SimpleDateFormat formatSend = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat formatView = new SimpleDateFormat("dd MMMM yyyy");
        arrDateBirth.add(formatSend.format(dateSet));
        arrDateBirth.add(formatView.format(dateSet));
        return arrDateBirth;
    }

    public void pickImage(View v) {
        CropImage.startPickImageActivity(this);
    }

    public String encodeToBase64(Bitmap imgBitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 40, outputStream);
        byte[] imgByte = outputStream.toByteArray();
        String imgEncoded = Base64.encodeToString(imgByte, Base64.DEFAULT);
        return imgEncoded;
    }

    private void startCropImg(Uri imgUri) {
        CropImage.activity(imgUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAspectRatio(1, 1)
                .setInitialCropWindowPaddingRatio(0)
                .setMultiTouchEnabled(true)
                .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                .start(this);
    }
}
