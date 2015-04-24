package com.qjy.easynews.biz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.qjy.easynews.R;
import com.qjy.easynews.app.AppCtx;
import com.qjy.easynews.biz.Adapter.MyPagerAdapter;
import com.qjy.easynews.model.News;
import com.qjy.easynews.utils.BitmapCache;
import com.qjy.easynews.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qjy on 15-4-23.
 */
public class PicDetailActivity extends Activity {

    private TextView textView_content;
    private ViewPager viewPager_pic;
    private TextView textView_title;
    private TextView textView_page;
    private LinearLayout linearLayout_comments;
    private TextView textView_commentsNum;
    private String mPicId;
    private News picNews;
    private List<View> totalList;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private MyPagerAdapter adapter;
    private int size;
    private boolean isShowing;
    private LinearLayout linearLayout_top;
    private LinearLayout linearLayout_pl;
    private LinearLayout linearLayout_content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picdetail);

        initView();

        initData();

        addListener();
    }

    public void clickButton(View view) {
        switch (view.getId()) {
            case R.id.btn_picdetail_back:
                finish();
                break;
            case R.id.btn_picdetail_favorite:
                break;
            case R.id.btn_picdetail_share:
                break;
        }
    }

    public void initView() {
        textView_content = (TextView) findViewById(R.id.textView_picdetail_content);

        viewPager_pic = (ViewPager) findViewById(R.id.viewPager_picdetail_pic);

        textView_title = (TextView) findViewById(R.id.textView_picdetail_title);

        textView_page = (TextView) findViewById(R.id.textView_picdetail_page);

        linearLayout_comments = (LinearLayout) findViewById(R.id.linearLayout_picdetail_comments);

        textView_commentsNum = (TextView) findViewById(R.id.textView_picdetail_commentsNum);

        linearLayout_top = (LinearLayout) findViewById(R.id.linearLayout_picdetail_top);

        linearLayout_content = (LinearLayout) findViewById(R.id.linearLayout_picdetail_content);

        linearLayout_pl = (LinearLayout) findViewById(R.id.linearLayout_picdetail_pl);

        totalList = new ArrayList<>();
        adapter = new MyPagerAdapter(totalList);
        viewPager_pic.setAdapter(adapter);


    }

    public void initData(){
        Bundle bundle = getIntent().getExtras();

        mPicId = bundle.getString("item_id");

        requestQueue = AppCtx.getInstance().getRequestQueue();
        imageLoader = new ImageLoader(requestQueue, BitmapCache.getInstance());

        loadData();
    }

    public void loadData(){
        HttpUtils.getNewsDetail(this, mPicId, new HttpUtils.OnObjSuccessListener() {
            @Override
            public void loadUI(Object obj) {

                if(obj != null){
                    if(obj instanceof News){
                        picNews = (News) obj;
                        loadUIData();
                    }
                }

            }

            @Override
            public void errorUI() {

            }
        });
    }

    public void addListener(){
        viewPager_pic.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                textView_page.setText((i+1)+"/"+size);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        viewPager_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("PicDetailActivity","点击了");

                if(isShowing){
                    linearLayout_pl.setVisibility(View.GONE);
                    linearLayout_top.setVisibility(View.GONE);
                    linearLayout_content.setVisibility(View.GONE);


                }else{
                    linearLayout_pl.setVisibility(View.VISIBLE);
                    linearLayout_top.setVisibility(View.VISIBLE);
                    linearLayout_content.setVisibility(View.VISIBLE);
                }
                isShowing = !isShowing;
            }
        });

        linearLayout_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PicDetailActivity.this,CommentsActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("item_id",mPicId);

                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(intent);
            }
        });
    }

    private void loadUIData(){
        textView_commentsNum.setText("评论");
        textView_content.setText(picNews.getContent());
        List<String> pic_list = picNews.getPic_list();
        size = pic_list.size();
        textView_page.setText("1/"+size +"");
        textView_title.setText(picNews.getTitle());

        List<View> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            String picString = pic_list.get(i);
            NetworkImageView imageView = new NetworkImageView(this);

            imageView.setDefaultImageResId(R.drawable.defaultimg);
            imageView.setImageUrl(picString, imageLoader);


            list.add(imageView);
        }

        notifyAdapter(list);


    }

    private void notifyAdapter(List<View> list){
        totalList.addAll(list);
        adapter.notifyDataSetChanged();
    }


}