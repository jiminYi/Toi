package com.toi.teamtoi.toi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.toi.teamtoi.toi.adapter.RestRoomAdapter1;
import com.toi.teamtoi.toi.data.RestRoom;
import com.toi.teamtoi.toi.server.PostParam;
import com.toi.teamtoi.toi.server.Server;

import java.util.ArrayList;
import java.util.List;

public class BuildingRestRoomFragment extends Fragment {
    public BuildingRestRoomFragment() {

    }

    public static BuildingRestRoomFragment newInstance() {
        BuildingRestRoomFragment fragment = new BuildingRestRoomFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_building_rest_room, container, false);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.building_rest_room_layout);
        Server server = new Server(getContext(), getActivity(), linearLayout);
        server.getData("http://35.162.76.175/empty_building.php", new ArrayList<PostParam>());
//        final List<String> buildings = new ArrayList<String>();
//        buildings.add("명신관");
//        buildings.add("순헌관");
//        buildings.add("진리관");
//        for(int i = 0; i < buildings.size(); i++) {
//            final LinearLayout subLayout = new LinearLayout(getActivity());
//            subLayout.setOrientation(LinearLayout.VERTICAL);
//            FrameLayout.LayoutParams pm = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//            pm.gravity = Gravity.CENTER;
//            final Button btnBuilding = new Button(getContext());
//            final ListView lvRestRoom = new ListView(getContext());
//            final String buildingName = buildings.get(i);
//            btnBuilding.setText(buildingName + "(펼치기)");
//            btnBuilding.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getActivity(), "건물: " + buildingName, Toast.LENGTH_SHORT).show();
//                    if (btnBuilding.getText().toString().contains("펼치기")) {
//                        lvRestRoom.setVisibility(View.VISIBLE);
//                        btnBuilding.setText(buildingName + "(숨기기)");
//                    } else {
//                        lvRestRoom.setVisibility(View.GONE);
//                        btnBuilding.setText(buildingName + "(펼치기)");
//                    }
//                }
//            });
//            List<RestRoom> restRooms = new ArrayList<RestRoom>();
//            //String position, String waitingTime, String status, int floor, int maxNumOfPeople, int numOfSpace, int numOfEmptySpace, boolean hasVendingMachine, boolean isPowderRoom
//            restRooms.add(new RestRoom("명신 앞", "3분", 3, 7, 3, 0, false, false));
//            restRooms.add(new RestRoom("명신 뒤", "1분", 3, 2, 3, 0, false, false));
//            RestRoomAdapter1 restRoomAdapter1 = new RestRoomAdapter1(getContext(), R.layout.restroom_item1, restRooms, getActivity());
//            lvRestRoom.setAdapter(restRoomAdapter1);
//            lvRestRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Fragment restRoomDetail = RestRoomDetailFragment.newInstance();
//                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.fragment_main, restRoomDetail);
//                    transaction.addToBackStack(null);
//                    transaction.commit();
//                }
//            });
//            lvRestRoom.setVisibility(View.GONE);
//            subLayout.addView(btnBuilding);
//            subLayout.addView(lvRestRoom);
//            linearLayout.addView(subLayout);
//        }
        return view;
    }
}
