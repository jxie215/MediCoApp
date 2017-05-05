package com.example.jaden.medicoapp.patientrecord.uploadimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jaden.medicoapp.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by buste on 5/5/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PhotoHolder> {

    private ArrayList<Photo> mPhotos;
    public RecyclerAdapter(ArrayList<Photo> photos) {
        mPhotos = photos;
    }


    public static class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mItemImage;
        private TextView mItemName;
        private TextView mItemDate;
        private TextView mItemDoctor;
        private Photo mPhoto;


        public PhotoHolder(View v) {
            super(v);

            mItemImage = (ImageView) v.findViewById(R.id.imageViewForImage);
            mItemName = (TextView) v.findViewById(R.id.card_img);
            mItemDate = (TextView) v.findViewById(R.id.card_date);
            mItemDoctor = (TextView) v.findViewById(R.id.card_doctor);
            v.setOnClickListener(this);
        }

        public void bindPhoto(Photo photo) {
            mPhoto = photo;
            mItemImage.setImageBitmap(mPhoto.getImage());
            mItemName.setText(photo.getName());
            mItemDate.setText(photo.getDate());
            mItemDoctor.setText(photo.getDoctor());
        }

        @Override
        public void onClick(View v) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            mPhoto.getImage().compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();
            Intent showPhotoIntent = new Intent(itemView.getContext(),ImageActivity.class);
            showPhotoIntent.putExtra("Image",bArray);
            itemView.getContext().startActivity(showPhotoIntent);
        }
    }
    @Override
    public RecyclerAdapter.PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_list, parent, false);
        return new PhotoHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.PhotoHolder holder, int position) {
        Photo itemPhoto = mPhotos.get(position);
        holder.bindPhoto(itemPhoto);
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }
}