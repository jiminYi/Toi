package com.toi.teamtoi.toi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.toi.teamtoi.toi.adapter.RestRoomAdapter1;
import com.toi.teamtoi.toi.data.RestRoom;

import java.util.ArrayList;
import java.util.List;

public class FloorRestRoomFragment extends Fragment {
    private static final String ARG_BUILDING_NAME = "building name";
    private static final String ARG_START_FLOOR = "start floor";
    private static final String ARG_END_FLOOR = "end floor";

    private String buildingName;
    private int startFloor;
    private int endFloor;

    public FloorRestRoomFragment() {

    }

    public static FloorRestRoomFragment newInstance(String buildingName, int startFloor, int endFloor) {
        FloorRestRoomFragment fragment = new FloorRestRoomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_BUILDING_NAME, buildingName);
        args.putInt(ARG_START_FLOOR, startFloor);
        args.putInt(ARG_END_FLOOR, endFloor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("전체 화장실 - 화장실 선택");
        if (getArguments() != null) {
            buildingName = getArguments().getString(ARG_BUILDING_NAME);
            startFloor = getArguments().getInt(ARG_START_FLOOR);
            endFloor = getArguments().getInt(ARG_END_FLOOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_floor_rest_room, container, false);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_floor_rest_room_title);
        tvTitle.setText(buildingName);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.floor_rest_room_layout);
        Log.d("floor-rest room", "start: " + startFloor + ", end: " + endFloor);
        for(int i = startFloor; i <= endFloor; i++) {
            if(i != 0) {
                final LinearLayout subLayout = new LinearLayout(getActivity());
                subLayout.setOrientation(LinearLayout.VERTICAL);
                FrameLayout.LayoutParams pm = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                pm.gravity = Gravity.CENTER;
                final Button mButton = new Button(getActivity());
                final ListView lvRestRoom = new ListView(getActivity());
                List<RestRoom> restRooms = new ArrayList<RestRoom>();
                //String position, String waitingTime, String status, int floor, int maxNumOfPeople, int numOfSpace, int numOfEmptySpace, boolean hasVendingMachine, boolean isPowderRoom
                restRooms.add(new RestRoom("명신 앞", "3분", "혼잡", 3, 7, 3, 0, false, false));
                restRooms.add(new RestRoom("명신 뒤", "1분", "보통", 3, 2, 3, 0, false, false));
                RestRoomAdapter1 restRoomAdapter1 = new RestRoomAdapter1(getContext(), R.layout.restroom_item1, restRooms, getActivity());
                lvRestRoom.setAdapter(restRoomAdapter1);
                lvRestRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Fragment restRoomDetail = RestRoomDetailFragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_main, restRoomDetail);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
                final String floorStr;
                if(i < 0) {
                    floorStr = "B" + (-i) + "층";
                } else {
                    floorStr = i + "층";
                }
                mButton.setText(floorStr + "(펼치기)");
                mButton.setLayoutParams(pm);
                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "건물: " + buildingName + ", 층: " + mButton.getText().toString().replace("(*)", ""), Toast.LENGTH_SHORT).show();
                        if (mButton.getText().toString().contains("펼치기")) {
                            lvRestRoom.setVisibility(View.VISIBLE);
                            mButton.setText(floorStr + "(숨기기)");
                        } else {
                            lvRestRoom.setVisibility(View.GONE);
                            mButton.setText(floorStr + "(펼치기)");
                        }
                    }
                });
                lvRestRoom.setVisibility(View.GONE);
                subLayout.addView(mButton);
                subLayout.addView(lvRestRoom);
                linearLayout.addView(subLayout);
            }
        }
        return view;
    }
}
