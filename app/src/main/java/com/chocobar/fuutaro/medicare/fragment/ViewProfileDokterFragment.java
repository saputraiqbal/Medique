package com.chocobar.fuutaro.medicare.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chocobar.fuutaro.medicare.R;

public class ViewProfileDokterFragment extends DialogFragment {
    private ImageView imgProfile;
    private TextView txtDokter;
    private TextView txtProfile;
    private Button btnBack;

    private String base64Avatar;
    private String getDokterName;
    private String getDokterProfile;

    public ViewProfileDokterFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dokter_profile, container, false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static ViewProfileDokterFragment newInstance(String base64Avatar, String getDokterName, String getDokterProfile){
        ViewProfileDokterFragment viewProfile = new ViewProfileDokterFragment();
        Bundle args = new Bundle();
        args.putString("getImg", base64Avatar);
        args.putString("getName", getDokterName);
        args.putString("getProfile", getDokterProfile);
        viewProfile.setArguments(args);
        return viewProfile;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgProfile = view.findViewById(R.id.imgProfile);
        txtDokter = view.findViewById(R.id.txtNameProfile);
        txtProfile = view.findViewById(R.id.txtProfileDokter);
        btnBack = view.findViewById(R.id.btnBackToDetailActivity);

        base64Avatar = getArguments().getString("getImg");
        getDokterName = getArguments().getString("getName");
        getDokterProfile = getArguments().getString("getProfile");

        if(base64Avatar.equals("null"))
            imgProfile.setBackgroundResource(R.drawable.ic_profile);
        else{
            base64Avatar = base64Avatar.substring(base64Avatar.indexOf(",") + 1);
            byte[] avatarByte = Base64.decode(base64Avatar, Base64.DEFAULT);
            Bitmap imgDecode = BitmapFactory.decodeByteArray(avatarByte, 0, avatarByte.length);
            imgProfile.setImageBitmap(imgDecode);
        }
        txtDokter.setText(getDokterName);
        txtProfile.setText(getDokterProfile);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }
}
