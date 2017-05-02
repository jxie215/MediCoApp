package com.example.jaden.medicoapp.doctor.request;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jaden.medicoapp.R;

public class RequestFragment extends Fragment {

    private View rootView;
    private RecyclerView mRecyclerView;

    public RequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_request, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.request_container);
        mRecyclerView.setAdapter(new RequestAdapter(getContext()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootView;
    }

}
