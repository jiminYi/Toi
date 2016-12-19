package com.toi.teamtoi.toi.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
            viewHolder.floor = (TextView) convertView.findViewById(R.id.tv_floor);
            viewHolder.position = (TextView) convertView.findViewById(R.id.tv_position);
            viewHolder.emptySpace = (TextView) convertView.findViewById(R.id.tv_empty_space);
            viewHolder.waitingTime = (TextView) convertView.findViewById(R.id.tv_waiting_time);
            viewHolder.imgPowder = (ImageView) convertView.findViewById(R.id.img_powder);
            viewHolder.imgVending = (ImageView) convertView.findViewById(R.id.img_vending);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CampusViewHolder) convertView.getTag();
        }

        String floorStr = "";
        if(itemList.get(position).getFloor() > 0) {
            floorStr += itemList.get(position).getFloor() + "층";
        } else {
            floorStr += "B" + -itemList.get(position).getFloor() + "층";
        }
        viewHolder.floor.setText(floorStr);
        viewHolder.position.setText(itemList.get(position).getPosition());
        viewHolder.emptySpace.setText((itemList.get(position).getNumOfSpace() - itemList.get(position).getNumOfEmptySpace()) + "/" + itemList.get(position).getNumOfSpace());
        viewHolder.waitingTime.setText("대기시간: " + itemList.get(position).getWaitingTime() + "분");

        if(itemList.get(position).isPowderRoom()) {
            viewHolder.imgPowder.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgPowder.setVisibility(View.INVISIBLE);
        }

        if(itemList.get(position).isHasVendingMachine()) {
            viewHolder.imgVending.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgVending.setVisibility(View.INVISIBLE);
        }


        return convertView;
    }

    public class CampusViewHolder {
        public TextView floor;
        public TextView position;
        public TextView emptySpace;
        public TextView waitingTime;
        public ImageView imgPowder;
        public ImageView imgVending;
    }
}
