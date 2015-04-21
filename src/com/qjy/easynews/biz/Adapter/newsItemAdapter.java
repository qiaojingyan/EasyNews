package com.qjy.easynews.biz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.qjy.easynews.model.News;

import java.util.List;

/**
 * Created by qjy on 15-4-21.
 */
public class newsItemAdapter extends BaseAdapter{

    private List<News> list;

    private Context context;

    private LayoutInflater inflater;

    public newsItemAdapter(List<News> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
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


        if(convertView == null){

        }


        return convertView;
    }

    class ViewHolder{
        NetworkImageView imageView;
        TextView titleText;
    }
}
