package com.qjy.easynews.biz.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.qjy.easynews.R;
import com.qjy.easynews.app.AppCtx;
import com.qjy.easynews.model.PicContent;
import com.qjy.easynews.utils.BitmapCache;

import java.util.List;

/**
 * Created by qjy on 15-4-22.
 */
public class PicContentAdapter extends BaseAdapter{

    private List<PicContent> list;

    private Context context;

    private LayoutInflater inflater;
    private ImageLoader imageLoader;
    private RequestQueue mRequestQueue;
    private int widthPixels;
    private Bitmap bitmap;

    public PicContentAdapter(List<PicContent> list, Context context) {
        this.list = list;
        this.context = context;

        inflater = LayoutInflater.from(context);
        mRequestQueue = AppCtx.getInstance().getRequestQueue();
        imageLoader = new ImageLoader(mRequestQueue, BitmapCache.getInstance());
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.defaultimg);
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
            convertView = inflater.inflate(R.layout.item_pic_content,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imageViews = new NetworkImageView[4];
            viewHolder.imageViews[0] = (NetworkImageView) convertView.findViewById(R.id.imageView1);
            viewHolder.imageViews[1] = (NetworkImageView) convertView.findViewById(R.id.imageView2);
            viewHolder.imageViews[2] = (NetworkImageView) convertView.findViewById(R.id.imageView3);
            viewHolder.imageViews[3] = (NetworkImageView) convertView.findViewById(R.id.imageView4);
            viewHolder.textView_descript = (TextView) convertView.findViewById(R.id.textView_item_piccontent_descript);
            viewHolder.textView_title = (TextView) convertView.findViewById(R.id.textView_item_piccontent_title);
            viewHolder.textView_count = (TextView) convertView.findViewById(R.id.textView_item_piccontent_pictotal);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PicContent picContent = list.get(position);
        viewHolder.textView_descript.setText(picContent.getDescript());
        viewHolder.textView_count.setText(picContent.getPic_total()+"图");
        viewHolder.textView_title.setText(picContent.getTitle());

        int n = 0;
        if(picContent.getPic_list() != null){
            n = picContent.getPic_list().size();
        }

        if(n>4){
            n=4;
        }
        setImageSize(viewHolder,n);
        for (int i = 0; i < n; i++) {
            viewHolder.imageViews[i].setVisibility(View.VISIBLE);

            viewHolder.imageViews[i].setDefaultImageResId(R.drawable.defaultimg);
            viewHolder.imageViews[i].setImageUrl(picContent.getPic_list().get(i), imageLoader);
        }
        for (int i = n; i < 4; i++) {
            viewHolder.imageViews[i].setVisibility(View.GONE);
        }

        return convertView;
    }

    private void setImageSize(ViewHolder viewHolder,int n){
        switch (n){
            case 1:
                setWholeWidth(viewHolder.imageViews[0]);
                break;
            case 2:
                setHalfWidth(viewHolder.imageViews[0]);
                setHalfWidth(viewHolder.imageViews[1]);
                break;
            case 3:
                setHalfWidth(viewHolder.imageViews[0]);
                setHalfWidth(viewHolder.imageViews[1]);
                setWholeWidth(viewHolder.imageViews[2]);
                break;
            case 4:
                setHalfWidth(viewHolder.imageViews[0]);
                setHalfWidth(viewHolder.imageViews[1]);
                setHalfWidth(viewHolder.imageViews[2]);
                setHalfWidth(viewHolder.imageViews[3]);
        }
    }

    private void setHalfWidth(NetworkImageView imageView){
        setImageSize(imageView,getScreenWidth()/2);
    }
    private void setWholeWidth(NetworkImageView imageView){
        setImageSize(imageView,getScreenWidth());
    }

    private void setImageSize(NetworkImageView imageView,int width){

        int bitmapHeight= bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();
        int height = (int)(((float)width/(float)bitmapWidth)*(float)bitmapHeight);


        imageView.setLayoutParams(new LinearLayout.LayoutParams(width,height));
    }

    private int getScreenWidth(){
        if (widthPixels == 0) {
            widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        }
        return widthPixels;
    }

    class ViewHolder{
        NetworkImageView[] imageViews;
        TextView textView_title;
        TextView textView_descript;
        TextView textView_count;
    }
}
