package com.toi.teamtoi.toi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.toi.teamtoi.toi.R;
import com.toi.teamtoi.toi.data.RestRoom;

import java.util.ArrayList;
import java.util.List;

public class RestRoomAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<RestRoom> itemList;
    private int layout;

    public RestRoomAdapter(Context context, int layout, List<RestRoom> itemList) {
        this.layout = layout;
        this.itemList = itemList;
        if (this.itemList == null) {
            this.itemList = new ArrayList<RestRoom>();
        }
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CampusViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);

            viewHolder = new CampusViewHolder();
            viewHolder.position = (TextView) convertView.findViewById(R.id.tv_position);
            viewHolder.emptySpace = (TextView) convertView.findViewById(R.id.tv_empty_space);
            viewHolder.waitingTime = (TextView) convertView.findViewById(R.id.tv_waiting_time);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CampusViewHolder) convertView.getTag();
        }

        viewHolder.position.setText(itemList.get(position).getPosition());
        viewHolder.emptySpace.setText((itemList.get(position).getNumOfSpace() - itemList.get(position).getNumOfEmptySpace()) + "/" + itemList.get(position).getNumOfSpace());
        viewHolder.waitingTime.setText("대기시간: " + itemList.get(position).getWaitingTime());

        return convertView;
    }

    public class CampusViewHolder {
        public TextView position;
        public TextView emptySpace;
        public TextView waitingTime;
    }
}
