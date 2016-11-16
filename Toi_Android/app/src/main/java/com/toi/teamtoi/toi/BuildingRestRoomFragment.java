package com.toi.teamtoi.toi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.toi.teamtoi.toi.server.PostParam;
import com.toi.teamtoi.toi.server.BuildingRestRoomServer;

import java.util.ArrayList;

public class BuildingRestRoomFragment extends Fragment {
    private static final String ARG_PARAM_URL = "arg_param_url";
    private String url;

    public BuildingRestRoomFragment() {

    }

    public static BuildingRestRoomFragment newInstance(String url) {
        BuildingRestRoomFragment fragment = new BuildingRestRoomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(ARG_PARAM_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_building_rest_room, container, false);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.building_rest_room_layout);
        BuildingRestRoomServer server = new BuildingRestRoomServer(getContext(), getActivity(), linearLayout);
        server.getData(url, new ArrayList<PostParam>());
        return view;
    }
}
