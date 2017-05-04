package com.example.jaden.medicoapp.patientrecord.makeappointment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaden.medicoapp.DBHelper;
import com.example.jaden.medicoapp.R;
import com.example.jaden.medicoapp.doctor.utils.AppointmentValues;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FragmentBookAppointment extends Fragment {
    private Button requestAptmntButton, prevDateButton, nextDateButton;
    private TextView mTextDocName, mTextDocSpecialty;
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

    private DBHelper dbHelper;
    private SQLiteDatabase sqlDB;
    private ArrayList<String> tuples;
    public static String dateSlotSelected;

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
        mTextDocSpecialty = (TextView) view.findViewById(R.id.doctor_specialty_label);
        mImageDoc = (ImageView) view.findViewById(R.id.doctor_photo_book_appt);
        chooseDate = (EditText) view.findViewById(R.id.choose_date);
        prevDateButton = (Button) view.findViewById(R.id.prev_date);
        nextDateButton = (Button) view.findViewById(R.id.next_date);

        mTextDocName.setText("Doctor Adnan");
        mTextDocSpecialty.setText("Master of the universe");
        mImageDoc.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.doctorpic1));

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todaysDate = new Date();
        String date = dateFormat.format(todaysDate);
        chooseDate.setText(date);

        dbHelper = new DBHelper(getContext());
        sqlDB = dbHelper.getWritableDatabase();

        recyclerView = (RecyclerView) view.findViewById(R.id.time_selection_grid);
        recyclerView.setHasFixedSize(false);
        gridLayoutManager = new GridLayoutManager(view.getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        requestInfoFromDate(date);

        adapter = new TimeSlotRecyclerViewAdapter(getActivity(), tuples);
        adapter.notifyData(tuples);
        recyclerView.setAdapter(adapter);

        chooseDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            String dateRequested;
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    dateRequested = chooseDate.getText().toString();
                    Log.i("JIAN", "DateRequested: "+dateRequested);
                    requestInfoFromDate(dateRequested);
                }
            }
        });

        prevDateButton.setOnClickListener(new View.OnClickListener() {
            String dateRequested;
            @Override
            public void onClick(View v) {
                String dateRequestedStr = chooseDate.getText().toString();
                int year = Integer.parseInt(dateRequestedStr.substring(0,dateRequestedStr.indexOf("-")));
                int month = Integer.parseInt(dateRequestedStr.substring(5,7));
                int date = Integer.parseInt(dateRequestedStr.substring(8))-1;
                Log.i("JIAN", "DATE: "+year+"-"+month+"-"+date);

                requestInfoFromDate(year+"-0"+month+"-0"+date);
                chooseDate.setText(year+"-0"+month+"-0"+date);
            }
        });

        nextDateButton.setOnClickListener(new View.OnClickListener() {
            String dateRequested;
            @Override
            public void onClick(View v) {
                String dateRequestedStr = chooseDate.getText().toString();
                int year = Integer.parseInt(dateRequestedStr.substring(0,dateRequestedStr.indexOf("-")));
                int month = Integer.parseInt(dateRequestedStr.substring(5,7));
                int date = Integer.parseInt(dateRequestedStr.substring(8))+1;
                Log.i("JIAN", "DATE: "+year+"-"+month+"-"+date);
                requestInfoFromDate(year+"-0"+month+"-0"+date);
                chooseDate.setText(year+"-0"+month+"-0"+date);
            }
        });

        requestAptmntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateSlotSelected == null)
                    Toast.makeText(getContext(), "Please select a slot first.", Toast.LENGTH_SHORT).show();
                else
                {
                    String date = dateSlotSelected.substring(0,dateSlotSelected.indexOf("/"));
                    String slot = dateSlotSelected.substring(dateSlotSelected.indexOf("/")+1);
                    Log.i("JIAN", date +" at "+ slot+" confirmed");
                    Snackbar.make(v, "Appointment confirmed", Snackbar.LENGTH_SHORT);
                    ContentValues cv = new ContentValues();
                    cv.put(dbHelper.PAT_DATE, date);
                    cv.put(dbHelper.PAT_SLOT, slot);
                    // put fields in table
                    sqlDB.insert(dbHelper.PAT_TABLENAME, null, cv);
                    Toast.makeText(getContext(), "Appointment confirmed with Doctor Adnan", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }

            }
        });
        return view;
    }

    private void requestInfoFromDate(String dateRequested)
    {
        // request the availability hours first
        tuples = new ArrayList<>();
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM "+DBHelper.DOC_TABLENAME, null);
        cursor.moveToFirst();
        do {
            if(cursor.getCount() == 0)
                break;
            String date = dateRequested;
            String slot1 = cursor.getString(cursor.getColumnIndex(DBHelper.DOC_SLOT1));
            String slot2 = cursor.getString(cursor.getColumnIndex(DBHelper.DOC_SLOT2));
            String slot3 = cursor.getString(cursor.getColumnIndex(DBHelper.DOC_SLOT3));
            String slot4 = cursor.getString(cursor.getColumnIndex(DBHelper.DOC_SLOT4));
            String slot5 = cursor.getString(cursor.getColumnIndex(DBHelper.DOC_SLOT5));
            String slot6 = cursor.getString(cursor.getColumnIndex(DBHelper.DOC_SLOT6));
            String slot7 = cursor.getString(cursor.getColumnIndex(DBHelper.DOC_SLOT7));
            String slot8 = cursor.getString(cursor.getColumnIndex(DBHelper.DOC_SLOT8));
            String slot9 = cursor.getString(cursor.getColumnIndex(DBHelper.DOC_SLOT9));
            String slot10 = cursor.getString(cursor.getColumnIndex(DBHelper.DOC_SLOT10));
            String slot11 = cursor.getString(cursor.getColumnIndex(DBHelper.DOC_SLOT11));
            String slot12 = cursor.getString(cursor.getColumnIndex(DBHelper.DOC_SLOT12));

            String tuple1 = date+"/"+slot1;
            String tuple2 = date+"/"+slot2;
            String tuple3 = date+"/"+slot3;
            String tuple4 = date+"/"+slot4;
            String tuple5 = date+"/"+slot5;
            String tuple6 = date+"/"+slot6;
            String tuple7 = date+"/"+slot7;
            String tuple8 = date+"/"+slot8;
            String tuple9 = date+"/"+slot9;
            String tuple10 = date+"/"+slot10;
            String tuple11 = date+"/"+slot11;
            String tuple12 = date+"/"+slot12;

            Log.i("JIAN", tuple1);
            Log.i("JIAN", tuple2);
            Log.i("JIAN", tuple3);
            Log.i("JIAN", tuple4);
            Log.i("JIAN", tuple5);
            Log.i("JIAN", tuple6);
            Log.i("JIAN", tuple7);
            Log.i("JIAN", tuple8);
            Log.i("JIAN", tuple9);
            Log.i("JIAN", tuple10);
            Log.i("JIAN", tuple11);
            Log.i("JIAN", tuple12);

            tuples.add(tuple1);
            tuples.add(tuple2);
            tuples.add(tuple3);
            tuples.add(tuple4);
            tuples.add(tuple5);
            tuples.add(tuple6);
            tuples.add(tuple7);
            tuples.add(tuple8);
            tuples.add(tuple9);
            tuples.add(tuple10);
            tuples.add(tuple11);
            tuples.add(tuple12);


        }while(cursor.moveToNext());

        // then request the hours booked on this date and remove from availability
        cursor = sqlDB.rawQuery("SELECT * FROM "+DBHelper.PAT_TABLENAME, null);
        Log.i("JIAN", cursor.getCount()+"");
        cursor.moveToFirst();
        do {
            if (cursor.getCount() == 0)
                break;
            String tuple = cursor.getString(cursor.getColumnIndex(DBHelper.PAT_DATE))+"/"+cursor.getString(cursor.getColumnIndex(DBHelper.PAT_SLOT));
            Log.i("JIAN", "patient slot removing: "+tuple);
            tuples.remove(tuple);
        }while(cursor.moveToNext());
        if(adapter != null)
            adapter.notifyData(tuples);
    }

    public static void setDateSlotSelection(String dateSlot)
    {
        dateSlotSelected = dateSlot;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.frag_title_book_appointment);
    }

}
