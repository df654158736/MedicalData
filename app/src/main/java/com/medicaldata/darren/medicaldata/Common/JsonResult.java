package com.medicaldata.darren.medicaldata.Common;

import android.util.Log;

import java.text.MessageFormat;

/**
 * Created by Darren on 2017/8/17.
 */


public class JsonResult {

    public String getResult() {

        String str = "{\"cid\":\"101\",\"mobile\":\"15253144620\",\"flag\":\"success\",\"cause\":\"\",\"ename\":\"张三\",\"employno\":\"002\",\"dept\":\"市场部\",\"rowcount\":\"5\",\"currentpage\":\"1\" ,\"vote\":[{\"id\":\"132\",\"Title\":\"标题: 对公司的意见\",\"stype\":\"类型1\",\"SelCount\":\"0\",\"Sel1\":\"\",\"Sel2\":\"\",\"Sel3\":\"\",\"Sel4\":\"\",\"Sel5\":\"\",\"Sel6\":\"\",\"Sel7\":\"\",\"Sel8\":\"\",\"Sel9\":\"\",\"Sel10\":\"\",\"ctype\":\"3\"},{\"id\":\"131\",\"Title\":\"今天看见雪了吗？\",\"stype\":\"类型2\",\"SelCount\":\"2\",\"Sel1\":\"是\",\"Sel2\":\"否\",\"Sel3\":\"\",\"Sel4\":\"\",\"Sel5\":\"\",\"Sel6\":\"\",\"Sel7\":\"\",\"Sel8\":\"\",\"Sel9\":\"\",\"Sel10\":\"\",\"ctype\":\"4\"},{\"id\":\"130\",\"Title\":\"你今天中午吃什么？\",\"stype\":\"类型1\",\"SelCount\":\"4\",\"Sel1\":\"米饭\",\"Sel2\":\"鸡蛋\",\"Sel3\":\"鸡肉\",\"Sel4\":\"猪肉\",\"Sel5\":\"\",\"Sel6\":\"\",\"Sel7\":\"\",\"Sel8\":\"\",\"Sel9\":\"\",\"Sel10\":\"\",\"ctype\":\"5\"},{\"id\":\"129\",\"Title\":\"哈哈\",\"stype\":\"类型2\",\"SelCount\":\"4\",\"Sel1\":\"第一\",\"Sel2\":\"第二\",\"Sel3\":\"第三\",\"Sel4\":\"第四\",\"Sel5\":\"\",\"Sel6\":\"\",\"Sel7\":\"\",\"Sel8\":\"\",\"Sel9\":\"\",\"Sel10\":\"\",\"ctype\":\"2\"},{\"id\":\"128\",\"Title\":\"标题\",\"stype\":\"类型1\",\"SelCount\":\"3\",\"Sel1\":\"第一\",\"Sel2\":\"第二\",\"Sel3\":\"第三\",\"Sel4\":\"\",\"Sel5\":\"\",\"Sel6\":\"\",\"Sel7\":\"\",\"Sel8\":\"\",\"Sel9\":\"\",\"Sel10\":\"\",\"ctype\":\"1\"}]}";

        return str;
    }




    public String getRiskSubjectResult() {
        String str =
                Begin() +
                        MultipleAnswerString("111","病史","是否有下列（请勾选您有的选项）", Res.q_bingshi.get(0), Res.q_bingshi.get(1),Res.q_bingshi.get(2),Res.q_bingshi.get(3),Res.q_bingshi.get(4),Res.q_bingshi.get(5),Res.q_bingshi.get(6),Res.q_bingshi.get(7)) + "," +
                        MultipleAnswerString("222","症状","是否有下列（请勾选您有的选项）", Res.q_symptom.get(0), Res.q_symptom.get(1),Res.q_symptom.get(2),Res.q_symptom.get(3),Res.q_symptom.get(4),Res.q_symptom.get(5)) + "," +
                        MultipleAnswerString("333","其他健康问题", "是否有下列（请勾选您有的选项）",Res.q_other.get(0), Res.q_other.get(1),Res.q_other.get(2)) + "," +
                        MultipleAnswerString("444","心血管危险因素", "是否有下列（请勾选您有的选项）",Res.q_risk.get(0), Res.q_risk.get(1),Res.q_risk.get(2),Res.q_risk.get(3),Res.q_risk.get(4),Res.q_risk.get(5),Res.q_risk.get(6),Res.q_risk.get(7),Res.q_risk.get(8),Res.q_risk.get(9),Res.q_risk.get(10),Res.q_risk.get(11),Res.q_risk.get(12)) +
                        End();
        return str;
    }


    public String getSleepResult() {

        String str =
                Begin() +
                        EditTextString("入睡时间", "7") + "," +
                        EditTextString("清醒时间", "7") + "," +
                        EditTextString("深睡时长（小时）", "3") + "," +
                        EditTextString("浅睡时长（小时）", "3") + "," +
                        EditTextString("清醒时长（小时）", "3") +
                        End();
        return str;
    }

    public String getPhysicalAgilitySubjectResult() {

        //String str = "{\"flag\":\"success\",\"vote\": [{\"id\":\"132\",\"Title\":\"题目1：输入体重（kg)\",\"stype\":\"类型1\",\"SelCount\":\"0\",\"Sel1\":\"\",\"Sel2\":\"\",\"Sel3\":\"\",\"Sel4\":\"\",\"Sel5\":\"\",\"Sel6\":\"\",\"Sel7\":\"\",\"Sel8\":\"\",\"Sel9\":\"\",\"Sel10\":\"\",\"ctype\":\"3\"},{\"id\":\"132\",\"Title\":\"题目2：输入腰围（cm)\",\"stype\":\"类型1\",\"SelCount\":\"0\",\"Sel1\":\"\",\"Sel2\":\"\",\"Sel3\":\"\",\"Sel4\":\"\",\"Sel5\":\"\",\"Sel6\":\"\",\"Sel7\":\"\",\"Sel8\":\"\",\"Sel9\":\"\",\"Sel10\":\"\",\"ctype\":\"3\"},{\"id\":\"132\",\"Title\":\"题目3：输入体脂百分比（%)\",\"stype\":\"类型1\",\"SelCount\":\"0\",\"Sel1\":\"\",\"Sel2\":\"\",\"Sel3\":\"\",\"Sel4\":\"\",\"Sel5\":\"\",\"Sel6\":\"\",\"Sel7\":\"\",\"Sel8\":\"\",\"Sel9\":\"\",\"Sel10\":\"\",\"ctype\":\"3\"},{\"id\":\"132\",\"Title\":\"题目4：输入12min.跑距离(km)\",\"stype\":\"类型1\",\"SelCount\":\"0\",\"Sel1\":\"\",\"Sel2\":\"\",\"Sel3\":\"\",\"Sel4\":\"\",\"Sel5\":\"\",\"Sel6\":\"\",\"Sel7\":\"\",\"Sel8\":\"\",\"Sel9\":\"\",\"Sel10\":\"\",\"ctype\":\"3\"}]}";
        String str =
                Begin() +
                        EditTextString("输入体重（kg)", "3") + "," +
                        EditTextString("输入腰围（cm)", "3") + "," +
                        EditTextString("输入体脂百分比（%)", "3") + "," +
                        EditTextString("输入12min.跑距离(km)", "3") + "," +
                        EditTextString("最大卧推重量(kg)", "3") + "," +
                        EditTextString("蹬腿最大重量(kg)（需在器械健身房）", "3") + "," +
                        EditTextString("俯卧撑最大次数", "3") + "," +
                        EditTextString("屈膝抬肩最大次数", "3") + "," +
                        EditTextString("YMCA卧推最大次数", "3") + "," +
                        EditTextString("坐位体前屈测量值（cm）", "3") + "," +
                        EditTextString("YMCA坐位体前屈测量值（cm）", "3") +
                        End();


        return str;
    }

    public String getCardiovascularDiseaseResult() {

        //String str = "{\"flag\":\"success\",\"vote\": [{\"id\":\"132\",\"Title\":\"题目1：输入体重（kg)\",\"stype\":\"类型1\",\"SelCount\":\"0\",\"Sel1\":\"\",\"Sel2\":\"\",\"Sel3\":\"\",\"Sel4\":\"\",\"Sel5\":\"\",\"Sel6\":\"\",\"Sel7\":\"\",\"Sel8\":\"\",\"Sel9\":\"\",\"Sel10\":\"\",\"ctype\":\"3\"},{\"id\":\"132\",\"Title\":\"题目2：输入腰围（cm)\",\"stype\":\"类型1\",\"SelCount\":\"0\",\"Sel1\":\"\",\"Sel2\":\"\",\"Sel3\":\"\",\"Sel4\":\"\",\"Sel5\":\"\",\"Sel6\":\"\",\"Sel7\":\"\",\"Sel8\":\"\",\"Sel9\":\"\",\"Sel10\":\"\",\"ctype\":\"3\"},{\"id\":\"132\",\"Title\":\"题目3：输入体脂百分比（%)\",\"stype\":\"类型1\",\"SelCount\":\"0\",\"Sel1\":\"\",\"Sel2\":\"\",\"Sel3\":\"\",\"Sel4\":\"\",\"Sel5\":\"\",\"Sel6\":\"\",\"Sel7\":\"\",\"Sel8\":\"\",\"Sel9\":\"\",\"Sel10\":\"\",\"ctype\":\"3\"},{\"id\":\"132\",\"Title\":\"题目4：输入12min.跑距离(km)\",\"stype\":\"类型1\",\"SelCount\":\"0\",\"Sel1\":\"\",\"Sel2\":\"\",\"Sel3\":\"\",\"Sel4\":\"\",\"Sel5\":\"\",\"Sel6\":\"\",\"Sel7\":\"\",\"Sel8\":\"\",\"Sel9\":\"\",\"Sel10\":\"\",\"ctype\":\"3\"}]}";
        String str =
                Begin() +
                        SingleAnswerString("111","是否存在确诊的心血管，肺部疾病和代谢性疾病？（心血管：心脏，外周血管疾病或脑血管疾病；肺部：慢性阻塞性肺部疾病，哮喘，间质性肺病或囊性纤维变形代谢；糖尿病（1型或2型）或肾脏疾病）") + "," +
                        SingleAnswerString("222","是否存在CV，肺部疾病或代谢性疾病的主要症状或体征？（疼痛，胸部，颈部，下颚，上肢或其他代表缺血的部位不适，休息或轻微运动时呼吸困难，头昏眼花或晕厥，端坐呼吸或阵发性夜间呼吸困难，脚踝水肿，心悸或心动过速，间歇性跛行，明确的心脏杂音，正常活动出现的异常疲劳或气短）") + "," +
                        MultipleAnswerString("333","", "CVD危险因素的数量",Res.q_disease.get(0), Res.q_disease.get(1),Res.q_disease.get(2),Res.q_disease.get(3),Res.q_disease.get(4),Res.q_disease.get(5),Res.q_disease.get(6),Res.q_disease.get(7)) +
                        End();


        return str;
    }


    private String EditTextString(String title, String ctype) {

        try {

            String body = "\"id\":\"132\",\"Title\":\"{0}\",\"stype\":\"类型1\",\"SelCount\":\"0\",\"Sel1\":\"\",\"Sel2\":\"\",\"Sel3\":\"\",\"Sel4\":\"\",\"Sel5\":\"\",\"Sel6\":\"\",\"Sel7\":\"\",\"Sel8\":\"\",\"Sel9\":\"\",\"Sel10\":\"\",\"ctype\":\"{1}\",\"Unit\":\"\"";

            return "{" + MessageFormat.format(body, title, ctype) + "}";
        } catch (Exception e) {

            Log.i("EditTextString", e.getMessage());

        }
        return null;

    }

    private String SingleAnswerString(String id,String title) {

        try {

            String body = "\"id\":\"{1}\",\"Title\":\"{0}\",\"stype\":\"类型2\",\"SelCount\":\"2\",\"Sel1\":\"是\",\"Sel2\":\"否\",\"Sel3\":\"\",\"Sel4\":\"\",\"Sel5\":\"\",\"Sel6\":\"\",\"Sel7\":\"\",\"Sel8\":\"\",\"Sel9\":\"\",\"Sel10\":\"\",\"ctype\":\"1\",\"Unit\":\"\"";

            return "{" + MessageFormat.format(body, title,id) + "}";
        } catch (Exception e) {

            Log.i("EditTextString", e.getMessage());

        }
        return null;

    }

    private String MultipleAnswerString(String id,String unit, String title, String... strArray) {

        try {

            String body = "\"id\":\"{16}\",\"Title\":\"{0}\",\"stype\":\"类型2\",\"SelCount\":\"{1}\",\"Sel1\":\"{2}\",\"Sel2\":\"{3}\",\"Sel3\":\"{4}\",\"Sel4\":\"{5}\",\"Sel5\":\"{6}\",\"Sel6\":\"{7}\",\"Sel7\":\"{8}\",\"Sel8\":\"{9}\",\"Sel9\":\"{10}\",\"Sel10\":\"{11}\",\"Sel11\":\"{12}\",\"Sel12\":\"{13}\",\"Sel13\":\"{14}\",\"ctype\":\"2\",\"Unit\":\"{15}\"";

            int len = strArray.length;

            String str1 = len >= 1 ? strArray[0] : "";
            String str2 = len >= 2 ? strArray[1] : "";
            String str3 = len >= 3 ? strArray[2] : "";
            String str4 = len >= 4 ? strArray[3] : "";
            String str5 = len >= 5 ? strArray[4] : "";
            String str6 = len >= 6 ? strArray[5] : "";
            String str7 = len >= 7 ? strArray[6] : "";
            String str8 = len >= 8 ? strArray[7] : "";
            String str9 = len >= 9 ? strArray[8] : "";
            String str10 = len >= 10 ? strArray[9] : "";
            String str11 = len >= 11 ? strArray[10] : "";
            String str12 = len >= 12 ? strArray[11] : "";
            String str13 = len >= 13 ? strArray[12] : "";

            String finalBody = MessageFormat.format(body, title, len + "", str1, str2, str3, str4, str5, str6, str7, str8, str9, str10,str11,str12,str13, unit,id);

            return "{" + finalBody + "}";

        } catch (Exception e) {

            Log.i("EditTextString", e.getMessage());
            return null;

        }

    }

    private String Begin() {

        return "{\"flag\":\"success\",\"vote\": [";

    }

    private String End() {

        return "]}";

    }

}
