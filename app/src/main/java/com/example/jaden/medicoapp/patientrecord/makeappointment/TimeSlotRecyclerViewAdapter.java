package com.example.jaden.medicoapp.patientrecord.makeappointment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        public RViewHolder(final View itemView) {
            super(itemView);
            this.timeSlotLabel = (TextView) itemView.findViewById(R.id.time_slot_label);
            this.timeSlotLabel.setOnClickListener(new View.OnClickListener() {
                boolean isSelected = false;
                @Override
                public void onClick(View v) {
                    if(!isSelected) {
                        isSelected = true;
                        FragmentBookAppointment.setDateSlotSelection((String)itemView.getTag());
                        v.setBackgroundColor(v.getResources().getColor(R.color.primary));
                    }
                    else {
                        isSelected = false;
                        FragmentBookAppointment.dateSlotSelected = null;
                        v.setBackgroundColor(v.getResources().getColor(R.color.simply_white));
                    }
                }
            });

        }
    }

    public TimeSlotRecyclerViewAdapter(Context context, ArrayList<String> slots){
        this.slots = slots;
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
        holder.timeSlotLabel.setText(slots.get(position).substring(slot.indexOf("/")+1));
        holder.itemView.setTag(slots.get(position));
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
