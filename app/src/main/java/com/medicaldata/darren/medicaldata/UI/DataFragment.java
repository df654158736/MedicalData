package com.medicaldata.darren.medicaldata.UI;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medicaldata.darren.medicaldata.Base.BaseFragment;
import com.medicaldata.darren.medicaldata.Common.Res;
import com.medicaldata.darren.medicaldata.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Darren on 2018/3/30.
 */

public class DataFragment extends BaseFragment {

    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.tv_data_height)
    TextView tvDataHeight;
    @BindView(R.id.tv_data_weight)
    TextView tvDataWeight;
    @BindView(R.id.tv_data_gender)
    TextView tvDataGender;
    @BindView(R.id.tv_data_age)
    TextView tvDataAge;
    @BindView(R.id.ll_data)
    LinearLayout llData;
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    Unbinder unbinder;


    protected Context context;
    private DataAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        return view;
    }

    @Override
    protected void initData() {
        List<String> list = new ArrayList<>();
        list.add("风险筛查：|"+ Res.loginBeanModel.getRisk1Report());
        list.add("风险筛查：|"+ Res.loginBeanModel.getRisk2Report());
        list.add("心血管风险筛查：|" + Res.loginBeanModel.getCardiovascularReport() );
        list.add(TextUtils.isEmpty(Res.loginBeanModel.getSleep()) ? "我的最近一次睡眠时长为：|请去睡眠管理问卷录入至少一次睡眠记录。" : "我的最近一次睡眠时长为：|" + Res.loginBeanModel.getSleep() + "个小时。");
        list.add(Res.loginBeanModel.getBmi() == 0 ? "我的BMI：|请去体适能问卷录入至少一次体重。" : "我的BMI：|"+Res.loginBeanModel.getWeightReport());


        list.add(TextUtils.isEmpty(Res.loginBeanModel.getWaistReport()) ? "我的腰围：请去体适能问卷录入至少一次腰围|" : Res.loginBeanModel.getWaistReport()+"|");
        list.add(TextUtils.isEmpty(Res.loginBeanModel.getBodyFatReport()) ? "我的体脂：请去体适能问卷录入至少一次体脂|" : Res.loginBeanModel.getBodyFatReport()+"|");
        list.add(TextUtils.isEmpty(Res.loginBeanModel.getTwelveMinuteDistanceReport()) ? "我的12min跑距离：请去体适能问卷录入至少一次12min跑距离|" : Res.loginBeanModel.getTwelveMinuteDistanceReport()+"|");
        list.add(TextUtils.isEmpty(Res.loginBeanModel.getBenchPressReport()) ? "我的最大卧推重量：请去体适能问卷录入至少一次我的最大卧推重量|" : Res.loginBeanModel.getBenchPressReport()+"|");
        list.add(TextUtils.isEmpty(Res.loginBeanModel.getDriveLegReport()) ? "我的最大蹬腿重量：请去体适能问卷录入至少一次我的最大蹬腿重量|" : Res.loginBeanModel.getDriveLegReport()+"|");
        list.add(TextUtils.isEmpty(Res.loginBeanModel.getPushupReport()) ? "我的最大俯卧撑次数：请去体适能问卷录入至少一次我的最大俯卧撑次数|" : Res.loginBeanModel.getPushupReport()+"|");
        list.add(TextUtils.isEmpty(Res.loginBeanModel.getShoulderFlexionReport()) ? "我的最大屈膝抬肩次数：请去体适能问卷录入至少一次我的最大屈膝抬肩次数|" : Res.loginBeanModel.getShoulderFlexionReport()+"|");
        list.add(TextUtils.isEmpty(Res.loginBeanModel.getYMCABenchPressReport()) ? "我的最大YMCA卧推次数：请去体适能问卷录入至少一次我的最大YMCA卧推次数|" : Res.loginBeanModel.getYMCABenchPressReport()+"|");
        list.add(TextUtils.isEmpty(Res.loginBeanModel.getSitAnteriorReport()) ? "我的坐位体前屈测量值：请去体适能问卷录入至少一次我的坐位体前屈测量值|" : Res.loginBeanModel.getSitAnteriorReport()+"|");
        list.add(TextUtils.isEmpty(Res.loginBeanModel.getYMCASitAnteriorReport()) ? "我的YMCA坐位体前屈测量值：请去体适能问卷录入至少一次我的YMCA坐位体前屈测量值|" : Res.loginBeanModel.getYMCASitAnteriorReport()+"|");

        adapter = new DataAdapter(context,list);

        myRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        myRecyclerView.addItemDecoration(new DividerItemDecoration());

        myRecyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public class DividerItemDecoration extends RecyclerView.ItemDecoration {

        private float mDividerHeight;

        private Paint mPaint;

        public DividerItemDecoration() {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.BLACK);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

//          //第一个ItemView不需要在上面绘制分割线
            if (parent.getChildAdapterPosition(view) != 0){
                //这里直接硬编码为1px
                outRect.top = 1;
                mDividerHeight = 1;
            }
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);

            int childCount = parent.getChildCount();

            for ( int i = 0; i < childCount; i++ ) {
                View view = parent.getChildAt(i);

                int index = parent.getChildAdapterPosition(view);
                //第一个ItemView不需要绘制
                if ( index == 0 ) {
                    continue;
                }

                float dividerTop = view.getTop() - mDividerHeight;
                float dividerLeft = parent.getPaddingLeft();
                float dividerBottom = view.getTop();
                float dividerRight = parent.getWidth() - parent.getPaddingRight();

                c.drawRect(dividerLeft,dividerTop,dividerRight,dividerBottom,mPaint);
            }
        }

    }

    private class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        public static final int TYPE_Text = 0;
        public static final int TYPE_TextAndButton = 1;

        private Context mContext;
        private List<String> mDatas;

        public DataAdapter(Context context, List<String> datas) {
            super();
            this.mContext = context;
            this.mDatas = datas;
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater mInflater = LayoutInflater.from(mContext);
            View view;
            if (viewType == TYPE_Text) {
                view = mInflater.inflate(R.layout.item_datafragment_text, parent,false);
                Item1ViewHolder holder = new Item1ViewHolder(view);
                return holder;
            } else {
                view = mInflater.inflate(R.layout.item_datafragment_textandbutton, parent,false);
                Item2ViewHolder holder = new Item2ViewHolder(view);
                return holder;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (position > 2) {
                Item2ViewHolder mholder2 = (Item2ViewHolder) holder;
                String[] arry = mDatas.get(position).split("[|]");
                mholder2.mTextViewDes1.setText(arry[0]);
                if(arry.length >1){
                    mholder2.mTextViewDes2.setText(arry[1]);
                }

            } else {
                Item1ViewHolder mholder1 = (Item1ViewHolder) holder;
                String[] arry = mDatas.get(position).split("[|]");
                mholder1.mTextViewDes1.setText(arry[0]);
                if(arry.length >1){
                    mholder1.mTextViewDes2.setText(arry[1]);
                }

            }
        }

        @Override
        public int getItemViewType(int position) {
            return position > 2 ? TYPE_TextAndButton : TYPE_Text;
        }

        @Override
        public int getItemCount() {
            return  mDatas.size();
        }

        //item1 的ViewHolder
        public class Item1ViewHolder extends RecyclerView.ViewHolder {
            TextView mTextViewDes1;
            TextView mTextViewDes2;

            public Item1ViewHolder(View itemView) {
                super(itemView);
                mTextViewDes1 = itemView.findViewById(R.id.tv_data_des1);
                mTextViewDes2 = itemView.findViewById(R.id.tv_data_des2);
            }
        }

        //item2 的ViewHolder
        public class Item2ViewHolder extends RecyclerView.ViewHolder {
            TextView mTextViewDes1;
            TextView mTextViewDes2;
            Button mButton;

            public Item2ViewHolder(View itemView) {
                super(itemView);
                mTextViewDes1 = itemView.findViewById(R.id.tv_data2_des1);
                mTextViewDes2 = itemView.findViewById(R.id.tv_data2_des2);
                mButton = itemView.findViewById(R.id.bt_data_long);
            }
        }

    }
}
