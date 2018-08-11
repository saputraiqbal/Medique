package com.chocobar.fuutaro.medicare;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chocobar.fuutaro.medicare.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FaskesFragment_recs extends Fragment {

    TextView txt1;
    public FaskesFragment_recs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faskes, container, false);
        txt1 = view.findViewById(R.id.txtTest);
        return view;
    }

}
