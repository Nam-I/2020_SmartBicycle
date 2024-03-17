package com.example.smartbicycle;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.zip.ZipError;
// LocationListener 에서 받은 값을 formatting 해서 setText 함
// RidingRecordFragment 에서 formatting 이용

public class RecordFormatting {
    public static final int RIDING_MODE = 0;
    public static final int RECORDED_MODE = 1;
    public static final int ZERO_RESET_MODE = 2;
    public static final int LISTENER_RESET_MODE=3;

    public static final double TEST_WEIGHT = 60.0;

    private static final String[] zeroReset = {"0", "0", "0.0", "0.0", "0.0", "0"};

    private int mode;
    private ArrayList<String> strList;
    private String[] strArr;

    private String runningTime = "0";         // 1,2
    private String distance = "0";            // 1,2
    private String avgSpeed = "0.0";            // 1,2
    private String highestSpeed = "0.0";        // 1,2
    private String currentSpeed = "0.0";        // 1
    private String calorie = "0";             // 1

    private double avgSpeedNum = 0.0;
    private double riddingTime;

    public RecordFormatting(int mode, ArrayList<String> rawStr) {
        this.mode = mode;

        if(this.mode==LISTENER_RESET_MODE){
            runningTime = "0";
            distance = "0";
            avgSpeed = "0.0";
            highestSpeed = "0.0";
            currentSpeed = "0.0";
            calorie = "0";
        }else {
            if (this.mode != ZERO_RESET_MODE) {
                this.strList = rawStr;
            } else strList = null;

            run();
        }
    }

    private void run() {
        if (this.mode != ZERO_RESET_MODE) {
            unpack();
        } else {
            runningTime = "0";
            distance = "0";
            avgSpeed = "0.0";
            highestSpeed = "0.0";
            currentSpeed = "0.0";
            calorie = "0";
        }
        if (this.mode == RIDING_MODE) {
            this.avgSpeedNum = Double.parseDouble(avgSpeed);
        } else if (this.mode == ZERO_RESET_MODE) {
            this.avgSpeedNum = 0.0;
        }
        formatting();
        pack();
        listToString();
    }

    private void unpack() {

        this.runningTime = this.strList.get(0);
        this.distance = this.strList.get(1);
        this.avgSpeed = this.strList.get(2);
        this.highestSpeed = this.strList.get(3);
        if (mode == RIDING_MODE) {
            this.currentSpeed = this.strList.get(4);
            this.calorie = this.strList.get(5);
        }


    }

    private void formatting() {
        timeFormatting();
        distanceFormatting();
        this.avgSpeed = speedFormatting(this.avgSpeed);
        this.highestSpeed = speedFormatting(this.highestSpeed);
        if (this.mode != RECORDED_MODE) {
            this.currentSpeed = speedFormatting(this.currentSpeed);
            calCalorie();
        }

    }

    private void pack() {
        ArrayList<String> changedStr = new ArrayList<String>();
        changedStr.add(runningTime);
        changedStr.add(distance);
        changedStr.add(avgSpeed);
        changedStr.add(highestSpeed);
        if (mode != RECORDED_MODE) {
            changedStr.add(currentSpeed);
            changedStr.add(calorie);
        }
        this.strList = changedStr;
    }

    private void listToString() {
        String[] array = new String[this.strList.size()];
        int size = 0;
        for (String temp : this.strList) {
            array[size++] = temp;
        }
        strArr = array;

    }

    public String[] getRecord() {
        return this.strArr;
    }

    private void calCalorie() {
        //double Coefficient= getCoefficient();
        double Coefficient = getApproximation();
        int cal = (int) (Coefficient * ((double) TEST_WEIGHT) * riddingTime);
        if (this.mode == ZERO_RESET_MODE) {
            cal = 0;

        }
        calorie = String.valueOf(cal) + "cal";
    }

    @NotNull
    private String speedFormatting(String speed) {
        return String.format(Locale.US, "%.2f", Double.parseDouble(speed)) + "km/h";
    }

    private void timeFormatting() {
        int time = Integer.parseInt(this.runningTime);
        int numHour = time / 3600;


        int numMinute = (time % 3600) / 60;
        this.riddingTime = (double) time / 60.0;
        int numSecond = (time % 3600) % 60;
        String h = String.format(Locale.US, "%02d", numHour);
        String m = String.format(Locale.US, "%02d", numMinute);

        String s = String.format(Locale.US, "%02d", numSecond);

        this.runningTime = h + ":" + m + ":" + s;
    }

    private void distanceFormatting() {
        double distance = Double.parseDouble(this.distance);
        if (distance < 0.1) this.distance = String.format(Locale.US, "%.2f", distance) + "m";
        else this.distance = String.format(Locale.US, "%.2f", distance / 1000.0) + "km";

    }

//    private double[] getCoefficientRange(){
//        if(0<=avgSpeedNum && avgSpeedNum<=13){
//            return new double[]{0, 0.065,0,13};
//        }
//
//        else if(avgSpeedNum<=16){
//            return new double[]{0.065, 0.0783,13,16};
//        }
//
//        else if(avgSpeedNum<=19){
//                return new double[]{0.0783, 0.0939,16,19};
//        }
//
//        else if(avgSpeedNum<=22){
//            return new double[]{0.939,0.113,19,22};
//        }
//
//        else if(avgSpeedNum<=24){
//            return new double[]{0.113,0.124,22,24};
//        }
//
//        else if(avgSpeedNum<=26){
//            return new double[]{0.124,0.136,24,26};
//        }
//
//        else if(avgSpeedNum<=27){
//            return new double[]{0.136,0.149,26,27};
//        }
//
//        else if(avgSpeedNum<=29){
//            return new double[]{0.149,0.163,27,29};
//        }
//        else if(avgSpeedNum<=31){
//            return new double[]{0.163,0.179,29,31};
//        }
//
//        else if(avgSpeedNum<=32){
//            return new double[]{0.179,0.196,31,32};
//        }
//        else if(avgSpeedNum<=34){
//            return new double[]{0.196,0.215,32,34};
//        }
//
//        else if(avgSpeedNum<=37){
//            return new double[]{0.215,0.259,34,37};
//        }
//
//        else if(avgSpeedNum<=40){
//            return new double[]{0.259,0.311,37,40};
//        }
//
//        else{
//            double efficient = this.avgSpeedNum*0.007775;
//            return new double[]{0, efficient,0,this.avgSpeedNum};
//        }
//
//    }

//    private double getCoefficient(){
//        double[] arr=getCoefficientRange();
//        double m=this.avgSpeedNum-arr[2];
//        double n=arr[3]-this.avgSpeedNum;
//        return (m*arr[1]+n*arr[0])/(m+n);
//    }

    private double getApproximation() {
        return this.avgSpeedNum * 0.007775;
    }


}
