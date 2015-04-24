package com.qjy.easynews.biz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.qjy.easynews.R;
import com.qjy.easynews.model.Comment;
import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by qjy on 15-4-23.
 */
public class CommentAdapter extends BaseAdapter{

    private Context context;
    private List<Comment> list;
    private LayoutInflater inflater;

    public CommentAdapter(Context context, List<Comment> list) {
        this.context = context;
        this.list = list;
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

        ViewHolder viewHolder = null;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_comments,parent,false);
            viewHolder = new ViewHolder();

            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView_item_comments_header);
            viewHolder.textView_content = (TextView) convertView.findViewById(R.id.textView_item_comments_content);
            viewHolder.textView_user = (TextView) convertView.findViewById(R.id.textView_item_comments_user);
            viewHolder.textView_createTime = (TextView) convertView.findViewById(R.id.textView_item_comments_createtime);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Comment comment = list.get(position);

        viewHolder.textView_createTime.setText(comment.getCreate_time());
        viewHolder.textView_content.setText(comment.getContent());
        viewHolder.textView_user.setText(comment.getUser());


        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        TextView textView_user;
        TextView textView_content;
        TextView textView_createTime;

    }
}
