package com.example.jaden.medicoapp.patientrecord.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jaden.medicoapp.R;

public class FragmentHome extends Fragment {
    TextView mWelcomeText ;
    Context mContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_patient_record, container,false);
        mContext = getActivity();
        mWelcomeText = (TextView) rootView.findViewById(R.id.welcome_text);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_pill);
        fab.setVisibility(View.GONE);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
