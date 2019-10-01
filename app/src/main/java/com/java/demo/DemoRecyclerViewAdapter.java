package com.java.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.java.models.ResponseModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DemoRecyclerViewAdapter extends RecyclerView.Adapter<DemoRecyclerViewAdapter.ViewHolder> {
    private final ArrayList<ResponseModel> mValues;
    private final Context mContext;


    public DemoRecyclerViewAdapter(Context context, ArrayList<ResponseModel> items) {
        this.mContext = context;
        this.mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_frag_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DemoRecyclerViewAdapter.ViewHolder holder, int position) {
        // Set Values
        holder.mDuration.setText(mValues.get(position).getDuration() +"");
        holder.mTime.setText(getDateTime(mValues.get(position).getRisetime()));
    }

    @Override
    public int getItemCount() {
       return mValues.size();
    }

    /**
     * Method for taking the Unix based value from the API and converting it to a readable
     * Date Format.
     * @param value of the Unix based datetime.
     * **/
    private String getDateTime(long value) {
        Date date = new Date(value * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d yyyy HH:mm a");
        return sdf.format(date);
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView mDuration;
        public TextView mTime;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mDuration = itemView.findViewById(R.id.tv_duration_value);
            mTime = itemView.findViewById(R.id.tv_risetime_value);
        }
    }
}
