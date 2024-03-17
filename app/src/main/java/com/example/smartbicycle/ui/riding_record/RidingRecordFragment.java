package com.example.smartbicycle.ui.riding_record;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbicycle.R;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class RidingRecordFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private RidingRecordAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<RidingRecordItem> mMyData;

    public RidingRecordFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_riding_record, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.riding_record_recycler);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RidingRecordAdapter(mMyData, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.scrollToPosition(0);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataSet();
    }

    private void initDataSet() {
        //for Test
        mMyData = new ArrayList<>();

        mMyData.add(new RidingRecordItem("6", "2020/11/17", "01:52:33", "14.41km", "2.5km/h", "5.3km/h"));
        mMyData.add(new RidingRecordItem("5", "2020/11/15", "00:44:53", "5.32km", "8.5km/h", "10.9km/h"));
        mMyData.add(new RidingRecordItem("4", "2020/11/13", "03:12:34", "21.12km", "3.0km/h", "8.1km/h"));
        mMyData.add(new RidingRecordItem("3", "2020/11/05", "00:42:41", "4.94km", "1.1km/h", "3.4km/h"));
        mMyData.add(new RidingRecordItem("2", "2020/10/29", "00:31:04", "2.01km", "4.4km/h", "10.3km/h"));
        mMyData.add(new RidingRecordItem("1", "2020/10/25", "02:32:10", "11.57km", "3.9km/h", "9.1km/h"));

    }


}
