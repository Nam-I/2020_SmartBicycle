package com.example.smartbicycle.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.smartbicycle.RecordFormatting;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static com.example.smartbicycle.RecordFormatting.RIDING_MODE;

public class MyLocationListener implements LocationListener {
    private Context myContext;
    private Activity myActivity;
    private LocationManager locationManager;

    private long startTime = 0L;
    private long passedTime = 0L;
    private long runningTime = 0L;
    private double distance = 0.0;
    private double avgSpeed = 0.0;
    private double highestSpeed = 0.0;
    private double currentSpeed = 0.0;
    private int calorie = 0;

    private boolean isPause = true;
    private boolean isFirst = true;

    private String[] valueStr;
    private TextView[] textViewArr;
    private RecordFormatting recordFormatting;

    private Location pastLocation;


    @SuppressLint("MissingPermission")
    public MyLocationListener(LocationManager locationManager, Context context, Activity myActivity, TextView[] textViewArr) {
        this.myContext = context;
        this.myActivity = myActivity;
        this.locationManager = locationManager;
        this.textViewArr = textViewArr;
        pastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        startTime = pastLocation.getTime();

    }


    @SuppressLint("MissingPermission")
    @Override
    public void onLocationChanged(Location location) {

        if (isFirst) {
            pastLocation = location;
            this.startTime = pastLocation.getTime();
            runningTime = passedTime + ((location.getTime() - startTime) / 1000);
            this.isFirst = false;
            this.isPause = false;

            setText();
        } else {
            if (isPause) {
                pastLocation = location;
                this.startTime = pastLocation.getTime();
                runningTime = passedTime + ((location.getTime() - startTime) / 1000);
                this.isPause = false;
                setText();

            } else {
                if (pastLocation != null) {
                    if (location.hasSpeed()) {
                        currentSpeed = ((double) location.getSpeed()) * 3.6;             // km/h단위
                        if (currentSpeed > highestSpeed) highestSpeed = currentSpeed;
                    }
                    distance += location.distanceTo(pastLocation);  // m 단위로 반환
                    runningTime = passedTime + ((location.getTime() - startTime) / 1000);   // 1970.1.1 기준 밀리세컨드
                    avgSpeed = distance / (double) runningTime;
                    // TODO: setText


                    pastLocation = location;
                    setText();
                }
            }
        }
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onProviderEnabled(String provider) {


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
    }

    @Override
    public void onProviderDisabled(String provider) {


    }

    void setText() {

        valueStr = new String[]{String.valueOf(runningTime), String.valueOf(distance),
                String.valueOf(avgSpeed), String.valueOf(highestSpeed), String.valueOf(currentSpeed), String.valueOf(calorie)};
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(valueStr));
        recordFormatting = new RecordFormatting(RecordFormatting.RIDING_MODE, arrayList);
        int size = 0;
        for (String temp : recordFormatting.getRecord()) {
            textViewArr[size].setText(temp);
            size++;
        }
    }

    void setPause() {
        this.isPause = true;
        this.passedTime = this.runningTime;
    }

    void reset() {
        this.isFirst = true;
        this.passedTime = 0;

        startTime = 0L;
        runningTime = 0L;
        distance = 0.0;
        avgSpeed = 0.0;
        highestSpeed = 0.0;
        currentSpeed = 0.0;
        calorie = 0;

    }


}
