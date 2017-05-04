package com.example.jaden.medicoapp.patientrecord.makeappointment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jaden.medicoapp.DBHelper;
import com.example.jaden.medicoapp.R;
import com.example.jaden.medicoapp.doctor.utils.AppointmentValues;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FragmentBookAppointment extends Fragment {
    private Button requestAptmntButton, prevDateButton, nextDateButton;
    private TextView apptmntText, apptmntSlot, mTextDocName, mTextDocEmail, mTextDocPhone;
    private EditText chooseDate;
    private CalendarView mCalendarView;
    private ImageView mImageDoc;
    private String mStartTimeSelected, mEndTimeSelected;
    private HorizontalScrollView timeSlots;
    private Context mContext;
    private AppointmentValues mAppointment = new AppointmentValues();
    private String[] mSlots = new String[13];
    private static final String TIME_SLOT = "http://rjtmobile.com/medictto/appointment_available.php?&";
    private static final String APPOINTMENT_URL = "http://rjtmobile.com/medictto/appointment_book.php?&";

    DBHelper myDBLHelper;
    SQLiteDatabase sqlDB;
    ArrayList<String> tuples;

    private RecyclerView recyclerView;
    private TimeSlotRecyclerViewAdapter adapter;
    private GridLayoutManager gridLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragment_book_appointment, container, false);
        requestAptmntButton = (Button) view.findViewById(R.id.req_apptmnt_button);
        mTextDocName = (TextView) view.findViewById(R.id.doctor_name_label);
        mImageDoc = (ImageView) view.findViewById(R.id.doc_photo);
        chooseDate = (EditText) view.findViewById(R.id.choose_date);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todaysDate = new Date();
        String date = dateFormat.format(todaysDate);
        chooseDate.setText(date);

        chooseDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            String dateRequested;
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    dateRequested = chooseDate.getText().toString();
                    requestInfoFromDate(dateRequested,v.getRootView());
                }

            }
        });
        return view;
    }

    private void requestInfoFromDate(String dateRequested, View view)
    {
        tuples = new ArrayList<>();
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM "+DBHelper.PAT_TABLENAME+ "WHERE "+DBHelper.PAT_DATE+" LIKE "+dateRequested, null);
        cursor.moveToFirst();
        do {
            if(cursor.getCount() == 0)
                break;
            String itemId = cursor.getString(cursor.getColumnIndex(DBHelper.ITEMID));
            String date = cursor.getString(cursor.getColumnIndex(DBHelper.PAT_DATE));
            String slot = cursor.getString(cursor.getColumnIndex(DBHelper.PAT_SLOT));

            String tuple = date+"-"+slot;
            tuples.add(tuple);

        }while(cursor.moveToNext());

        recyclerView = (RecyclerView) view.findViewById(R.id.time_selection_grid);
        recyclerView.setHasFixedSize(false);
        gridLayoutManager = new GridLayoutManager(view.getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new TimeSlotRecyclerViewAdapter(getActivity(), tuples);
        recyclerView.setAdapter(adapter);
    }

//    private void getInfo(String date) {
//        mAppointment.setPatientID("400");
//        mAppointment.setDoctorId("101");
//        // get slot infor
//        apptmntText.setText(date);
//        String url = TIME_SLOT + "&doctorID=" + mAppointment.getDoctorId() + "&currentdate=" + date;
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                try {
//                    JSONArray array = jsonObject.getJSONArray("appointment slot");
//                    JSONObject object = array.getJSONObject(0);
//                    VolleyLog.d("volley", object);
//                    mSlots[1] = object.getString("slot_1");
//                    mSlots[2] = object.getString("slot_2");
//                    mSlots[3] = object.getString("slot_3");
//                    mSlots[4] = object.getString("slot_4");
//                    mSlots[5] = object.getString("slot_5");
//                    mSlots[6] = object.getString("slot_6");
//                    mSlots[7] = object.getString("slot_7");
//                    mSlots[8] = object.getString("slot_8");
//                    mSlots[9] = object.getString("slot_9");
//                    mSlots[10] = object.getString("slot_10");
//                    mSlots[11] = object.getString("slot_11");
//                    mSlots[12] = object.getString("slot_12");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                setTimeSlots();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                VolleyLog.d("volley", "Error: " + volleyError.getMessage());
//                for (int i = 0; i < mSlots.length; i++) {
//                    mSlots[i] = "1";
//                }
//                setTimeSlots();
//            }
//        });
//        VolleyController.getInstance().addToRequestQueue(jsonObjectRequest);
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.frag_title_book_appointment);
    }

}
