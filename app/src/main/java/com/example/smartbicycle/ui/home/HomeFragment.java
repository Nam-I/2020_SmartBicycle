package com.example.smartbicycle.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.smartbicycle.R;
import com.example.smartbicycle.RecordFormatting;

import java.util.Date;
import java.util.Objects;


public class HomeFragment extends Fragment {

    private LocationManager locationManager;
    private TextView time, distance, avgSpeed, highestSpeed, currentSpeed, calorie;
    private TextView[] textViewArr;
    private ImageButton startButton, stopButton, pauseButton;
    private View v;
    private MyLocationListener myListener;
    private Context myContext;
    private Activity myActivity;
    private BtnOnClickListener myOnClickListener;
    private RecordFormatting zeroFormatting;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        myContext=getContext();
        myActivity=getActivity();
        xmlLinking(v);
        if (ContextCompat.checkSelfPermission(myActivity,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(myActivity,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION},
                    55);
        }

        this.locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        this.zeroFormatting = new RecordFormatting(RecordFormatting.ZERO_RESET_MODE, null);
        myListener = new MyLocationListener(locationManager, myContext, myActivity, textViewArr);
        myOnClickListener = new BtnOnClickListener(locationManager, myListener, v, myContext);


        btnLinking();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {


        if (ContextCompat.checkSelfPermission(myActivity,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(myActivity,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION},
                    55);
        }

        super.onResume();
        locationManager.removeUpdates(myListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(myActivity,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(myActivity,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION},
                    55);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(myActivity,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(myActivity,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION},
                    55);
        }
    }

    void reset() {
        String[] strArr = zeroFormatting.getRecord();

        int size = 0;
        for (String temp : strArr) {
            textViewArr[size].setText(temp);
            size++;
        }

    }

    void xmlLinking(View v) {
        this.time = (TextView) v.findViewById(R.id.time_num);
        this.distance = (TextView) v.findViewById(R.id.distance_num);
        this.avgSpeed = (TextView) v.findViewById(R.id.avgSpeed_num);
        this.highestSpeed = (TextView) v.findViewById(R.id.highestSpeed_num);
        this.currentSpeed = (TextView) v.findViewById(R.id.current_speed);
        this.calorie = (TextView) v.findViewById(R.id.calorie_num);

        this.textViewArr = new TextView[]{time, distance, avgSpeed, highestSpeed, currentSpeed, calorie};

        this.startButton = (ImageButton) v.findViewById(R.id.start_button);
        this.stopButton = (ImageButton) v.findViewById(R.id.stop_button);
        this.pauseButton = (ImageButton) v.findViewById(R.id.pause_button);
    }

    void btnLinking() {
        this.startButton.setOnClickListener(myOnClickListener);
        this.stopButton.setOnClickListener(myOnClickListener);
        this.pauseButton.setOnClickListener(myOnClickListener);

    }

    class BtnOnClickListener implements View.OnClickListener {

        private LocationManager locationManager;
        private View v;
        private MyLocationListener myListener;
        private Context myContext;


        @SuppressLint("MissingPermission")
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.start_button:

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 0, myListener);

                    break;

                case R.id.pause_button:

                    locationManager.removeUpdates(myListener);
                    this.myListener.setPause();

                    break;

                case R.id.stop_button:

                    locationManager.removeUpdates(myListener);
                    // TODO : 데이터 저장
                    myListener.reset();
                    reset();
                    break;

            }

        }

        public BtnOnClickListener(LocationManager locationManager, MyLocationListener myListener, View v, Context myContext) {
            this.locationManager = locationManager;
            this.myListener = myListener;
            this.v = v;
            this.myContext = myContext;
        }

    }

}
