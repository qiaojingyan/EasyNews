package com.qjy.easynews.biz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.qjy.easynews.R;
import com.qjy.easynews.app.AppCtx;
import com.qjy.easynews.model.News;
import com.qjy.easynews.utils.BitmapCache;

import java.util.List;

/**
 * Created by qjy on 15-4-21.
 */
public class NewsItemsAdapter extends BaseAdapter{

    private List<News> list;

    private Context context;

    private RequestQueue mRequestQueue;

    private LayoutInflater inflater;

    public NewsItemsAdapter(List<News> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        mRequestQueue = AppCtx.getInstance().getRequestQueue();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_news,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (NetworkImageView) convertView.findViewById(R.id.networkImg_item);
            viewHolder.titleText = (TextView) convertView.findViewById(R.id.textView_item_title);
            viewHolder.descriptText = (TextView) convertView.findViewById(R.id.textView_item_des);
            viewHolder.commentTotalText = (TextView) convertView.findViewById(R.id.textView_item_comment);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        News news = list.get(position);
        viewHolder.titleText.setText(news.getTitle());
        viewHolder.descriptText.setText(news.getDescript());
        viewHolder.commentTotalText.setText(news.getComment_total()+" 评论");

        String imgURL = news.getCover_pic();

        ImageLoader imageLoader = new ImageLoader(mRequestQueue, BitmapCache.getInstance());

        viewHolder.imageView.setErrorImageResId(R.drawable.defaultimg);
        viewHolder.imageView.setDefaultImageResId(R.drawable.defaultimg);
        viewHolder.imageView.setImageUrl(imgURL,imageLoader);


        return convertView;
    }

    class ViewHolder{
        NetworkImageView imageView;
        TextView titleText;
        TextView descriptText;
        TextView commentTotalText;
    }
}
