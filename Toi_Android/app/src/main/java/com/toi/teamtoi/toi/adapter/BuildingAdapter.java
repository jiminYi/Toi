package com.toi.teamtoi.toi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.toi.teamtoi.toi.R;
import com.toi.teamtoi.toi.data.Building;

import java.util.List;

public class BuildingAdapter  extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Building> buildingList;
    private int layout;

    public BuildingAdapter(Context context, int layout, List<Building> buildingList) {
        this.layout = layout;
        this.buildingList = buildingList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return buildingList.size();
    }

    @Override
    public Object getItem(int position) {
        return buildingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BuildingViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);

            viewHolder = new BuildingViewHolder();
            viewHolder.buildingName = (TextView) convertView.findViewById(R.id.tv_building_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BuildingViewHolder) convertView.getTag();
        }

        viewHolder.buildingName.setText(buildingList.get(position).getName());

        return convertView;
    }

    public class BuildingViewHolder {
        public TextView buildingName;
    }
}
