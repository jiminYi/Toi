package com.toi.teamtoi.toi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toi.teamtoi.toi.server.FloorRestRoomServer;
import com.toi.teamtoi.toi.server.PostParam;

import java.util.ArrayList;
import java.util.List;

public class FloorRestRoomFragment extends Fragment {
    private static final String ARG_BUILDING_NAME = "building name";
    private static final String ARG_START_FLOOR = "start floor";
    private static final String ARG_END_FLOOR = "end floor";
    private String buildingName;
    private int startFloor;
    private int endFloor;
    private LinearLayout linearLayout;
    private static FloorRestRoomFragment fragment;

    public FloorRestRoomFragment() {

    }

    public static FloorRestRoomFragment getInstance() {
        return fragment;
    }

    public static FloorRestRoomFragment newInstance(String buildingName, int startFloor, int endFloor) {
        fragment = new FloorRestRoomFragment();
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
        if (getArguments() != null) {
            buildingName = getArguments().getString(ARG_BUILDING_NAME);
            startFloor = getArguments().getInt(ARG_START_FLOOR);
            endFloor = getArguments().getInt(ARG_END_FLOOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_floor_rest_room, container, false);
        getActivity().setTitle("전체 화장실 - 화장실 선택");
        if(MainActivity.refreshMenu != null) {
            MainActivity.refreshMenu.setEnabled(true);
            MainActivity.refreshMenu.setVisible(true);
        }
        linearLayout = (LinearLayout) view.findViewById(R.id.floor_rest_room_layout);
        TextView tvTitle = new TextView(getContext());
        tvTitle.setText(buildingName);
        FrameLayout.LayoutParams pm = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pm.gravity = Gravity.CENTER;
        tvTitle.setLayoutParams(pm);
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setTextSize(20);
        tvTitle.setTextColor(getResources().getColor(R.color.colorTitle));
        tvTitle.setPadding(5, 5, 5, 5);
        tvTitle.setBackgroundResource(R.color.button);
        linearLayout.addView(tvTitle);
        FloorRestRoomServer server = new FloorRestRoomServer(startFloor, endFloor, getContext(), getActivity(), linearLayout);
        List<PostParam> postParams = new ArrayList<PostParam>();
        PostParam postParam = new PostParam("building_name", buildingName);
        postParams.add(postParam);
        server.getData(MainActivity.SERVER_ADDR + "rest_room_by_building.php", postParams);
        return view;
    }

    public void refresh() {
        linearLayout.removeAllViews();
        TextView tvTitle = new TextView(getContext());
        tvTitle.setText(buildingName);
        FrameLayout.LayoutParams pm = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pm.gravity = Gravity.CENTER;
        tvTitle.setLayoutParams(pm);
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setTextSize(20);
        tvTitle.setTextColor(getResources().getColor(R.color.colorTitle));
        tvTitle.setPadding(5, 5, 5, 5);
        tvTitle.setBackgroundResource(R.color.button);
        linearLayout.addView(tvTitle);
        FloorRestRoomServer server = new FloorRestRoomServer(startFloor, endFloor, getContext(), getActivity(), linearLayout);
        List<PostParam> postParams = new ArrayList<PostParam>();
        PostParam postParam = new PostParam("building_name", buildingName);
        postParams.add(postParam);
        server.getData(MainActivity.SERVER_ADDR + "rest_room_by_building.php", postParams);
    }
}
