package com.medicaldata.darren.medicaldata.Model;

import java.math.BigDecimal;

/**
 * Created by Darren on 2017/11/7.
 */

public class ChartDataBean {
    /**
     * Id : b0987c81-9f0c-4f1b-84d0-3318851d18ab
     * Weight : 60
     * BMI : 19.59
     * CreatedTime : 2017-11-07T17:14:19.157
     * UserId : 2c404ff2-19e1-4139-8512-213e3c74176f
     */

    private String Id;
    private float Weight;
    private float BMI;
    private int BodyFat;

    private float SpendHour;
    private int Waist;
    private float TwelveMinuteDistance;
    private int BenchPress;
    private int DriveLeg;
    private int Pushup;
    private int ShoulderFlexion;
    private int SitAnterior;
    private int YMCABenchPress;
    private int YMCASitAnterior;




    private String CreatedTime;
    private String UserId;

    public int getWaist() { return Waist; }

    public void setWaist(int Waist) {
        this.Waist = Waist;
    }

    public float getTwelveMinuteDistance() { return TwelveMinuteDistance; }

    public void setTwelveMinuteDistance(float TwelveMinuteDistance) {
        this.TwelveMinuteDistance = TwelveMinuteDistance;
    }

    public int getBenchPress() { return BenchPress; }

    public void setBenchPress(int BenchPress) {
        this.BenchPress = BenchPress;
    }

    public int getDriveLeg() { return DriveLeg; }

    public void setDriveLeg(int DriveLeg) {
        this.DriveLeg = DriveLeg;
    }

    public int getPushup() { return Pushup; }

    public void setPushup(int Pushup) {
        this.Pushup = Pushup;
    }

    public int getShoulderFlexion() { return ShoulderFlexion; }

    public void setShoulderFlexion(int ShoulderFlexion) {
        this.ShoulderFlexion = ShoulderFlexion;
    }

    public int getSitAnterior() { return SitAnterior; }

    public void setSitAnterior(int SitAnterior) {
        this.SitAnterior = SitAnterior;
    }

    public int getYMCABenchPress() { return YMCABenchPress; }

    public void setYMCABenchPress(int YMCABenchPress) {
        this.YMCABenchPress = YMCABenchPress;
    }

    public int getYMCASitAnterior() { return YMCASitAnterior; }

    public void setYMCASitAnterior(int YMCASitAnterior) {
        this.YMCASitAnterior = YMCASitAnterior;
    }





    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public float getSpendHour() {
        return SpendHour;
    }

    public void setSpendHour(float SpendHour) {
        this.SpendHour = SpendHour;
    }

    public float getWeight() {
        return Weight;
    }

    public void setWeight(float Weight) {
        this.Weight = Weight;
    }

    public int getBodyFat() { return BodyFat; }

    public void setBodyFat(int Weight) {
        this.BodyFat = BodyFat;
    }

    public float getBMI() {
        return BMI;
    }

    public void setBMI(float BMI) {
        this.BMI = BMI;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String CreatedTime) {
        this.CreatedTime = CreatedTime;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }
}
