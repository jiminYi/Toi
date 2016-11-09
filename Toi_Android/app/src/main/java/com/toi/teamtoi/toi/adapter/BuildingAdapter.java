package com.toi.teamtoi.toi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.toi.teamtoi.toi.R;
import com.toi.teamtoi.toi.data.Building;

import java.util.List;

public class BuildingAdapter  extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Building> mItemList;
    private int mLayout;

    public BuildingAdapter(Context context, int layout, List<Building> itemList) {
        this.mContext = context;
        this.mLayout = layout;
        this.mItemList = itemList;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BuildingViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(mLayout, parent, false);

            viewHolder = new BuildingViewHolder();
            viewHolder.buildingName = (TextView) convertView.findViewById(R.id.tv_building_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BuildingViewHolder) convertView.getTag();
        }

        viewHolder.buildingName.setText(mItemList.get(position).getName());

        return convertView;
    }

    public class BuildingViewHolder {
        public TextView buildingName;
    }
}
