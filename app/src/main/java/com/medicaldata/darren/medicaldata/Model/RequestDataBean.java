package com.medicaldata.darren.medicaldata.Model;

/**
 * Created by Darren on 2017/11/6.
 */

public class RequestDataBean {
    /**
     * FieldName : string
     * Value1 : string
     * Value2 : string
     * Id : 00000000-0000-0000-0000-000000000000
     */

    private String FieldName;
    private String Value1;
    private String Value2;
    private String Value3;
    private String Id;

    public String getFieldName() {
        return FieldName;
    }

    public void setFieldName(String FieldName) {
        this.FieldName = FieldName;
    }

    public String getValue1() {
        return Value1;
    }

    public void setValue1(String Value1) {
        this.Value1 = Value1;
    }

    public String getValue2() {
        return Value2;
    }

    public void setValue2(String Value2) {
        this.Value2 = Value2;
    }

    public String getValue3() {
        return Value3;
    }

    public void setValue3(String Value3) {
        this.Value3 = Value3;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
}
