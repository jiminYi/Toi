package com.toi.teamtoi.toi.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.toi.teamtoi.toi.FloorRestRoomFragment;
import com.toi.teamtoi.toi.R;
import com.toi.teamtoi.toi.data.Building;
import com.toi.teamtoi.toi.data.Campus;
import com.toi.teamtoi.toi.data.RestRoom;

import java.util.List;
import java.util.concurrent.TimeoutException;

public class RestRoomAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<RestRoom> mItemList;
    private int mLayout;
    private FragmentActivity fragmentActivity;

    public RestRoomAdapter(Context context, int layout, List<RestRoom> itemList, FragmentActivity fragmentActivity) {
        this.mContext = context;
        this.mLayout = layout;
        this.mItemList = itemList;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.fragmentActivity = fragmentActivity;
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
        CampusViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(mLayout, parent, false);

            viewHolder = new CampusViewHolder();
            viewHolder.position = (TextView) convertView.findViewById(R.id.tv_position);
            viewHolder.emptySapce = (TextView) convertView.findViewById(R.id.tv_empty_space);
            viewHolder.waitingTime = (TextView) convertView.findViewById(R.id.tv_waiting_time);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CampusViewHolder) convertView.getTag();
        }

        viewHolder.position.setText(mItemList.get(position).getPosition());
        viewHolder.emptySapce.setText(mItemList.get(position).getNumOfEmptySpace() + "/" + mItemList.get(position).getNumOfSpace());
        viewHolder.waitingTime.setText("대기시간: " + mItemList.get(position).getWaitingTime());

        return convertView;
    }

    public class CampusViewHolder {
        public TextView position;
        public TextView emptySapce;
        public TextView waitingTime;
    }
}
