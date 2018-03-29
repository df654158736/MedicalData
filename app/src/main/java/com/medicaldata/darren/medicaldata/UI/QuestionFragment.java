package com.medicaldata.darren.medicaldata.UI;

import android.content.Intent;
import android.os.Bundle;

import com.medicaldata.darren.medicaldata.Base.BaseListFragment;
import com.medicaldata.darren.medicaldata.Common.JsonResult;
import com.medicaldata.darren.medicaldata.Common.Res;
import com.medicaldata.darren.medicaldata.Model.BaseListModel;
import com.medicaldata.darren.medicaldata.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darren on 2018/3/29.
 */

public class QuestionFragment extends BaseListFragment {

    List<BaseListModel> mainList=new ArrayList();
    @Override
    public void fillData(List<BaseListModel> items) {
        items.add(new BaseListModel(Res.question_list_title.get(0), R.drawable.shaichawenjuan));
        items.add(new BaseListModel(Res.question_list_title.get(1),R.drawable.shaichawenjuan));
        items.add(new BaseListModel(Res.question_list_title.get(2),R.drawable.wenjuan));
        items.add(new BaseListModel(Res.question_list_title.get(3),R.drawable.wenjuan));

        mainList = items;
    }

    @Override
    public String setTitleName() {
        return "健康问答";
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                Intent bodyDataIntent =new Intent(getActivity(),QuestionnaireActivity.class);
                Bundle bodyDataBundle = new Bundle() ;
                bodyDataBundle.putString("QuestionJson",new JsonResult().getRiskSubjectResult()) ;
                bodyDataBundle.putString("Title", mainList.get(position).getName()) ;
                bodyDataIntent.putExtras(bodyDataBundle) ;
                startActivity(bodyDataIntent);
                return;
            case 1:
                Intent sportIntent =new Intent(getActivity(),QuestionnaireActivity.class);
                Bundle sportBundle = new Bundle() ;
                sportBundle.putString("QuestionJson",new JsonResult().getSleepResult()) ;
                sportBundle.putString("Title", mainList.get(position).getName()) ;
                sportIntent.putExtras(sportBundle) ;
                startActivity(sportIntent);
                return;
            case 2:
                Intent CardiovasscilarDiseaseIntent =new Intent(getActivity(),QuestionnaireActivity.class);
                Bundle CardiovasscilarDiseaseBundle = new Bundle() ;
                CardiovasscilarDiseaseBundle.putString("QuestionJson",new JsonResult().getCardiovascularDiseaseResult()) ;
                CardiovasscilarDiseaseBundle.putString("Title", mainList.get(position).getName()) ;
                CardiovasscilarDiseaseIntent.putExtras(CardiovasscilarDiseaseBundle) ;
                startActivity(CardiovasscilarDiseaseIntent);
                return;
            case 3:
                Intent physicalAgilityIntent =new Intent(getActivity(),QuestionnaireActivity.class);
                Bundle physicalAgilityBundle = new Bundle() ;
                physicalAgilityBundle.putString("QuestionJson",new JsonResult().getPhysicalAgilitySubjectResult()) ;
                physicalAgilityBundle.putString("Title", mainList.get(position).getName()) ;
                physicalAgilityIntent.putExtras(physicalAgilityBundle) ;
                startActivity(physicalAgilityIntent);
                return;
        }
    }

}
