package com.qjy.easynews.biz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.qjy.easynews.R;
import com.qjy.easynews.app.AppCtx;
import com.qjy.easynews.model.Video;
import com.qjy.easynews.utils.BitmapCache;

import java.util.List;

/**
 * Created by qjy on 15-4-24.
 */
public class VideoAdapter extends BaseAdapter{

    private List<Video> list;

    private Context context;

    private LayoutInflater inflater;
    private ImageLoader imageLoader;

    public VideoAdapter(List<Video> list, Context context) {
        this.list = list;
        this.context = context;

        inflater = LayoutInflater.from(context);
        imageLoader = new ImageLoader(AppCtx.getInstance().getRequestQueue(), BitmapCache.getInstance());
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
            convertView = inflater.inflate(R.layout.item_video,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.networkImageView = (NetworkImageView) convertView.findViewById(R.id.networkImageView_item_video_pic);
            viewHolder.textView_playNum = (TextView) convertView.findViewById(R.id.textView_item_video_playNum);
            viewHolder.textView_title = (TextView) convertView.findViewById(R.id.textView_item_video_title);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Video video = list.get(position);

        viewHolder.textView_title.setText(video.getTitle());
        viewHolder.textView_playNum.setText(video.getPlay_num()+"播放");

        viewHolder.networkImageView.setDefaultImageResId(R.drawable.defaultimg);
        viewHolder.networkImageView.setImageUrl(video.getPic(), imageLoader);

        return convertView;
    }

    class ViewHolder{
        NetworkImageView networkImageView;
        TextView textView_title;
        TextView textView_playNum;
    }
}
