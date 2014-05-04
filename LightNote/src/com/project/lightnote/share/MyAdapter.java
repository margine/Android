package com.project.lightnote.share;

import java.util.List;  
import java.util.Map;  
  




import com.project.lightnote.R;

import android.content.Context;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.BaseAdapter;  
import android.widget.ImageView;  
import android.widget.TextView;  
  
public class MyAdapter extends BaseAdapter {  
    private Context context;  
    private List<Map<String, Object>> list;  
  
    public MyAdapter(Context context, List<Map<String, Object>> list) {  
        this.context = context;  
        this.list = list;  
    }  
  
    @Override  
    public int getCount() {  
        return list.size();  
    }  
  
    @Override  
    public Object getItem(int arg0) {  
        return list.get(arg0);  
    }  
  
    @Override  
    public long getItemId(int arg0) {  
        return arg0;  
    }  
  
    @Override  
    public View getView(int position, View arg1, ViewGroup arg2) {  
        View view = LayoutInflater.from(context).inflate(R.layout.listview_row,  
                null);  
        ImageView imageView = (ImageView) view  
                .findViewById(R.id.listview_row_iv);  
        TextView textView = (TextView) view.findViewById(R.id.listview_row_tv);  
        imageView.setBackgroundResource((Integer) list.get(position).get(  
                "image"));  
        textView.setText((String) list.get(position).get("name"));  
        return view;  
    }  
  
}  