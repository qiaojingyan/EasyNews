package com.qjy.easynews.biz.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.qjy.easynews.R;
import com.qjy.easynews.app.AppCtx;
import com.qjy.easynews.biz.Adapter.PicContentAdapter;
import com.qjy.easynews.biz.activity.PicDetailActivity;
import com.qjy.easynews.biz.widget.PullToRefreshBase;
import com.qjy.easynews.biz.widget.PullToRefreshListView;
import com.qjy.easynews.model.PicContent;
import com.qjy.easynews.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qjy on 15-4-22.
 */
public class PicContentFragment extends Fragment {

    private String mCateId;
    private int mCurrentPage = 1;
    private List<PicContent> mTotalList;
    private PicContentAdapter adapter;
    private PullToRefreshListView mContentListView;
    private ListView mListView;
    private boolean isLoading;
    private FragmentActivity context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mCateId = bundle.getString("cate_id");
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content,container,false);

        //初始化View
        initView(view);

        //初始化数据
        initData();

        //添加监听器
        addListener();

        return view;
    }

    public void initView(View view){

        //获得 PullToRefreshListView
        mContentListView = (PullToRefreshListView) view.findViewById(R.id.content_list);
        mListView = mContentListView.getRefreshableView();
        LinearLayout layoutEmpty = (LinearLayout) view.findViewById(R.id.linearLayout_contentfragment_emptyinfo);
        mContentListView.setEmptyView(layoutEmpty);

        //设置ListView的Adapter
        mTotalList = new ArrayList<>();

        adapter = new PicContentAdapter(mTotalList, context);
        mListView.setAdapter(adapter);

    }

    public void addListener(){
        mContentListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentPage = 1;
                loadData();
            }
        });

        mContentListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                if(!isLoading){
                    Toast.makeText(AppCtx.getInstance(),"正在加载",Toast.LENGTH_LONG).show();
                    mCurrentPage++;
                    isLoading = true;
                    loadData();
                }
            }
        });

        //添加 item 点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, PicDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("item_id",mTotalList.get(position).getId()+"");
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

    }

    private void initData(){
        loadData();
    }

    private void loadData(){
        HttpUtils.getPicContent(context, mCateId, mCurrentPage, new HttpUtils.OnPicSuccessListener() {
            @Override
            public void loadPicUI(List<PicContent> list) {
                if(list != null){
                    notifyAdapter(list);
                }
            }
        });
    }


    private void notifyAdapter(List<PicContent> list){
        if(mCurrentPage == 1){
            mTotalList.clear();
            mContentListView.onRefreshComplete();
        }
        mTotalList.addAll(list);
        adapter.notifyDataSetChanged();
        isLoading = false;
    }
}