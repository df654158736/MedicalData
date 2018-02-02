package com.medicaldata.darren.medicaldata.Common;

import android.app.Activity;

import com.medicaldata.darren.medicaldata.Model.LoginBean;
import com.medicaldata.darren.medicaldata.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darren on 2017/9/18.
 */
public class Res {

    public static LoginBean loginBeanModel;

    public static List<Activity> activityLists = new ArrayList<>();

    public static List<String> q_bingshi = new ArrayList<String>(){
        {add("一次心脏病发作");}
        {add("心脏病手术");}
        {add("心脏导管插入术");}
        {add("经皮冠状动脉成形术（PTCA）");}
        {add("起搏器/植入式心脏除颤/复律器");}
        {add("心瓣膜疾病");}
        {add("心力衰竭");}
        {add("心脏移植");}
    };

    public static int[] q_tabIcons = {
            R.drawable.tab_question_selector,
            R.drawable.tab_data_selector,
            R.drawable.tab_my_selector,
            R.drawable.tab_my_selector,
    };

    public static List<String> q_symptom = new ArrayList<String>(){
        {add("在用力时胸部不适");}
        {add("不明原因的呼吸困难");}
        {add("头晕眼花、晕倒或眩晕");}
        {add("脚踝肿胀");}
        {add("因为快而强的心跳导致感觉不适");}
        {add("服用治疗心脏病的药物");}
    };

    public static List<String> q_other = new ArrayList<String>(){
        {add("糖尿病");}
        {add("哮喘或其他肺部疾病");}
        {add("短距离行走时，小腿发热或抽筋的感觉");}
    };

    public static List<String> q_risk = new ArrayList<String>(){
        {add("男性>=45岁");}
        {add("女性>=55岁");}
        {add("吸烟或吸烟不足6个月");}
        {add("血压>=140/90 mmHg");}
        {add("不知道自己的血压");}
        {add("正在服用降压药");}
        {add("血浆胆固醇>=200 mg/dl");}
        {add("不知道自己的血浆胆固醇水平");}
        {add("有一个近亲有心脏病或做过心脏手术，其中父亲或兄弟<=55岁，母亲或姐妹<=65岁");}
        {add("很少进行体力活动（如每周运动<3天，每天<30 min）");}
        {add("体重指数>=30 kg/m²（BMI=体重公斤/身高米/身高米）");}
        {add("糖尿病前期");}
        {add("不知道是否处于糖尿病前期");}

    };

    public static List<String> tab_titles = new ArrayList<String>(){
        {add("健康评估");}
        {add("健康数据");}
        {add("新知");}
        {add("我的");}
    };

    public static List<String> question_list_title = new ArrayList<String>(){
        {add("风险筛查");}
        {add("睡眠管理");}
        {add("心血管风险筛查");}
        {add("体适能");}
    };

    public static List<String> my_list_title = new ArrayList<String>(){
        {add("用户名");}
        {add("我的设备");}
        {add("基础数据");}
        {add("计步器");}
        {add("注销");}
    };

    public static List<String> q_disease = new ArrayList<String>(){
        {add("年龄（男性>=45岁，女性>=55岁）");}
        {add("家族史（心肌梗塞，冠状血管重建，父亲或其他男性近亲属55岁前猝死；母亲或其他女性近亲属65岁前猝死）");}
        {add("吸烟（吸烟或戒烟不足6个月或吸二手烟）");}
        {add("静坐少动的生活方式（至少3个月没有参加每周至少3天，每天不少于30min的中等强度体力活动 - 40%~60% VO2R）");}
        {add("肥胖（体重指数>=30kg/㎡或者男性腰围>102cm，女性腰围>88cm）");}
        {add("高血压（收缩压>=140mmHg和/或舒张压>=90mmHg，至少进行两次测量确定，或正在服用降压药）");}
        {add("血脂异常（低密度脂蛋白（LDL）胆固醇>=130mg/dl(3.37mmol/L)，或高密度脂蛋白（HDL）胆固醇<40mg/dl（1.04mmol/L），或正在服用降脂药。血清总胆固醇>=200mg/dl（5.18mmol/L））");}
        {add("糖尿病前期（空腹血糖受损（IFG），即空腹血糖>=100mg/dl（5.55mmol/L）并且<=125mg/dl（6.94mmol/L）；或葡萄糖耐量受损（IGT），即口服葡萄糖耐量实验（OGTT）2h血糖>=140mg/dl（7.77mmol/L）并且<=199mg/dl（11.04mmol/L），至少进行两次测量确定）");}
    };


}
