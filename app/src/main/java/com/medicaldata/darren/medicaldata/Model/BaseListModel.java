package com.medicaldata.darren.medicaldata.Model;

/**
 * Created by Darren on 2017/8/30.
 */
public class BaseListModel {

    private String name;

    private int imageId;



    public BaseListModel(){}

    public BaseListModel(String name, int imageId){

        this.name=name;

        this.imageId=imageId;

    }



    public String getName() {

        return name;

    }

    public void setName(String name) {

        this.name = name;

    }

    public int getImageId() {

        return imageId;

    }

    public void setImageId(int imageId) {

        this.imageId = imageId;

    }

}
