package com.qjy.easynews.biz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.qjy.easynews.R;
import com.qjy.easynews.app.AppCtx;
import com.qjy.easynews.biz.Adapter.CyclePagerAdapter;
import com.qjy.easynews.biz.Adapter.NewsItemsAdapter;
import com.qjy.easynews.biz.activity.NewsDetailActivity;
import com.qjy.easynews.biz.widget.PullToRefreshBase;
import com.qjy.easynews.biz.widget.PullToRefreshListView;
import com.qjy.easynews.model.News;
import com.qjy.easynews.model.Title;
import com.qjy.easynews.utils.BitmapCache;
import com.qjy.easynews.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qjy on 15-4-21.
 */
public class ContentFragment extends Fragment {

    private PullToRefreshListView mRefreshListView;
    private ListView mListView;
    private int mCurrentPage = 1;
    private String mCateId;
    private LinearLayout mLinearEmpty;
    private NewsItemsAdapter adapter;
    private List<News> totalList;
    private boolean isLoading;
    private ViewPager mTopViewPager;
    private RequestQueue mRequestQueue;
    private TextView mTopTextView;
    private View topView;
    private LinearLayout mDotsLinearLayout;
    private List<News> mTopList;
    private ImageView[] dots;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (dots != null) {
                        int currentItem = mTopViewPager.getCurrentItem();
                        int toItem = currentItem + 1;
                        mTopViewPager.setCurrentItem(toItem, true);
                    }
                    // 每4秒钟发送一个message，用于切换viewPager中的图片
                    this.sendEmptyMessageDelayed(1, 4000);
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        mCateId = bundle.getString("cate_id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        initView(view);
        initData();
        addListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.sendEmptyMessageDelayed(1, 4000);
    }

    @Override
    public void onStop() {
        super.onStop();
        mHandler.removeMessages(1);
    }

    private void initView(View view) {
        mRefreshListView = (PullToRefreshListView) view.findViewById(R.id.content_list);
        mLinearEmpty = (LinearLayout) view.findViewById(R.id.linearLayout_contentfragment_emptyinfo);
        mRefreshListView.setEmptyView(mLinearEmpty);

        //用来做头listView的Head
        topView = getActivity().getLayoutInflater().inflate(R.layout.page_newstop,null);
        mTopViewPager = (ViewPager) topView.findViewById(R.id.viewPager_newstop);
        mTopTextView = (TextView) topView.findViewById(R.id.textView_newstop);
        mDotsLinearLayout = (LinearLayout) topView.findViewById(R.id.linearLayout_newstop_dots);
    }

    private void initData() {

        mRequestQueue = AppCtx.getInstance().getRequestQueue();
        totalList = new ArrayList<>();
        adapter = new NewsItemsAdapter(totalList, getActivity());
        mListView = mRefreshListView.getRefreshableView();
        mListView.addHeaderView(topView);
        mListView.setAdapter(adapter);

        loadData();


    }

    public void addListener() {
        mRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentPage = 1;
                loadData();
            }
        });

        mRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                mCurrentPage++;
                if(!isLoading) {
                    Toast.makeText(getActivity(), "正在加载", Toast.LENGTH_SHORT).show();
                    isLoading = true;
                    loadData();
                }
            }
        });


        //头上的ViewPager改变时字幕的变化以及点的变化
        mTopViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                pageSelected(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //给ListView的Item加监听器

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                position = position-mListView.getHeaderViewsCount();

                //获得News 的 Id
                String news_id = totalList.get(position).getId()+"";

                //Activity 跳转
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);

                //诶Activity 传值
                Bundle bundle = new Bundle();
                bundle.putString("news_id",news_id);
                intent.putExtras(bundle);

                //启动Activity
                startActivity(intent);
            }
        });


    }

    private void loadData() {
        HttpUtils.getNews(getActivity(), mCateId, mCurrentPage, new HttpUtils.OnSuccessListener() {
            @Override
            public void loadUI(List<Title> list) {
            }

            @Override
            public void loadNewsUI(List<News> topList, List<News> list) {
                if(mCurrentPage == 1 && topList != null){
                    mTopList = topList;
                    initTopData(topList);

                }
                if (list != null) {
                    notifyAdapter(list);
                }
            }
        });
    }

    /**
     * 初始化listView的头
     * @param topList
     */
    private void initTopData(List<News> topList){
        ArrayList<View> imgList = new ArrayList<View>();

        //初始化头上的点
        dots = new ImageView[topList.size()];
        mDotsLinearLayout.removeAllViews();

        for (int i = 0; i < topList.size(); i++) {
            News news = topList.get(i);
            NetworkImageView imageView = new NetworkImageView(getActivity());
            ImageLoader imageLoader = new ImageLoader(mRequestQueue, BitmapCache.getInstance());
            imageView.setImageUrl(news.getCover_pic(), imageLoader);
            imgList.add(imageView);

            dots[i] = new ImageView(getActivity());
            dots[i].setImageResource(R.drawable.dots);
            mDotsLinearLayout.addView(dots[i]);
        }
        pageSelected(0);
        CyclePagerAdapter pagerAdapter = new CyclePagerAdapter(imgList);
        mTopViewPager.setAdapter(pagerAdapter);

    }

    private void pageSelected(int position){
        if(mTopList == null){
            return;
        }
        News news = mTopList.get(position%dots.length);
        mTopTextView.setText(news.getTitle());

        if(dots != null){
            for (int i = 0; i < dots.length; i++) {
                dots[i].setEnabled(true);
            }
            dots[position%dots.length].setEnabled(false);
        }
    }

    private void notifyAdapter(List<News> list) {

        if (mCurrentPage == 1) {
            totalList.clear();
            mRefreshListView.onRefreshComplete();
        }
        totalList.addAll(list);
        adapter.notifyDataSetChanged();
        isLoading = false;
    }


}