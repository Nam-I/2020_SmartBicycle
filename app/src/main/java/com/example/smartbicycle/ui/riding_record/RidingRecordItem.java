package com.example.smartbicycle.ui.riding_record;

public class RidingRecordItem {
    final private String RidingCount;
    final private String RidingDate;
    final private String RidingTime;
    final private String RidingDistance;
    final private String RidingAvgSpeed;
    final private String RidingHighestSpeed;

    public RidingRecordItem(String count, String date, String time, String distance, String avgSpeed, String highestSpeed) {
        // TODO : String 형식 맞추기
        this.RidingCount = count + "번째 주행";
        this.RidingDate = date;
        this.RidingTime = time;
        this.RidingDistance = distance;
        this.RidingAvgSpeed = avgSpeed;
        this.RidingHighestSpeed = highestSpeed;
    }

    /*
    public void setRidingCount(String count) {
        RidingCount = count;
    }

    public void setRidingDate(String date) {
        RidingDate = date;
    }

    public void setRidingTime(String time) {
        RidingTime = time;
    }

    public void setRidingDistance(String distance) {
        RidingDistance = distance;
    }

    public void setRidingAvgSpeed(String avgSpeed) {
        RidingAvgSpeed = avgSpeed;
    }

    public void setRidingHighestSpeed(String highestSpeed) {
        RidingHighestSpeed = highestSpeed;
    }
    */

    public String getRidingCount() {
        return this.RidingCount;
    }

    public String getRidingDate() {
        return this.RidingDate;
    }

    public String getRidingTime() {
        return this.RidingTime;
    }

    public String getRidingDistance() {
        return this.RidingDistance;
    }

    public String getRidingAvgSpeed() {
        return this.RidingAvgSpeed;
    }

    public String getRidingHighestSpeed() {
        return this.RidingHighestSpeed;
    }

    public String[] getAllItem() {
        String[] AllItem = {this.RidingCount, this.RidingDate, this.RidingTime, this.RidingDistance,
                this.RidingAvgSpeed, this.RidingHighestSpeed};
        return AllItem;
    }
}
