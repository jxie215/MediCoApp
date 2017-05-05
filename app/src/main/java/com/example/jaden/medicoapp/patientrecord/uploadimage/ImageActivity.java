package com.example.jaden.medicoapp.patientrecord.uploadimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.jaden.medicoapp.R;

public class ImageActivity extends AppCompatActivity {

    TouchImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        byte[] bos = getIntent().getExtras().getByteArray("Image");
        Bitmap bm = BitmapFactory.decodeByteArray(bos, 0 ,bos.length);
        image = (TouchImageView) findViewById(R.id.imageZoom);
        image.setImageBitmap(bm);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
