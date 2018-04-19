package com.medicaldata.darren.medicaldata.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.medicaldata.darren.medicaldata.Base.BaseListFragment;
import com.medicaldata.darren.medicaldata.Common.Res;
import com.medicaldata.darren.medicaldata.Model.BaseListModel;
import com.medicaldata.darren.medicaldata.R;

import java.util.List;

/**
 * Created by Darren on 2018/3/30.
 */

public class MyFragment  extends BaseListFragment{
    @Override
    public void fillData(List<BaseListModel> items) {
        items.add(new BaseListModel(Res.my_list_title.get(0), R.drawable.my));
        items.add(new BaseListModel(Res.my_list_title.get(1), R.drawable.wodeshebei));
        items.add(new BaseListModel(Res.my_list_title.get(2), R.drawable.xitongshebei));
        items.add(new BaseListModel(Res.my_list_title.get(3), R.drawable.yonghuguanlian));
        items.add(new BaseListModel(Res.my_list_title.get(4), R.drawable.xitongshebei));
        items.add(new BaseListModel(Res.my_list_title.get(5), R.drawable.yonghuguanlian));
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 2:
                Intent intent = new Intent(getActivity(), MyBaseDataActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 3:
                Intent stepIntent = new Intent(getActivity(), com.medicaldata.darren.medicaldata.Step.activity.MainActivity.class);
                Bundle stepBundle = new Bundle();
                stepIntent.putExtras(stepBundle);
                startActivity(stepIntent);
                break;
            case 4:
                Intent bongIntent = new Intent(getActivity(), com.medicaldata.darren.medicaldata.Bong.MainActivity.class);
                Bundle bongBundle = new Bundle();
                bongIntent.putExtras(bongBundle);
                startActivity(bongIntent);
                break;
            case 5:
                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(loginIntent);
                break;
            default:
                Toast.makeText(getContext(), "暂未开放", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public String setTitleName() {
        return "我的";
    }
}
