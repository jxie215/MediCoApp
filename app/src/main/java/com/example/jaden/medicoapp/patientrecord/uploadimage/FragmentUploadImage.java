package com.example.jaden.medicoapp.patientrecord.uploadimage;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaden.medicoapp.DBHelper;
import com.example.jaden.medicoapp.R;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.jaden.medicoapp.DBHelper.IMAGE_TABLE;

/**
 * Created by snehalsutar on 2/18/16.
 */
public class FragmentUploadImage extends Fragment implements View.OnClickListener {

    private static final int IMAGE_PICK_REQUEST_CODE = 1;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 2;
    EditText img_name, doctor_name, edit;
    Button chose_img, search;
    Spinner spinner;
    Context mContext;
    Activity mActivity;
    DBHelper dbHelper;
    SQLiteDatabase sqlDB;
    ContentValues contentValues;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerAdapter mAdapter;
    ArrayList<Photo> mPhotosList;


    /**********  File Path *************/
    Uri imageUri                      = null;

    /***********************************************************************************************
     * ON CREATE VIEW for fragment.
     */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_upload_images, container, false);
        mContext=getActivity();
        mActivity = getActivity();

        img_name = (EditText) rootView.findViewById(R.id.img_name);
        doctor_name = (EditText) rootView.findViewById(R.id.doctor_name);
        edit = (EditText) rootView.findViewById(R.id.edit);

        chose_img = (Button) rootView.findViewById(R.id.chose_img);
        search = (Button) rootView.findViewById(R.id.search);
        chose_img.setOnClickListener(this);
        search.setOnClickListener(this);
        dbHelper = new DBHelper(getContext());
        sqlDB = dbHelper.getWritableDatabase();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.upload_img_recyclerview);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mPhotosList = new ArrayList<>();
        mAdapter = new RecyclerAdapter(mPhotosList);
        mRecyclerView.setAdapter(mAdapter);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }

        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("Date");
        adapter.add("Name");
        adapter.add("Doctor");
        adapter.add("Select Search Category");
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount()); //display hint
        return rootView;
    }

    /***********************************************************************************************
     * On Click Listeners for Choose Photo From Gallery and Click Photo from Camera.
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chose_img:
                if(!img_name.getText().toString().isEmpty() && !doctor_name.getText().toString().isEmpty()) {
                    choseImage();
                }else{
                    Toast.makeText(getActivity(),"Required: Photo Name & Doctor Name",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.search:
                chooseImageFromDatabase();
                break;
        }
    }


    private void choseImage() {
        /*************************** Camera Intent Start ************************/

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }

        /*************************** Camera Intent End ************************/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Log.i("Abdul", String.valueOf(data.getExtras().get("data")));
            Bitmap yourBitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            yourBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();
            String date = DateFormat.getDateTimeInstance().format(new Date());
            contentValues = new ContentValues();
            contentValues.put(dbHelper.DOCTOR, doctor_name.getText().toString());
            contentValues.put(dbHelper.IMAGE_NAME, img_name.getText().toString());
            contentValues.put(dbHelper.IMAGE, bArray);
            contentValues.put(dbHelper.IMAGE_DATE,date);
            sqlDB.insert(IMAGE_TABLE, null, contentValues);
            img_name.setText("");
            doctor_name.setText("");
            Toast.makeText(getActivity(),"Image Added Successfully",Toast.LENGTH_SHORT).show();
        }
        else if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_CANCELED){
            Toast.makeText(getActivity(),"Image Upload Cancelled",Toast.LENGTH_SHORT).show();
        }
    }

    private void chooseImageFromDatabase() {
        mPhotosList.clear();
        mAdapter.notifyDataSetChanged();
        Cursor cursor = null;
        if(!edit.getText().toString().isEmpty()) {

            if(spinner.getSelectedItem().toString().compareTo("Name")==0){
                cursor = sqlDB.rawQuery("SELECT * FROM " + dbHelper.IMAGE_TABLE + " where "
                        + dbHelper.IMAGE_NAME + " LIKE '%" + edit.getText().toString() +"%'", null);
            }
            else if(spinner.getSelectedItem().toString().compareTo("Date")==0){
                cursor = sqlDB.rawQuery("SELECT * FROM " + dbHelper.IMAGE_TABLE + " where "
                        + dbHelper.IMAGE_DATE + " LIKE '%" + edit.getText().toString() +"%'", null);
            }
            else if(spinner.getSelectedItem().toString().compareTo("Doctor")==0){
                cursor = sqlDB.rawQuery("SELECT * FROM " + dbHelper.IMAGE_TABLE + " where "
                        + dbHelper.DOCTOR + " LIKE '%" + edit.getText().toString() +"%'", null);
            }else{
                Toast.makeText(getActivity(),"Select a Category from List",Toast.LENGTH_SHORT).show();
                return;
            }
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Photo photo = new Photo();
                    byte[] byteArray = cursor.getBlob(4);

                    Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    photo.setName(cursor.getString(cursor.getColumnIndex(dbHelper.IMAGE_NAME)));
                    photo.setDoctor(cursor.getString(cursor.getColumnIndex(dbHelper.DOCTOR)));
                    photo.setDate(cursor.getString(cursor.getColumnIndex(dbHelper.IMAGE_DATE)));
                    photo.setImage(bm);
                    mPhotosList.add(photo);
                    mAdapter.notifyDataSetChanged();
                } while (cursor.moveToNext());
            } else {
                Toast.makeText(getActivity(), "No Such Record in Database", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getActivity(), "Enter a search term", Toast.LENGTH_SHORT).show();
        }
        edit.setText("");
    }

}
