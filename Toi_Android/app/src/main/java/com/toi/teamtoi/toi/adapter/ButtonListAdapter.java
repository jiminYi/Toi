package com.toi.teamtoi.toi.adapter;

import android.content.Context;
import android.support.v4.app.*;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.toi.teamtoi.toi.R;
import com.toi.teamtoi.toi.RestRoomDetailFragment;
import com.toi.teamtoi.toi.data.FloorRestRoom;
import com.toi.teamtoi.toi.data.RestRoom;

import java.util.*;

public class ButtonListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;
    private List<FloorRestRoom> mItemList;
    private int mLayout;
    private FragmentActivity fragmentActivity;

    public ButtonListAdapter(Context context, int layout, List<FloorRestRoom> itemList, FragmentActivity fragmentActivity) {
        this.context = context;
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
            viewHolder.subject = (Button) convertView.findViewById(R.id.btn_subject);
            viewHolder.restRoomList = (ListView) convertView.findViewById(R.id.lv_rest_room);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CampusViewHolder) convertView.getTag();
        }

        FloorRestRoom item = mItemList.get(position);
        int floor = Integer.parseInt(item.getFloor());
        Log.d("buttonList", "floor = " + floor);
        String floorStr;
        if(floor < 0) {
            floorStr = "B" + (-floor) + "층";
        } else {
            floorStr = floor + "층";
        }
        Log.d("buttonList", "floorStr = " + floorStr);
        viewHolder.subject.setText(floorStr + "(펼치기)");
        final Button btnSubject = viewHolder.subject;
        final ListView lvRestRoom = viewHolder.restRoomList;
        viewHolder.subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnSubject.getText().toString().contains("펼치기")) {
                    lvRestRoom.setVisibility(View.VISIBLE);
                    btnSubject.setText(btnSubject.getText().toString().replace("(펼치기)", "") + "(숨기기)");
                } else {
                    lvRestRoom.setVisibility(View.GONE);
                    btnSubject.setText(btnSubject.getText().toString().replace("(숨기기)", "") + "(펼치기)");
                }
            }
        });
        final List<RestRoom> restRoomList = item.getRestRoomList();
        Log.d("buttonList", "rest room size = " + restRoomList.size());
        RestRoomAdapter restRoomAdapter1 = new RestRoomAdapter(context, R.layout.restroom_item, restRoomList, fragmentActivity);
        viewHolder.restRoomList.setAdapter(restRoomAdapter1);
        viewHolder.restRoomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment restRoomDetail = RestRoomDetailFragment.newInstance(restRoomList.get(position));
                FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_main, restRoomDetail);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        viewHolder.restRoomList.setVisibility(View.GONE);

        return convertView;
    }

    public class CampusViewHolder {
        public Button subject;
        public ListView restRoomList;
    }
}
