package com.example.jaden.medicoapp.patientrecord.makeappointment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jaden.medicoapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by snehalsutar on 3/1/16.
 */

public class FragmentDocSpeciality extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    View mRootView;
    ArrayList<DocSpecItems> arrayList;
    FragmentDocSpecialityAdapter adapter;
    GridView gridView;
    FragmentDocSpecInterface docSpecInterface;
    ImageView generalDocImg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_doc_speciality, container, false);
        docSpecInterface = (FragmentDocSpecInterface) getActivity();
        gridView = (GridView) mRootView.findViewById(R.id.doc_speciality_grid);
        generalDocImg = (ImageView) mRootView.findViewById(R.id.doc_spec_image);
        Picasso.with(getActivity())
                .load("http://i.imgur.com/BKAMqGu.png")
                .placeholder(R.drawable.medicinethumbnail)
                .fit()
                .centerCrop()
                .into(generalDocImg);
        generalDocImg.setOnClickListener(this);
        arrayList = new ArrayList<>();
        arrayList.add(new DocSpecItems("http://i.imgur.com/xMnrGOd.png", "DENTAL"));
        arrayList.add(new DocSpecItems("http://i.imgur.com/KRKgaef.png", "PEDIATRICIAN"));
        arrayList.add(new DocSpecItems("http://i.imgur.com/NNIrRXS.png", "ORTHOPEDIC"));
        arrayList.add(new DocSpecItems("http://i.imgur.com/tuMOTPs.png", "NEUROLOGIST"));
        arrayList.add(new DocSpecItems("http://i.imgur.com/soeBaHu.png", "CARDIOLOGIST"));
        arrayList.add(new DocSpecItems("http://i.imgur.com/vSHNevc.png", "DERMATOLOGIST"));
        arrayList.add(new DocSpecItems("http://i.imgur.com/qRtgxN7.png", "HEMATOLOGIST"));
        arrayList.add(new DocSpecItems("http://i.imgur.com/eHuqoWY.png", "RADIOLOGIST"));
        arrayList.add(new DocSpecItems("http://i.imgur.com/UlkqQ1i.png", "OPTHOMOLOGIST"));

        adapter = new FragmentDocSpecialityAdapter(arrayList, getActivity());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(FragmentDocSpeciality.this);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.frag_title_select_field);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("click", "item click " + position );
        docSpecInterface.callViewDoctorList();
    }

    @Override
    public void onClick(View v) {
        Log.d("click", " click ");
        docSpecInterface.callViewDoctorList();
    }
}

class FragmentDocSpecialityAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<DocSpecItems> itemsArrayList;

    FragmentDocSpecialityAdapter(ArrayList<DocSpecItems> arrayList, Context context) {
        mContext = context;
        itemsArrayList = arrayList;
    }

    @Override
    public int getCount() {
        return itemsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = inflater.inflate(R.layout.fragment_doc_speciality_item, null);

            DocSpecItems docSpecItems = itemsArrayList.get(position);

            // set value into text view
            TextView textView = (TextView) gridView.findViewById(R.id.spec_name);
            textView.setText(docSpecItems.getSpecName());

            // set image based on selected text
            ImageView imageView = (ImageView) gridView.findViewById(R.id.doc_spec_image);
            Picasso.with(mContext)
                    .load(docSpecItems.getImageUrl())
                    .placeholder(R.drawable.medicinethumbnail)
                    .fit()
                    .centerCrop()
                    .into(imageView);
        } else {
            gridView = convertView;
        }

        return gridView;
    }
}
