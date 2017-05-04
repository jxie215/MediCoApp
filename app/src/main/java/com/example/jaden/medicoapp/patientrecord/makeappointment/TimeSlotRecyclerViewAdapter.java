package com.example.jaden.medicoapp.patientrecord.makeappointment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jaden.medicoapp.R;

import java.util.ArrayList;

public class TimeSlotRecyclerViewAdapter extends RecyclerView.Adapter<TimeSlotRecyclerViewAdapter.RViewHolder> implements View.OnClickListener {

    Context context;
    LayoutInflater layoutInflater;

    public String TAG = TimeSlotRecyclerViewAdapter.class.getSimpleName();
    ArrayList<String> slots;

    //define interface
    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_view_time_slot, parent, false);
        RViewHolder rViewHolder = new RViewHolder(view);
        view.setOnClickListener(this);
        return rViewHolder;
    }

    public static class RViewHolder extends RecyclerView.ViewHolder{
        TextView timeSlotLabel;

        ImageView image;
        public RViewHolder(View itemView) {
            super(itemView);
            this.timeSlotLabel = (TextView) itemView.findViewById(R.id.time_slot_label);

        }
    }

    public TimeSlotRecyclerViewAdapter(Context context, ArrayList<String> removeTheseSlots){
        this.slots.add("9:00AM");
        this.slots.add("9:30AM");
        this.slots.add("10:00AM");
        this.slots.add("10:30AM");
        this.slots.add("11:00AM");
        this.slots.add("11:30AM");
        this.slots.add("1:00AM");
        this.slots.add("1:30AM");
        this.slots.add("2:00AM");
        this.slots.add("2:30AM");
        this.slots.add("3:00AM");
        this.slots.add("4:30AM");

        for(String slot: removeTheseSlots)
            this.slots.remove(slot);
        layoutInflater = LayoutInflater.from(context);
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);

    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view, String.valueOf(view.getTag()));
        }
        else{
            Log.e("CLICK", "ERROR");
        }
    }

    @Override
    public void onBindViewHolder(final RViewHolder holder, int position) {
        String slot = slots.get(position);
        holder.timeSlotLabel.setText(slots.get(position).substring(slot.indexOf("-")));
    }

    @Override
    public int getItemCount() {
        return slots.size();
    }

    public void notifyData(ArrayList<String> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.slots = myList;
        notifyDataSetChanged();
    }

}
