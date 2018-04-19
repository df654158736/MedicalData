package com.medicaldata.darren.medicaldata.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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
import com.medicaldata.darren.medicaldata.Base.BaseActivity;
import com.medicaldata.darren.medicaldata.Common.AsyncHttpClientUtils;
import com.medicaldata.darren.medicaldata.Common.MainListAdapter;
import com.medicaldata.darren.medicaldata.Common.Res;
import com.medicaldata.darren.medicaldata.Common.mUtil;
import com.medicaldata.darren.medicaldata.Model.BaseListModel;
import com.medicaldata.darren.medicaldata.Model.ChartDataBean;
import com.medicaldata.darren.medicaldata.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabs;


    private List<Pair<String, Fragment>> items;


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
            for (Activity activity : Res.activityLists) {
                activity.finish();
            }

            Res.activityLists.clear();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Res.activityLists.add(MainActivity.this);

        items = new ArrayList<>();
        items.add(new Pair<String, Fragment>(Res.tab_titles.get(0), new QuestionFragment()));
        items.add(new Pair<String, Fragment>(Res.tab_titles.get(1), new DataFragment()));
        items.add(new Pair<String, Fragment>(Res.tab_titles.get(2), new KnowledgeFragment()));
        items.add(new Pair<String, Fragment>(Res.tab_titles.get(3), new MyFragment()));

        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(items.size());
        tabs.setupWithViewPager(viewPager);

        setupTabIcons();

    }

    private void setupTabIcons() {
        tabs.getTabAt(0).setCustomView(getTabView(0));
        tabs.getTabAt(1).setCustomView(getTabView(1));
        tabs.getTabAt(2).setCustomView(getTabView(2));
        tabs.getTabAt(3).setCustomView(getTabView(3));
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);

        TextView txtTitle= view.findViewById(R.id.txt_title);
        txtTitle.setText(Res.tab_titles.get(position));
        ImageView imgTitle= view.findViewById(R.id.img_title);
        imgTitle.setImageResource(Res.q_tabIcons[position]);

        return view;
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
            final WebView webView = (WebView) rootView.findViewById(R.id.webView);


            //启用支持javascript
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);

            //WebView加载web资源
            webView.loadUrl("http://wx22826aa42e29b248.mp.weixinhost.com/addon/material?a=show&id=871822485216178176&sid=871822485216198656&from=groupmessage");
            //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // TODO Auto-generated method stub
                    //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
                    return true;
                }
            });

            Button bt_return = (Button) rootView.findViewById(R.id.bt_return);

            bt_return.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (webView.canGoBack()) {
                        webView.goBack();
                    }
                }
            });
            return rootView;
        }
    }


    public class MainAdapter extends FragmentPagerAdapter {

        public MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return items.get(position).second;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return items.get(position).first;
        }

    }
}
