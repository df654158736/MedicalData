package com.medicaldata.darren.medicaldata.Base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.medicaldata.darren.medicaldata.Model.BaseListModel;
import com.medicaldata.darren.medicaldata.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Darren on 2018/3/29.
 */

public abstract class BaseListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    protected Context context;
    @BindView(R.id.text_title)
    TextView textTitle;
    Unbinder unbinder;
    private MainAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_refresh, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onRefresh() {
        setRefreshing(false);
    }

    @Override
    public void onLoadMoreRequested() {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.loadComplete();
            }
        }, 500);
    }

    @Override
    protected void initData() {
        List<BaseListModel> items = new ArrayList<>();
        fillData(items);

        textTitle.setText(setTitleName());
        adapter = new MainAdapter(items);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.isFirstOnly(false);
        adapter.setOnLoadMoreListener(this);

        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);
        refreshLayout.setOnRefreshListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(adapter);
    }

    public abstract void fillData(List<BaseListModel> items);

    public abstract void onItemClick(int position);

    public abstract String setTitleName();

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

    private class MainAdapter extends BaseQuickAdapter<BaseListModel> {

        MainAdapter(List<BaseListModel> data) {
            super(R.layout.item_baselist, data);
        }

        @Override
        protected void convert(final BaseViewHolder baseViewHolder, BaseListModel itemModel) {

            baseViewHolder.setImageResource(R.id.base_list_image, itemModel.getImageId());
            baseViewHolder.setText(R.id.base_list_name, itemModel.getName());
            baseViewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(baseViewHolder.getAdapterPosition());
                }
            });
        }
    }

    public void setRefreshing(final boolean refreshing) {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(refreshing);
            }
        });
    }
}
