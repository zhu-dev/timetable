package com.breeziness.timetable.courcetask.popwin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.breeziness.timetable.R;

import java.util.List;

public class PopListAdapter extends BaseAdapter {

    private List<DropBean> drops;
    private Context context;

    public PopListAdapter(List<DropBean> drops, Context context) {
        this.drops = drops;
        this.context = context;
    }

    @Override
    public int getCount() {
        return drops.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
           holder = new ViewHolder();
           convertView = LayoutInflater.from(context).inflate(R.layout.drop_item,parent,false);
           holder.tv_weekday = convertView.findViewById(R.id.drop_item_tv);//绑定
           holder.tick = convertView.findViewById(R.id.drop_item_tick);
           convertView.setTag(holder);//Sets the tag associated with this view  设置与此视图关联的标记
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_weekday.setText(drops.get(position).getWeekday());//显示weekday
        //如果选中，则显示勾号
        if (drops.get(position).isCheck()){
            holder.tick.setVisibility(View.VISIBLE);
        }else {
            holder.tick.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder{
        TextView tv_weekday;
        ImageView tick;
    }
}
