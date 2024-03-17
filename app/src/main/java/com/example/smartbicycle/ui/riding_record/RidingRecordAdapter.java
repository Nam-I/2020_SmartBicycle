package com.example.smartbicycle.ui.riding_record;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbicycle.R;

import java.util.ArrayList;

public class RidingRecordAdapter extends RecyclerView.Adapter<RidingRecordAdapter.ViewHolder> {
    private ArrayList<RidingRecordItem> myList;

    public RidingRecordAdapter(ArrayList<RidingRecordItem> myList, Activity activity) {
        this.myList = myList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.riding_record_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
        holder.RidingCount.setText(myList.get(position).getRidingCount());
        holder.RidingDate.setText(myList.get(position).getRidingDate());
        holder.RidingTime.setText(myList.get(position).getRidingTime());
        holder.RidingDistance.setText(myList.get(position).getRidingDistance());
        holder.RidingAvgSpeed.setText(myList.get(position).getRidingAvgSpeed());
        holder.RidingHighestSpeed.setText(myList.get(position).getRidingHighestSpeed());

    }

    @Override

    public int getItemCount() {
        return myList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView RidingCount;
        public TextView RidingDate;
        public TextView RidingTime;
        public TextView RidingDistance;
        public TextView RidingAvgSpeed;
        public TextView RidingHighestSpeed;

        public ViewHolder(View view) {
            super(view);

            RidingCount = (TextView) view.findViewById(R.id.item_riding_count);
            RidingDate = (TextView) view.findViewById(R.id.item_riding_date);
            RidingTime = (TextView) view.findViewById(R.id.item_riding_time_num);
            RidingDistance = (TextView) view.findViewById(R.id.item_riding_distance_num);
            RidingAvgSpeed = (TextView) view.findViewById(R.id.item_riding_average_speed_num);
            RidingHighestSpeed = (TextView) view.findViewById(R.id.item_riding_highest_speed_num);

        }

    }
}
