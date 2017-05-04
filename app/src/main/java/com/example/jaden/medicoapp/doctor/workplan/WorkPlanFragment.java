package com.example.jaden.medicoapp.doctor.workplan;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.jaden.medicoapp.DBHelper;
import com.example.jaden.medicoapp.R;
import com.example.jaden.medicoapp.patientrecord.utils.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.jaden.medicoapp.DBHelper.DOC_TABLENAME;

public class WorkPlanFragment extends Fragment {
    private View rootView;
    private CalendarView mCalendarView;
    private Button mButtonConfirm, mButtonCancel;
    private CheckBox mCheck1, mCheck2, mCheck3, mCheck4, mCheck5, mCheck6, mCheck7, mCheck8, mCheck9, mCheck10, mCheck11,mCheck12;
    private String[] timeSlots;
    private static final String TIME_SLOT = "http://rjtmobile.com/medictto/appointment_available.php?&";
    private final String doctorID = "101";

    DBHelper dbHelper;
    SQLiteDatabase sqlDB;

    public WorkPlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_work_plan, container, false);
        timeSlots =  getContext().getResources().getStringArray(R.array.timings_array);
        initialView();
        setListener();
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(calendar.getTime());
        getInfor(date);
        dbHelper = new DBHelper(getContext());
        sqlDB = dbHelper.getWritableDatabase();
        return rootView;
    }

    private void initialView() {
        mCalendarView = (CalendarView) rootView.findViewById(R.id.work_calendarView);
        mCheck1 = (CheckBox) rootView.findViewById(R.id.work_slot1);
        mCheck2 = (CheckBox) rootView.findViewById(R.id.work_slot2);
        mCheck3 = (CheckBox) rootView.findViewById(R.id.work_slot3);
        mCheck4 = (CheckBox) rootView.findViewById(R.id.work_slot4);
        mCheck5 = (CheckBox) rootView.findViewById(R.id.work_slot5);
        mCheck6 = (CheckBox) rootView.findViewById(R.id.work_slot6);
        mCheck7 = (CheckBox) rootView.findViewById(R.id.work_slot7);
        mCheck8 = (CheckBox) rootView.findViewById(R.id.work_slot8);
        mCheck9 = (CheckBox) rootView.findViewById(R.id.work_slot9);
        mCheck10 = (CheckBox) rootView.findViewById(R.id.work_slot10);
        mCheck11 = (CheckBox) rootView.findViewById(R.id.work_slot11);
        mCheck12 = (CheckBox) rootView.findViewById(R.id.work_slot12);
        mButtonConfirm = (Button) rootView.findViewById(R.id.work_confirm);
        mButtonCancel = (Button) rootView.findViewById(R.id.work_cancel);
    }

    private void setListener(){
        mCheck1.setText(timeSlots[0]);
        mCheck2.setText(timeSlots[1]);
        mCheck3.setText(timeSlots[2]);
        mCheck4.setText(timeSlots[3]);
        mCheck5.setText(timeSlots[4]);
        mCheck6.setText(timeSlots[5]);
        mCheck7.setText(timeSlots[6]);
        mCheck8.setText(timeSlots[7]);
        mCheck9.setText(timeSlots[8]);
        mCheck10.setText(timeSlots[9]);
        mCheck11.setText(timeSlots[10]);
        mCheck12.setText(timeSlots[11]);
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {

            ContentValues contentValues = new ContentValues();

            @Override
            public void onClick(View view) {
                if (mCheck1.isChecked()){
                    String s = mCheck1.getText().toString();
                    contentValues.put(dbHelper.DOC_SLOT1, s);
                }
                if (mCheck2.isChecked()){
                    String s = mCheck2.getText().toString();
                    contentValues.put(dbHelper.DOC_SLOT2, s);
                }
                if (mCheck3.isChecked()){
                    String s = mCheck3.getText().toString();
                    contentValues.put(dbHelper.DOC_SLOT3, s);
                }
                if (mCheck4.isChecked()){
                    String s = mCheck4.getText().toString();
                    contentValues.put(dbHelper.DOC_SLOT4, s);
                }
                if (mCheck5.isChecked()){
                    String s = mCheck5.getText().toString();
                    contentValues.put(dbHelper.DOC_SLOT5, s);
                }
                if (mCheck6.isChecked()){
                    String s = mCheck6.getText().toString();
                    contentValues.put(dbHelper.DOC_SLOT6, s);
                }
                if (mCheck7.isChecked()){
                    String s = mCheck7.getText().toString();
                    contentValues.put(dbHelper.DOC_SLOT7, s);
                }
                if (mCheck8.isChecked()){
                    String s = mCheck8.getText().toString();
                    contentValues.put(dbHelper.DOC_SLOT8, s);
                }
                if (mCheck9.isChecked()){
                    String s = mCheck9.getText().toString();
                    contentValues.put(dbHelper.DOC_SLOT9, s);
                }
                if (mCheck10.isChecked()){
                    String s = mCheck10.getText().toString();
                    contentValues.put(dbHelper.DOC_SLOT10, s);
                }
                if (mCheck11.isChecked()){
                    String s = mCheck11.getText().toString();
                    contentValues.put(dbHelper.DOC_SLOT11, s);
                }
                if (mCheck12.isChecked()){
                    String s = mCheck12.getText().toString();
                    contentValues.put(dbHelper.DOC_SLOT12, s);
                }

                contentValues.put(dbHelper.DOCID, doctorID);
                Log.i("table", contentValues.toString());
                sqlDB.insert(DOC_TABLENAME, null, contentValues);
                Snackbar.make(rootView, "Work plan confirmed", Snackbar.LENGTH_LONG).show();
            }
        });
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCheck1.setChecked(false);
                mCheck2.setChecked(false);
                mCheck3.setChecked(false);
                mCheck4.setChecked(false);
                mCheck5.setChecked(false);
                mCheck6.setChecked(false);
                mCheck7.setChecked(false);
                mCheck8.setChecked(false);
                mCheck9.setChecked(false);
                mCheck10.setChecked(false);
                mCheck11.setChecked(false);
                mCheck12.setChecked(false);
                Snackbar.make(rootView, "Work plan canceled", Snackbar.LENGTH_LONG).show();
                sqlDB.execSQL("delete from "+ DOC_TABLENAME);
            }
        });
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                i1++;
                String month;
                if (i1 < 10) {
                    month = "0" + i1;
                } else {
                    month = String.valueOf(i1);
                }
                String day;
                if (i2 < 10) {
                    day = "0" + i2;
                } else {
                    day = String.valueOf(i2);
                }
                String s = "" + i + "-" + month + "-" + day;
                getInfor(s);
            }
        });
    }

    private void getInfor(String date) {
//        String doctorID = "101";
        // get slot infor
        // date = "2017-02-07";
        String url = TIME_SLOT + "&doctorID=" + doctorID + "&currentdate=" + date;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray array = jsonObject.getJSONArray("appointment slot");
                    JSONObject object = array.getJSONObject(0);
                    VolleyLog.d("volley", object);
                    String s = object.getString("slot_1");
                    if (s != null) {
                        mCheck1.setChecked(true);
                    }
                    s = object.getString("slot_2");
                    if (s != null) {
                        mCheck2.setChecked(true);
                    }
                    s = object.getString("slot_3");
                    if (s != null) {
                        mCheck3.setChecked(true);
                    }
                    s = object.getString("slot_4");
                    if (s != null) {
                        mCheck4.setChecked(true);
                    }
                    s = object.getString("slot_5");
                    if (s != null) {
                        mCheck5.setChecked(true);
                    }
                    s = object.getString("slot_6");
                    if (s != null) {
                        mCheck6.setChecked(true);
                    }
                    s = object.getString("slot_7");
                    if (s != null) {
                        mCheck7.setChecked(true);
                    }
                    s = object.getString("slot_8");
                    if (s != null) {
                        mCheck8.setChecked(true);
                    }
                    s = object.getString("slot_9");
                    if (s != null) {
                        mCheck9.setChecked(true);
                    }
                    s = object.getString("slot_10");
                    if (s != null) {
                        mCheck10.setChecked(true);
                    }
                    s = object.getString("slot_11");
                    if (s != null) {
                        mCheck11.setChecked(true);
                    }
                    s = object.getString("slot_12");
                    if (s != null) {
                        mCheck12.setChecked(true);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d("volley", "Error: " + volleyError.getMessage());
                mCheck1.setChecked(false);
                mCheck2.setChecked(false);
                mCheck3.setChecked(false);
                mCheck4.setChecked(false);
                mCheck5.setChecked(false);
                mCheck6.setChecked(false);
                mCheck7.setChecked(false);
                mCheck8.setChecked(false);
                mCheck9.setChecked(false);
                mCheck10.setChecked(false);
                mCheck11.setChecked(false);
                mCheck12.setChecked(false);
            }
        });
        VolleyController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}
