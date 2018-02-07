package com.medicaldata.darren.medicaldata.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.medicaldata.darren.medicaldata.Common.AsyncHttpClientUtils;
import com.medicaldata.darren.medicaldata.Common.JsonResult;
import com.medicaldata.darren.medicaldata.Common.MainListAdapter;
import com.medicaldata.darren.medicaldata.Common.Res;
import com.medicaldata.darren.medicaldata.Common.mUtil;
import com.medicaldata.darren.medicaldata.Model.ChartDataBean;
import com.medicaldata.darren.medicaldata.Model.LoginBean;
import com.medicaldata.darren.medicaldata.Model.MainListModel;
import com.medicaldata.darren.medicaldata.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout tabLayout;
    private List<Fragment> fragments;
    private ViewPager mViewPager;


    //退出时的时间
    private long mExitTime;
    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出健康数据系统", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            for (Activity activity: Res.activityLists) {
                activity.finish();
            }

            Res.activityLists.clear();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        Res.activityLists.add(MainActivity.this);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        initValue();


    }

    private void initValue() {
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setCustomView(getTabView(0));
        tabLayout.getTabAt(1).setCustomView(getTabView(1));
        tabLayout.getTabAt(2).setCustomView(getTabView(2));
        tabLayout.getTabAt(3).setCustomView(getTabView(3));
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setText(Res.tab_titles.get(position));
        ImageView img_title = (ImageView) view.findViewById(R.id.img_title);
        img_title.setImageResource(Res.q_tabIcons[position]);
        return view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class QuestionFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        List<MainListModel> mainList=new ArrayList<MainListModel>();

        public QuestionFragment() {

            mainList.add(new MainListModel(Res.question_list_title.get(0),R.drawable.shaichawenjuan));
            mainList.add(new MainListModel(Res.question_list_title.get(1),R.drawable.shaichawenjuan));
            mainList.add(new MainListModel(Res.question_list_title.get(2),R.drawable.wenjuan));
            mainList.add(new MainListModel(Res.question_list_title.get(3),R.drawable.wenjuan));
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static QuestionFragment newInstance(int sectionNumber) {
            QuestionFragment fragment = new QuestionFragment();
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_question, container, false);
            ListView listView = (ListView) rootView.findViewById(R.id.questionfragmentlist);

            MainListAdapter adapter=new MainListAdapter(getContext(),
                    R.layout.item_mainlist, mainList);

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
            });

            return rootView;
        }
    }

    public static class DataFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private  LinearLayout ll_report1;
        private  LinearLayout ll_report2;
        private  LinearLayout ll_cardiovasculardisease_report;
        private View view_report;

        private  TextView risk_report1;
        private  TextView risk_report2;
        private  TextView cardiovasculardisease_report;

        private TextView sleep;
        private TextView bmi;
        private TextView bmireport;
        private TextView waist;
        private TextView waistreport;
        private TextView tizhi;
        private TextView tizhireport;
        private TextView twelveminutedistance;
        private TextView twelveminutedistancereport;
        private TextView benchpress;
        private TextView benchpressreport;
        private TextView pushup;
        private TextView pushupreport;
        private TextView shoulderflexion;
        private TextView shoulderflexionreport;
        private TextView ymcabenchpress;
        private TextView ymcabenchpressreport;
        private TextView sitanterior;
        private TextView sitanteriorreport;
        private TextView driveleg;
        private TextView drivelegreport;
        private TextView ymcasitanterior;
        private TextView ymcasitanteriorreport;

        private TextView height;
        private TextView weight;
        private TextView gender;
        private TextView age;

        private Gson gson;

        public DataFragment() {

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static DataFragment newInstance(int sectionNumber) {

            DataFragment fragment = new DataFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_data, container, false);

            height = (TextView) rootView.findViewById(R.id.tv_data_height);
            weight = (TextView) rootView.findViewById(R.id.tv_data_weight);
            gender = (TextView) rootView.findViewById(R.id.tv_data_gender);
            age = (TextView) rootView.findViewById(R.id.tv_data_age);

            sleep = (TextView) rootView.findViewById(R.id.tv_data_sleep);
            bmi = (TextView) rootView.findViewById(R.id.tv_data_bmi);
            bmireport = (TextView) rootView.findViewById(R.id.tv_data_bmireport);

            waist = (TextView) rootView.findViewById(R.id.tv_data_waist);
            waistreport = (TextView) rootView.findViewById(R.id.tv_data_waistreport);

            tizhi = (TextView) rootView.findViewById(R.id.tv_data_tizhi);
            tizhireport = (TextView) rootView.findViewById(R.id.tv_data_tizhireport);

            twelveminutedistance = (TextView) rootView.findViewById(R.id.tv_data_twelveminutedistance);
            twelveminutedistancereport = (TextView) rootView.findViewById(R.id.tv_data_twelveminutedistancereport);

            benchpress = (TextView) rootView.findViewById(R.id.tv_data_benchpress);
            benchpressreport = (TextView) rootView.findViewById(R.id.tv_data_benchpressreport);

            driveleg = (TextView) rootView.findViewById(R.id.tv_data_driveleg);
            drivelegreport = (TextView) rootView.findViewById(R.id.tv_data_drivelegreport);

            pushup = (TextView) rootView.findViewById(R.id.tv_data_pushup);
            pushupreport = (TextView) rootView.findViewById(R.id.tv_data_pushupreport);

            shoulderflexion = (TextView) rootView.findViewById(R.id.tv_data_shoulderflexion);
            shoulderflexionreport = (TextView) rootView.findViewById(R.id.tv_data_shoulderflexionreport);

            ymcabenchpress = (TextView) rootView.findViewById(R.id.tv_data_ymcabenchpress);
            ymcabenchpressreport = (TextView) rootView.findViewById(R.id.tv_data_ymcabenchpressreport);

            sitanterior = (TextView) rootView.findViewById(R.id.tv_data_sitanterior);
            sitanteriorreport = (TextView) rootView.findViewById(R.id.tv_data_sitanteriorreport);

            ymcasitanterior = (TextView) rootView.findViewById(R.id.tv_data_ymcasitanterior);
            ymcasitanteriorreport = (TextView) rootView.findViewById(R.id.tv_data_ymcasitanteriorreport);

            ll_report1 = (LinearLayout) rootView.findViewById(R.id.ll_risk_report1);
            ll_report2 = (LinearLayout) rootView.findViewById(R.id.ll_risk_report2);
            ll_cardiovasculardisease_report = (LinearLayout) rootView.findViewById(R.id.ll_cardiovasculardisease_report);

            view_report = (View) rootView.findViewById(R.id.v_report);

            risk_report1 = (TextView) rootView.findViewById(R.id.tv_risk_report1);
            risk_report2 = (TextView) rootView.findViewById(R.id.tv_risk_report2);
            cardiovasculardisease_report = (TextView) rootView.findViewById(R.id.tv_cardiovasculardisease_report);

            gson = new Gson();

            InitButton(rootView);
            initData();
            ResetState();

            return rootView;
        }

        private void InitButton(View rootView) {

            //region btsleep
            Button btsleep = (Button) rootView.findViewById(R.id.bt_sleep);
            btsleep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RequestParams requestParams = new RequestParams();
                    requestParams.put("FieldName","Sleep");
                    requestParams.put("Id", Res.loginBeanModel.getId());
                    // Simulate network access.
                    AsyncHttpClientUtils.getInstance().post("Data/LoadUserData",requestParams, new TextHttpResponseHandler(){

                        @Override
                        public void onSuccess(int i, Header[] headers, String s) {

                            //Json的解析类对象
                            JsonParser parser = new JsonParser();
                            //将JSON的String 转成一个JsonArray对象
                            JsonArray jsonArray = parser.parse(s).getAsJsonArray();
                            String[] datearray = new String[jsonArray.size()];
                            float[] objectarray = new float[jsonArray.size()];
                            int count = 0;
                            for (JsonElement user : jsonArray) {
                                ChartDataBean chartDataBean = gson.fromJson(user, ChartDataBean.class);
                                datearray[count] = mUtil.getSimpleDateFormate(chartDataBean.getCreatedTime());
                                objectarray[count] = chartDataBean.getSpendHour();
                                count++;
                            }

                            Intent intent =   new Intent(getActivity(),LineChartActivity.class);
                            Bundle bundle = new Bundle() ;
                            bundle.putSerializable("Data", datearray) ;
                            bundle.putSerializable("Score", objectarray) ;
                            bundle.putString("Type", "Float1") ;
                            bundle.putString("YName", "睡眠时间") ;
                            bundle.putString("Title", "睡眠时间连续分布表") ;
                            intent.putExtras(bundle) ;
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.i("MainActivity","|"+s+"|");
                        }
                    });

                }
            });
//endregion

            //region btbmi
            Button btbmi = (Button) rootView.findViewById(R.id.bt_bmi);
            btbmi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RequestParams requestParams = new RequestParams();
                    requestParams.put("FieldName","PA_Weight");
                    requestParams.put("Id", Res.loginBeanModel.getId());
                    // Simulate network access.
                    AsyncHttpClientUtils.getInstance().post("Data/LoadUserData",requestParams, new TextHttpResponseHandler(){

                        @Override
                        public void onSuccess(int i, Header[] headers, String s) {

                            //Json的解析类对象
                            JsonParser parser = new JsonParser();
                            //将JSON的String 转成一个JsonArray对象
                            JsonArray jsonArray = parser.parse(s).getAsJsonArray();
                            String[] datearray = new String[jsonArray.size()];
                            float[] objectarray = new float[jsonArray.size()];
                            int count = 0;
                            for (JsonElement user : jsonArray) {
                                ChartDataBean chartDataBean = gson.fromJson(user, ChartDataBean.class);
                                datearray[count] = mUtil.getSimpleDateFormate(chartDataBean.getCreatedTime());
                                objectarray[count] = chartDataBean.getBMI();
                                count++;
                            }

                            Intent intent =   new Intent(getActivity(),LineChartActivity.class);
                            Bundle bundle = new Bundle() ;
                            bundle.putSerializable("Data", datearray) ;
                            bundle.putSerializable("Score", objectarray) ;
                            bundle.putString("Type", "Float") ;
                            bundle.putString("YName", "BMI值") ;
                            bundle.putString("Title", "BMI值连续分布表") ;
                            intent.putExtras(bundle) ;
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.i("MainActivity","|"+s+"|");
                        }
                    });

                }
            });
//endregion

            //region bttizhi
            Button bttizhi = (Button) rootView.findViewById(R.id.bt_tizhi);
            bttizhi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RequestParams requestParams = new RequestParams();
                    requestParams.put("FieldName","PA_BodyFat");
                    requestParams.put("Id", Res.loginBeanModel.getId());
                    // Simulate network access.
                    AsyncHttpClientUtils.getInstance().post("Data/LoadUserData",requestParams, new TextHttpResponseHandler(){

                        @Override
                        public void onSuccess(int i, Header[] headers, String s) {

                            //Json的解析类对象
                            JsonParser parser = new JsonParser();
                            //将JSON的String 转成一个JsonArray对象
                            JsonArray jsonArray = parser.parse(s).getAsJsonArray();
                            String[] datearray = new String[jsonArray.size()];
                            int[] objectarray = new int[jsonArray.size()];
                            int count = 0;
                            for (JsonElement user : jsonArray) {
                                ChartDataBean chartDataBean = gson.fromJson(user, ChartDataBean.class);
                                datearray[count] = mUtil.getSimpleDateFormate(chartDataBean.getCreatedTime());
                                objectarray[count] = chartDataBean.getBodyFat();
                                count++;
                            }

                            Intent intent =   new Intent(getActivity(),LineChartActivity.class);
                            Bundle bundle = new Bundle() ;
                            bundle.putSerializable("Data", datearray) ;
                            bundle.putSerializable("Score", objectarray) ;
                            bundle.putString("Type", "Int") ;
                            bundle.putString("YName", "体脂值") ;
                            bundle.putString("Title", "体脂值连续分布表") ;
                            intent.putExtras(bundle) ;
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.i("MainActivity","|"+s+"|");
                        }
                    });
                }
            });
//endregion

            //region waist
            Button bt_waist = (Button) rootView.findViewById(R.id.bt_waist);
            bt_waist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RequestParams requestParams = new RequestParams();
                    requestParams.put("FieldName","PA_Waist");
                    requestParams.put("Id", Res.loginBeanModel.getId());
                    // Simulate network access.
                    AsyncHttpClientUtils.getInstance().post("Data/LoadUserData",requestParams, new TextHttpResponseHandler(){

                        @Override
                        public void onSuccess(int i, Header[] headers, String s) {

                            //Json的解析类对象
                            JsonParser parser = new JsonParser();
                            //将JSON的String 转成一个JsonArray对象
                            JsonArray jsonArray = parser.parse(s).getAsJsonArray();
                            String[] datearray = new String[jsonArray.size()];
                            int[] objectarray = new int[jsonArray.size()];
                            int count = 0;
                            for (JsonElement user : jsonArray) {
                                ChartDataBean chartDataBean = gson.fromJson(user, ChartDataBean.class);
                                datearray[count] = mUtil.getSimpleDateFormate(chartDataBean.getCreatedTime());
                                objectarray[count] = chartDataBean.getWaist();
                                count++;
                            }

                            Intent intent =   new Intent(getActivity(),LineChartActivity.class);
                            Bundle bundle = new Bundle() ;
                            bundle.putSerializable("Data", datearray) ;
                            bundle.putSerializable("Score", objectarray) ;
                            bundle.putString("Type", "Int") ;
                            bundle.putString("YName", "腰围值") ;
                            bundle.putString("Title", "腰围值连续分布表") ;
                            intent.putExtras(bundle) ;
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.i("MainActivity","|"+s+"|");
                        }
                    });
                }
            });
//endregion

            //region twelveminutedistance
            Button bt_twelveminutedistance = (Button) rootView.findViewById(R.id.bt_twelveminutedistance);
            bt_twelveminutedistance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RequestParams requestParams = new RequestParams();
                    requestParams.put("FieldName","PA_TwelveMinuteDistance");
                    requestParams.put("Id", Res.loginBeanModel.getId());
                    // Simulate network access.
                    AsyncHttpClientUtils.getInstance().post("Data/LoadUserData",requestParams, new TextHttpResponseHandler(){

                        @Override
                        public void onSuccess(int i, Header[] headers, String s) {

                            //Json的解析类对象
                            JsonParser parser = new JsonParser();
                            //将JSON的String 转成一个JsonArray对象
                            JsonArray jsonArray = parser.parse(s).getAsJsonArray();
                            String[] datearray = new String[jsonArray.size()];
                            float[] objectarray = new float[jsonArray.size()];
                            int count = 0;
                            for (JsonElement user : jsonArray) {
                                ChartDataBean chartDataBean = gson.fromJson(user, ChartDataBean.class);
                                datearray[count] = mUtil.getSimpleDateFormate(chartDataBean.getCreatedTime());
                                objectarray[count] = chartDataBean.getTwelveMinuteDistance();
                                count++;
                            }

                            Intent intent =   new Intent(getActivity(),LineChartActivity.class);
                            Bundle bundle = new Bundle() ;
                            bundle.putSerializable("Data", datearray) ;
                            bundle.putSerializable("Score", objectarray) ;
                            bundle.putString("Type", "Float") ;
                            bundle.putString("YName", "12Min跑距离值") ;
                            bundle.putString("Title", "12Min跑距离值连续分布表") ;
                            bundle.putInt("MaxValue", 10); ;

                            intent.putExtras(bundle) ;
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.i("MainActivity","|"+s+"|");
                        }
                    });
                }
            });
//endregion

            //region benchpress
            Button bt_benchpress = (Button) rootView.findViewById(R.id.bt_benchpress);
            bt_benchpress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RequestParams requestParams = new RequestParams();
                    requestParams.put("FieldName","PA_BenchPress");
                    requestParams.put("Id", Res.loginBeanModel.getId());
                    // Simulate network access.
                    AsyncHttpClientUtils.getInstance().post("Data/LoadUserData",requestParams, new TextHttpResponseHandler(){

                        @Override
                        public void onSuccess(int i, Header[] headers, String s) {

                            //Json的解析类对象
                            JsonParser parser = new JsonParser();
                            //将JSON的String 转成一个JsonArray对象
                            JsonArray jsonArray = parser.parse(s).getAsJsonArray();
                            String[] datearray = new String[jsonArray.size()];
                            int[] objectarray = new int[jsonArray.size()];
                            int count = 0;
                            for (JsonElement user : jsonArray) {
                                ChartDataBean chartDataBean = gson.fromJson(user, ChartDataBean.class);
                                datearray[count] = mUtil.getSimpleDateFormate(chartDataBean.getCreatedTime());
                                objectarray[count] = chartDataBean.getBenchPress();
                                count++;
                            }

                            Intent intent =   new Intent(getActivity(),LineChartActivity.class);
                            Bundle bundle = new Bundle() ;
                            bundle.putSerializable("Data", datearray) ;
                            bundle.putSerializable("Score", objectarray) ;
                            bundle.putString("Type", "Int") ;
                            bundle.putString("YName", "最大卧推重量值") ;
                            bundle.putString("Title", "最大卧推重量值连续分布表") ;
                            intent.putExtras(bundle) ;
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.i("MainActivity","|"+s+"|");
                        }
                    });
                }
            });
//endregion

            //region driveleg
            Button bt_driveleg = (Button) rootView.findViewById(R.id.bt_driveleg);
            bt_driveleg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RequestParams requestParams = new RequestParams();
                    requestParams.put("FieldName","PA_DriveLeg");
                    requestParams.put("Id", Res.loginBeanModel.getId());
                    // Simulate network access.
                    AsyncHttpClientUtils.getInstance().post("Data/LoadUserData",requestParams, new TextHttpResponseHandler(){

                        @Override
                        public void onSuccess(int i, Header[] headers, String s) {

                            //Json的解析类对象
                            JsonParser parser = new JsonParser();
                            //将JSON的String 转成一个JsonArray对象
                            JsonArray jsonArray = parser.parse(s).getAsJsonArray();
                            String[] datearray = new String[jsonArray.size()];
                            int[] objectarray = new int[jsonArray.size()];
                            int count = 0;
                            for (JsonElement user : jsonArray) {
                                ChartDataBean chartDataBean = gson.fromJson(user, ChartDataBean.class);
                                datearray[count] = mUtil.getSimpleDateFormate(chartDataBean.getCreatedTime());
                                objectarray[count] = chartDataBean.getDriveLeg();
                                count++;
                            }

                            Intent intent =   new Intent(getActivity(),LineChartActivity.class);
                            Bundle bundle = new Bundle() ;
                            bundle.putSerializable("Data", datearray) ;
                            bundle.putSerializable("Score", objectarray) ;
                            bundle.putString("Type", "Int") ;
                            bundle.putString("YName", "最大蹬腿重量值") ;
                            bundle.putString("Title", "最大蹬腿重量值连续分布表") ;
                            intent.putExtras(bundle) ;
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.i("MainActivity","|"+s+"|");
                        }
                    });
                }
            });
//endregion

            //region pushup
            Button bt_pushup = (Button) rootView.findViewById(R.id.bt_pushup);
            bt_pushup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RequestParams requestParams = new RequestParams();
                    requestParams.put("FieldName","PA_Pushup");
                    requestParams.put("Id", Res.loginBeanModel.getId());
                    // Simulate network access.
                    AsyncHttpClientUtils.getInstance().post("Data/LoadUserData",requestParams, new TextHttpResponseHandler(){

                        @Override
                        public void onSuccess(int i, Header[] headers, String s) {

                            //Json的解析类对象
                            JsonParser parser = new JsonParser();
                            //将JSON的String 转成一个JsonArray对象
                            JsonArray jsonArray = parser.parse(s).getAsJsonArray();
                            String[] datearray = new String[jsonArray.size()];
                            int[] objectarray = new int[jsonArray.size()];
                            int count = 0;
                            for (JsonElement user : jsonArray) {
                                ChartDataBean chartDataBean = gson.fromJson(user, ChartDataBean.class);
                                datearray[count] = mUtil.getSimpleDateFormate(chartDataBean.getCreatedTime());
                                objectarray[count] = chartDataBean.getPushup();
                                count++;
                            }

                            Intent intent =   new Intent(getActivity(),LineChartActivity.class);
                            Bundle bundle = new Bundle() ;
                            bundle.putSerializable("Data", datearray) ;
                            bundle.putSerializable("Score", objectarray) ;
                            bundle.putString("Type", "Int") ;
                            bundle.putString("YName", "最大俯卧撑次数") ;
                            bundle.putString("Title", "最大俯卧撑次数连续分布表") ;
                            intent.putExtras(bundle) ;
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.i("MainActivity","|"+s+"|");
                        }
                    });
                }
            });
//endregion

            //region shoulderflexion
            Button bt_shoulderflexion = (Button) rootView.findViewById(R.id.bt_shoulderflexion);
            bt_shoulderflexion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RequestParams requestParams = new RequestParams();
                    requestParams.put("FieldName","PA_ShoulderFlexion");
                    requestParams.put("Id", Res.loginBeanModel.getId());
                    // Simulate network access.
                    AsyncHttpClientUtils.getInstance().post("Data/LoadUserData",requestParams, new TextHttpResponseHandler(){

                        @Override
                        public void onSuccess(int i, Header[] headers, String s) {

                            //Json的解析类对象
                            JsonParser parser = new JsonParser();
                            //将JSON的String 转成一个JsonArray对象
                            JsonArray jsonArray = parser.parse(s).getAsJsonArray();
                            String[] datearray = new String[jsonArray.size()];
                            int[] objectarray = new int[jsonArray.size()];
                            int count = 0;
                            for (JsonElement user : jsonArray) {
                                ChartDataBean chartDataBean = gson.fromJson(user, ChartDataBean.class);
                                datearray[count] = mUtil.getSimpleDateFormate(chartDataBean.getCreatedTime());
                                objectarray[count] = chartDataBean.getShoulderFlexion();
                                count++;
                            }

                            Intent intent =   new Intent(getActivity(),LineChartActivity.class);
                            Bundle bundle = new Bundle() ;
                            bundle.putSerializable("Data", datearray) ;
                            bundle.putSerializable("Score", objectarray) ;
                            bundle.putString("Type", "Int") ;
                            bundle.putString("YName", "最大屈膝抬肩次数") ;
                            bundle.putString("Title", "最大屈膝抬肩次数连续分布表") ;
                            intent.putExtras(bundle) ;
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.i("MainActivity","|"+s+"|");
                        }
                    });
                }
            });
//endregion

            //region ymcabenchpress
            Button bt_ymcabenchpress = (Button) rootView.findViewById(R.id.bt_ymcabenchpress);
            bt_ymcabenchpress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RequestParams requestParams = new RequestParams();
                    requestParams.put("FieldName","PA_YMCABenchPress");
                    requestParams.put("Id", Res.loginBeanModel.getId());
                    // Simulate network access.
                    AsyncHttpClientUtils.getInstance().post("Data/LoadUserData",requestParams, new TextHttpResponseHandler(){

                        @Override
                        public void onSuccess(int i, Header[] headers, String s) {

                            //Json的解析类对象
                            JsonParser parser = new JsonParser();
                            //将JSON的String 转成一个JsonArray对象
                            JsonArray jsonArray = parser.parse(s).getAsJsonArray();
                            String[] datearray = new String[jsonArray.size()];
                            int[] objectarray = new int[jsonArray.size()];
                            int count = 0;
                            for (JsonElement user : jsonArray) {
                                ChartDataBean chartDataBean = gson.fromJson(user, ChartDataBean.class);
                                datearray[count] = mUtil.getSimpleDateFormate(chartDataBean.getCreatedTime());
                                objectarray[count] = chartDataBean.getYMCABenchPress();
                                count++;
                            }

                            Intent intent =   new Intent(getActivity(),LineChartActivity.class);
                            Bundle bundle = new Bundle() ;
                            bundle.putSerializable("Data", datearray) ;
                            bundle.putSerializable("Score", objectarray) ;
                            bundle.putString("Type", "Int") ;
                            bundle.putString("YName", "最大YMCA卧推次数") ;
                            bundle.putString("Title", "最大YMCA卧推次数连续分布表") ;
                            intent.putExtras(bundle) ;
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.i("MainActivity","|"+s+"|");
                        }
                    });
                }
            });
//endregion

            //region sitanterior
            Button bt_sitanterior = (Button) rootView.findViewById(R.id.bt_sitanterior);
            bt_sitanterior.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RequestParams requestParams = new RequestParams();
                    requestParams.put("FieldName","PA_SitAnterior");
                    requestParams.put("Id", Res.loginBeanModel.getId());
                    // Simulate network access.
                    AsyncHttpClientUtils.getInstance().post("Data/LoadUserData",requestParams, new TextHttpResponseHandler(){

                        @Override
                        public void onSuccess(int i, Header[] headers, String s) {

                            //Json的解析类对象
                            JsonParser parser = new JsonParser();
                            //将JSON的String 转成一个JsonArray对象
                            JsonArray jsonArray = parser.parse(s).getAsJsonArray();
                            String[] datearray = new String[jsonArray.size()];
                            int[] objectarray = new int[jsonArray.size()];
                            int count = 0;
                            for (JsonElement user : jsonArray) {
                                ChartDataBean chartDataBean = gson.fromJson(user, ChartDataBean.class);
                                datearray[count] = mUtil.getSimpleDateFormate(chartDataBean.getCreatedTime());
                                objectarray[count] = chartDataBean.getSitAnterior();
                                count++;
                            }

                            Intent intent =   new Intent(getActivity(),LineChartActivity.class);
                            Bundle bundle = new Bundle() ;
                            bundle.putSerializable("Data", datearray) ;
                            bundle.putSerializable("Score", objectarray) ;
                            bundle.putString("Type", "Int") ;
                            bundle.putString("YName", "坐位体前屈测量值") ;
                            bundle.putString("Title", "坐位体前屈测量值连续分布表") ;
                            intent.putExtras(bundle) ;
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.i("MainActivity","|"+s+"|");
                        }
                    });
                }
            });
//endregion

            //region ymcasitanterior
            Button bt_ymcasitanterior = (Button) rootView.findViewById(R.id.bt_ymcasitanterior);
            bt_ymcasitanterior.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RequestParams requestParams = new RequestParams();
                    requestParams.put("FieldName","PA_YMCASitAnterior");
                    requestParams.put("Id", Res.loginBeanModel.getId());
                    // Simulate network access.
                    AsyncHttpClientUtils.getInstance().post("Data/LoadUserData",requestParams, new TextHttpResponseHandler(){

                        @Override
                        public void onSuccess(int i, Header[] headers, String s) {

                            //Json的解析类对象
                            JsonParser parser = new JsonParser();
                            //将JSON的String 转成一个JsonArray对象
                            JsonArray jsonArray = parser.parse(s).getAsJsonArray();
                            String[] datearray = new String[jsonArray.size()];
                            int[] objectarray = new int[jsonArray.size()];
                            int count = 0;
                            for (JsonElement user : jsonArray) {
                                ChartDataBean chartDataBean = gson.fromJson(user, ChartDataBean.class);
                                datearray[count] = mUtil.getSimpleDateFormate(chartDataBean.getCreatedTime());
                                objectarray[count] = chartDataBean.getYMCASitAnterior();
                                count++;
                            }

                            Intent intent =   new Intent(getActivity(),LineChartActivity.class);
                            Bundle bundle = new Bundle() ;
                            bundle.putSerializable("Data", datearray) ;
                            bundle.putSerializable("Score", objectarray) ;
                            bundle.putString("Type", "Int") ;
                            bundle.putString("YName", "YMCA坐位体前屈测量值") ;
                            bundle.putString("Title", "YMCA坐位体前屈测量值连续分布表") ;
                            intent.putExtras(bundle) ;
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.i("MainActivity","|"+s+"|");
                        }
                    });
                }
            });
            //endregion

        }

        private void initData() {
            height.setText(Res.loginBeanModel.getHeight()+"cm");
            weight.setText(Res.loginBeanModel.getWeight());
            gender.setText(Res.loginBeanModel.getGenderText());
            age.setText(Res.loginBeanModel.getAge()+"");

            sleep.setText(TextUtils.isEmpty(Res.loginBeanModel.getSleep())?"请去睡眠管理问卷录入至少一次睡眠记录":"我的最近一次睡眠时长为："+Res.loginBeanModel.getSleep()+"个小时。");
            bmi.setText(Res.loginBeanModel.getBmi()==0?"我的BMI：":"我的BMI："+Res.loginBeanModel.getBmi());
            bmireport.setText(Res.loginBeanModel.getBmi()==0?"请去体适能问卷录入至少一次体重":Res.loginBeanModel.getWeightReport());
            waist.setText(TextUtils.isEmpty(Res.loginBeanModel.getWaist())?"我的腰围：":"我的腰围："+Res.loginBeanModel.getWaist());
            waistreport.setText(TextUtils.isEmpty(Res.loginBeanModel.getWaistReport())?"请去体适能问卷录入至少一次腰围":Res.loginBeanModel.getWaistReport());
            tizhi.setText(TextUtils.isEmpty(Res.loginBeanModel.getBodyFat())?"我的体脂：":"我的体脂："+Res.loginBeanModel.getBodyFat()+"%");
            tizhireport.setText(TextUtils.isEmpty(Res.loginBeanModel.getBodyFatReport())?"请去体适能问卷录入至少一次体脂":Res.loginBeanModel.getBodyFatReport());
            twelveminutedistance.setText(TextUtils.isEmpty(Res.loginBeanModel.getTwelveMinuteDistance())?"我的12min跑距离：":"我的12min跑距离："+Res.loginBeanModel.getTwelveMinuteDistance());
            twelveminutedistancereport.setText(TextUtils.isEmpty(Res.loginBeanModel.getTwelveMinuteDistanceReport())?"请去体适能问卷录入至少一次12min跑距离":Res.loginBeanModel.getTwelveMinuteDistanceReport());
            benchpress.setText(TextUtils.isEmpty(Res.loginBeanModel.getBenchPress())?"我的最大卧推重量：":"我的最大卧推重量："+Res.loginBeanModel.getBenchPress());
            benchpressreport.setText(TextUtils.isEmpty(Res.loginBeanModel.getBenchPressReport())?"请去体适能问卷录入至少一次我的最大卧推重量":Res.loginBeanModel.getBenchPressReport());
            driveleg.setText(TextUtils.isEmpty(Res.loginBeanModel.getDriveLeg())?"我的最大蹬腿重量：":"我的最大蹬腿重量："+Res.loginBeanModel.getDriveLeg());
            drivelegreport.setText(TextUtils.isEmpty(Res.loginBeanModel.getDriveLegReport())?"请去体适能问卷录入至少一次我的最大蹬腿重量":Res.loginBeanModel.getDriveLegReport());
            pushup.setText(TextUtils.isEmpty(Res.loginBeanModel.getPushup())?"我的最大俯卧撑次数：":"我的最大俯卧撑次数："+Res.loginBeanModel.getPushup());
            pushupreport.setText(TextUtils.isEmpty(Res.loginBeanModel.getPushupReport())?"请去体适能问卷录入至少一次我的最大俯卧撑次数":Res.loginBeanModel.getPushupReport());
            shoulderflexion.setText(TextUtils.isEmpty(Res.loginBeanModel.getShoulderFlexion())?"我的最大屈膝抬肩次数：":"我的最大屈膝抬肩次数："+Res.loginBeanModel.getShoulderFlexion());
            shoulderflexionreport.setText(TextUtils.isEmpty(Res.loginBeanModel.getShoulderFlexionReport())?"请去体适能问卷录入至少一次我的最大屈膝抬肩次数":Res.loginBeanModel.getShoulderFlexionReport());
            ymcabenchpress.setText(TextUtils.isEmpty(Res.loginBeanModel.getYMCABenchPress())?"我的最大YMCA卧推次数：":"我的最大YMCA卧推次数："+Res.loginBeanModel.getYMCABenchPress());
            ymcabenchpressreport.setText(TextUtils.isEmpty(Res.loginBeanModel.getYMCABenchPressReport())?"请去体适能问卷录入至少一次我的最大YMCA卧推次数":Res.loginBeanModel.getYMCABenchPressReport());
            sitanterior.setText(TextUtils.isEmpty(Res.loginBeanModel.getSitAnterior())?"我的坐位体前屈测量值：":"我的坐位体前屈测量值："+Res.loginBeanModel.getSitAnterior());
            sitanteriorreport.setText(TextUtils.isEmpty(Res.loginBeanModel.getSitAnteriorReport())?"请去体适能问卷录入至少一次我的坐位体前屈测量值":Res.loginBeanModel.getSitAnteriorReport());
            ymcasitanterior.setText(TextUtils.isEmpty(Res.loginBeanModel.getYMCASitAnterior())?"我的YMCA坐位体前屈测量值：":"我的YMCA坐位体前屈测量值："+Res.loginBeanModel.getYMCASitAnterior());
            ymcasitanteriorreport.setText(TextUtils.isEmpty(Res.loginBeanModel.getYMCASitAnteriorReport())?"请去体适能问卷录入至少一次我的YMCA坐位体前屈测量值":Res.loginBeanModel.getYMCASitAnteriorReport());
        }


        @Override
        public void onResume() {
            super.onResume();

            initData();
            ResetState();

        }

        private void ResetState() {
            String risk1 = Res.loginBeanModel.getRisk1Report();
            String risk2 = Res.loginBeanModel.getRisk2Report();
            String cardiovasculardisease = Res.loginBeanModel.getCardiovascularReport();

            if(risk1 !=null && !"".equals(risk1)){
                ll_report1.setVisibility(View.VISIBLE);
                risk_report1.setText(risk1);
            }else{
                ll_report1.setVisibility(View.GONE);
            }

            if(risk2 !=null && !"".equals(risk2)){
                ll_report2.setVisibility(View.VISIBLE);
                risk_report2.setText(risk2);
            }else{
                ll_report2.setVisibility(View.GONE);
            }

            if(risk1 !=null && !"".equals(risk1) && !"".equals(risk2) && risk2 !=null ){
                view_report.setVisibility(View.VISIBLE);
            }else{
                view_report.setVisibility(View.GONE);
            }
            if(cardiovasculardisease !=null && !"".equals(cardiovasculardisease)){
                ll_cardiovasculardisease_report.setVisibility(View.VISIBLE);
                cardiovasculardisease_report.setText(risk1);
            }else{
                ll_cardiovasculardisease_report.setVisibility(View.GONE);
            }
        }
    }

    public static class MyFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        List<MainListModel> mainList=new ArrayList<MainListModel>();
        public MyFragment() {


            mainList.add(new MainListModel(Res.my_list_title.get(0),R.drawable.my));
            mainList.add(new MainListModel(Res.my_list_title.get(1),R.drawable.wodeshebei));
            mainList.add(new MainListModel(Res.my_list_title.get(2),R.drawable.xitongshebei));
            mainList.add(new MainListModel(Res.my_list_title.get(3),R.drawable.yonghuguanlian));
            mainList.add(new MainListModel(Res.my_list_title.get(4),R.drawable.xitongshebei));
            mainList.add(new MainListModel(Res.my_list_title.get(5),R.drawable.yonghuguanlian));
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static MyFragment newInstance(int sectionNumber) {
            MyFragment fragment = new MyFragment();
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my, container, false);
            ListView listView = (ListView) rootView.findViewById(R.id.myfragmentlist);

            MainListAdapter adapter = new MainListAdapter(getContext(),
                    R.layout.item_mainlist, mainList);

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    switch (position){

                        case 2:
                            Intent intent =   new Intent(getActivity(),MyBaseDataActivity.class);
                            Bundle bundle = new Bundle() ;
                            intent.putExtras(bundle) ;
                            startActivity(intent);
                            break;
                        case 3:
                            Intent stepIntent =   new Intent(getActivity(),com.medicaldata.darren.medicaldata.Step.activity.MainActivity.class);
                            Bundle stepBundle = new Bundle() ;
                            stepIntent.putExtras(stepBundle) ;
                            startActivity(stepIntent);
                            break;
                        case 4:
                            Intent bongIntent =   new Intent(getActivity(),com.medicaldata.darren.medicaldata.Bong.MainActivity.class);
                            Bundle bongBundle = new Bundle() ;
                            bongIntent.putExtras(bongBundle) ;
                            startActivity(bongIntent);
                            break;
                        case 5:
                            Intent loginIntent =   new Intent(getActivity(),LoginActivity.class);
                            startActivity(loginIntent);
                            break;
                        default:
                            Toast.makeText(getContext(), "暂未开放", Toast.LENGTH_SHORT).show();
                            break;
                    }

                }
            });

            return rootView;
        }
    }

    public static class KnowledgeFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        public KnowledgeFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static KnowledgeFragment newInstance(int sectionNumber) {
            KnowledgeFragment fragment = new KnowledgeFragment();
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_knowledge, container, false);
            final WebView  webView = (WebView) rootView.findViewById(R.id.webView);


            //启用支持javascript
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);

            //WebView加载web资源
            webView.loadUrl("http://wx22826aa42e29b248.mp.weixinhost.com/addon/material?a=show&id=871822485216178176&sid=871822485216198656&from=groupmessage");
            //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // TODO Auto-generated method stub
                    //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
                    return true;
                }
            });

            Button bt_return = (Button)rootView.findViewById(R.id.bt_return);

            bt_return.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (webView.canGoBack()){
                        webView.goBack();
                    }
                }
            });
            return rootView;
        }
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0:
                    return QuestionFragment.newInstance(position + 1);
                case 1:
                    return DataFragment.newInstance(position + 1);
                case 2:
                    return KnowledgeFragment.newInstance(position + 1);
                case 3:
                    return MyFragment.newInstance(position + 1);
            }
            return null;

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

    }
}
