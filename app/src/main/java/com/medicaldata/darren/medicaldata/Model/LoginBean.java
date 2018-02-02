package com.medicaldata.darren.medicaldata.Model;

import android.text.TextUtils;

import com.medicaldata.darren.medicaldata.Common.Res;
import com.medicaldata.darren.medicaldata.Common.mUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Darren on 2017/11/6.
 */
public class LoginBean {

    /**
     * Id : 00000000-0000-0000-0000-000000000000
     * Username : string
     * Password : string
     * Gender : string
     * Height : string
     * Birthdate : string
     * CardiovascularReport : string
     * Risk1Report : string
     * Risk2Report : string
     * BenchPress : string
     * BenchPressReport : string
     * BodyFat : string
     * BodyFatReport : string
     * DriveLeg : string
     * DriveLegReport : string
     * Pushup : string
     * PushupReport : string
     * ShoulderFlexion : string
     * ShoulderFlexionReport : string
     * SitAnterior : string
     * SitAnteriorReport : string
     * TwelveMinuteDistance : string
     * TwelveMinuteDistanceReport : string
     * Waist : string
     * WaistReport : string
     * Weight : string
     * WeightReport : string
     * YMCABenchPress : string
     * YMCABenchPressReport : string
     * YMCASitAnterior : string
     * YMCASitAnteriorReport : string
     * CreatedTime : 2017-11-06T08:01:26.446Z
     */

    private String Id;
    private String Username;
    private String Password;
    private String Gender;
    private String Height;
    private String Birthdate;
    private String CardiovascularReport;
    private String Risk1Report;
    private String Risk2Report;
    private String BenchPress;
    private String BenchPressReport;
    private String Sleep;
    private String SleepReport;
    private String BodyFat;
    private String BodyFatReport;
    private String DriveLeg;
    private String DriveLegReport;
    private String Pushup;
    private String PushupReport;
    private String ShoulderFlexion;
    private String ShoulderFlexionReport;
    private String SitAnterior;
    private String SitAnteriorReport;
    private String TwelveMinuteDistance;
    private String TwelveMinuteDistanceReport;
    private String Waist;
    private String WaistReport;
    private String Weight;
    private String WeightReport;
    private String YMCABenchPress;
    private String YMCABenchPressReport;
    private String YMCASitAnterior;
    private String YMCASitAnteriorReport;
    private String CreatedTime;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }


    public int getAge() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date  = sdf.parse(this.Birthdate);
        }catch (ParseException e){
        }
        return mUtil.getAge(date);
    }

    public String getGenderText(){
        if(this.Gender.equals("M")){
            return  "男";
        }else{
            return  "女";
        }
    }


    public float getBmi(){
        float height = Integer.parseInt(this.Height)*0.01f;
        if(TextUtils.isEmpty(this.Weight)){
            return 0;
        }else{
            float weight = Float.parseFloat(this.Weight)*100;
            return Math.round((weight / (height * height))) * 0.01f;
        }
    }




    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String Height) {
        this.Height = Height;
    }

    public String getBirthdate() {
        return Birthdate;
    }

    public void setBirthdate(String Birthdate) {
        this.Birthdate = Birthdate;
    }

    public String getCardiovascularReport() {
        return CardiovascularReport;
    }

    public void setCardiovascularReport(String CardiovascularReport) {
        this.CardiovascularReport = CardiovascularReport;
    }

    public String getRisk1Report() {
        return Risk1Report;
    }

    public void setRisk1Report(String Risk1Report) {
        this.Risk1Report = Risk1Report;
    }

    public String getRisk2Report() {
        return Risk2Report;
    }

    public void setRisk2Report(String Risk2Report) {
        this.Risk2Report = Risk2Report;
    }

    public String getSleep() {
        return Sleep;
    }

    public void setSleep(String Sleep) {
        this.Sleep = Sleep;
    }

    public String getSleepReport() {
        return SleepReport;
    }

    public void setSleepReport(String SleepReport) {
        this.SleepReport = SleepReport;
    }

    public String getBenchPress() {
        return BenchPress;
    }

    public void setBenchPress(String BenchPress) {
        this.BenchPress = BenchPress;
    }

    public String getBenchPressReport() {
        return BenchPressReport;
    }

    public void setBenchPressReport(String BenchPressReport) {
        this.BenchPressReport = BenchPressReport;
    }

    public String getBodyFat() {
        return BodyFat;
    }

    public void setBodyFat(String BodyFat) {
        this.BodyFat = BodyFat;
    }

    public String getBodyFatReport() {
        return BodyFatReport;
    }

    public void setBodyFatReport(String BodyFatReport) {
        this.BodyFatReport = BodyFatReport;
    }

    public String getDriveLeg() {
        return DriveLeg;
    }

    public void setDriveLeg(String DriveLeg) {
        this.DriveLeg = DriveLeg;
    }

    public String getDriveLegReport() {
        return DriveLegReport;
    }

    public void setDriveLegReport(String DriveLegReport) {
        this.DriveLegReport = DriveLegReport;
    }

    public String getPushup() {
        return Pushup;
    }

    public void setPushup(String Pushup) {
        this.Pushup = Pushup;
    }

    public String getPushupReport() {
        return PushupReport;
    }

    public void setPushupReport(String PushupReport) {
        this.PushupReport = PushupReport;
    }

    public String getShoulderFlexion() {
        return ShoulderFlexion;
    }

    public void setShoulderFlexion(String ShoulderFlexion) {
        this.ShoulderFlexion = ShoulderFlexion;
    }

    public String getShoulderFlexionReport() {
        return ShoulderFlexionReport;
    }

    public void setShoulderFlexionReport(String ShoulderFlexionReport) {
        this.ShoulderFlexionReport = ShoulderFlexionReport;
    }

    public String getSitAnterior() {
        return SitAnterior;
    }

    public void setSitAnterior(String SitAnterior) {
        this.SitAnterior = SitAnterior;
    }

    public String getSitAnteriorReport() {
        return SitAnteriorReport;
    }

    public void setSitAnteriorReport(String SitAnteriorReport) {
        this.SitAnteriorReport = SitAnteriorReport;
    }

    public String getTwelveMinuteDistance() {
        return TwelveMinuteDistance;
    }

    public void setTwelveMinuteDistance(String TwelveMinuteDistance) {
        this.TwelveMinuteDistance = TwelveMinuteDistance;
    }

    public String getTwelveMinuteDistanceReport() {
        return TwelveMinuteDistanceReport;
    }

    public void setTwelveMinuteDistanceReport(String TwelveMinuteDistanceReport) {
        this.TwelveMinuteDistanceReport = TwelveMinuteDistanceReport;
    }

    public String getWaist() {
        return Waist;
    }

    public void setWaist(String Waist) {
        this.Waist = Waist;
    }

    public String getWaistReport() {
        return WaistReport;
    }

    public void setWaistReport(String WaistReport) {
        this.WaistReport = WaistReport;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String Weight) {
        this.Weight = Weight;
    }

    public String getWeightReport() {
        return WeightReport;
    }

    public void setWeightReport(String WeightReport) {
        this.WeightReport = WeightReport;
    }

    public String getYMCABenchPress() {
        return YMCABenchPress;
    }

    public void setYMCABenchPress(String YMCABenchPress) {
        this.YMCABenchPress = YMCABenchPress;
    }

    public String getYMCABenchPressReport() {
        return YMCABenchPressReport;
    }

    public void setYMCABenchPressReport(String YMCABenchPressReport) {
        this.YMCABenchPressReport = YMCABenchPressReport;
    }

    public String getYMCASitAnterior() {
        return YMCASitAnterior;
    }

    public void setYMCASitAnterior(String YMCASitAnterior) {
        this.YMCASitAnterior = YMCASitAnterior;
    }

    public String getYMCASitAnteriorReport() {
        return YMCASitAnteriorReport;
    }

    public void setYMCASitAnteriorReport(String YMCASitAnteriorReport) {
        this.YMCASitAnteriorReport = YMCASitAnteriorReport;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String CreatedTime) {
        this.CreatedTime = CreatedTime;
    }
}
