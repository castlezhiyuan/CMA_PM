package com.example.admin.cma;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王国新 on 2018/5/20.
 */

public class StaffTestingAdapter extends ArrayAdapter<StaffTesting> {
    private int resourceId;
    private MyFilter mFilter;
    private List<StaffTesting> list;   //用于展示的数据
    private List<StaffTesting> rawList;//原始数据

    public StaffTestingAdapter(Context context, int textViewResourceId, List<StaffTesting> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
        list = objects;
        rawList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StaffTesting staffTesting = getItem(position);
        View view;
        ViewHolder viewHolder;

        if(null == convertView){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.name =(TextView) view.findViewById(R.id.item_name);
            viewHolder.person=(TextView) view.findViewById(R.id.item_person);
            viewHolder.time=(TextView)view.findViewById(R.id.item_time);
            view.setTag(viewHolder);  //ViewHolder存在View中
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.name.setText(staffTesting.getName());
        viewHolder.person.setText(staffTesting.getPerson());
        viewHolder.time.setText(staffTesting.getTime());
        return view;
    }

    class ViewHolder{
        TextView name;
        TextView person;
        TextView time;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public StaffTesting getItem(int position) {
        //list用于展示
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        if (null == mFilter) {
            mFilter = new MyFilter();
        }
        return mFilter;
    }

    // 自定义Filter类
    class MyFilter extends Filter {
        @Override
        // 该方法在子线程中执行
        // 自定义过滤规则
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            //String filterString = constraint.toString().trim().toLowerCase();

            List<StaffTesting> filterList;
            // 如果搜索框内容为空，就恢复原始数据
            if (TextUtils.isEmpty(constraint)) {
                filterList = rawList;
            } else {
                // 过滤出新数据
                filterList = new ArrayList<>();
                for (StaffTesting st : list) {
                    if(st.getName().contains(constraint)||
                            st.getPerson().contains(constraint)||
                            st.getTime().contains(constraint))
                        filterList.add(st);
                }
            }
            results.values = filterList;
            results.count = filterList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            list = (List<StaffTesting>) results.values;

            if (results.count > 0) {
                notifyDataSetChanged();  // 通知数据发生了改变
            } else {
                notifyDataSetInvalidated(); // 通知数据失效
            }
        }
    }
}
