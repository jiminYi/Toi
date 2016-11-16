package com.toi.teamtoi.toi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.toi.teamtoi.toi.adapter.CampusAdapter;
import com.toi.teamtoi.toi.data.Building;
import com.toi.teamtoi.toi.data.Campus;
import com.toi.teamtoi.toi.server.CampusBuildingServer;
import com.toi.teamtoi.toi.server.PostParam;

import java.util.ArrayList;
import java.util.List;

public class BuildingListFragment extends Fragment {
    private static final String ARG_PARAM_URL = "arg_param_url";
    private String url;

    public BuildingListFragment() {

    }

    public static BuildingListFragment newInstance(String url) {
        BuildingListFragment fragment = new BuildingListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("전체 화장실 - 건물 선택");
        if (getArguments() != null) {
            url = getArguments().getString(ARG_PARAM_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_building_list, container, false);
        ListView lv_campus = (ListView) view.findViewById(R.id.lv_campus);
        CampusBuildingServer server = new CampusBuildingServer(getContext(), getActivity(), lv_campus);
        server.getData(url, new ArrayList<PostParam>());
        return view;
    }
}
