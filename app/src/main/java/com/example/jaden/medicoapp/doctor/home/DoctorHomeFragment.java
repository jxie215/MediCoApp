package com.example.jaden.medicoapp.doctor.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jaden.medicoapp.R;
import com.example.jaden.medicoapp.doctor.utils.RefreshLists;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorHomeFragment extends Fragment {


    public DoctorHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        new RefreshLists().refreshData("101");
        return inflater.inflate(R.layout.fragment_doctor_home, container, false);
    }

}
