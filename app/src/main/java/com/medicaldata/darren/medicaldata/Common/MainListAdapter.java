package com.medicaldata.darren.medicaldata.Common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.medicaldata.darren.medicaldata.Model.BaseListModel;
import com.medicaldata.darren.medicaldata.R;

import java.util.List;

/**
 * Created by Darren on 2017/8/30.
 */
public class MainListAdapter extends ArrayAdapter<BaseListModel> {

    private int resourceId;

    public MainListAdapter(Context context, int textViewResourceId,

                           List<BaseListModel> objects) {

        super(context, textViewResourceId, objects);

        resourceId=textViewResourceId;

    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        BaseListModel mainlist=getItem(position);//获取当前项的Fruit实例

        View view;

        if(convertView==null){
            view=LayoutInflater.from(getContext()).inflate(resourceId, null);
        }else{
            view=convertView;
        }

        ImageView image=(ImageView) view.findViewById(R.id.base_list_image);
        TextView name=(TextView) view.findViewById(R.id.base_list_name);
        image.setImageResource(mainlist.getImageId());
        name.setText(mainlist.getName());
        return view;

    }



}
