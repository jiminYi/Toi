package com.toi.teamtoi.toi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.toi.teamtoi.toi.server.PostParam;
import com.toi.teamtoi.toi.server.BuildingRestRoomServer;
import com.toi.teamtoi.toi.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class BuildingRestRoomFragment extends Fragment {
    private static final String ARG_PARAM_URL = "arg_param_url";
    private static final String ARG_PARAM_TITLE = "arg_param_title";
    private String url;
    private BuildingRestRoomServer server;
    private String title;

    public BuildingRestRoomFragment() {

    }

    public static BuildingRestRoomFragment newInstance(String url, String title) {
        BuildingRestRoomFragment fragment = new BuildingRestRoomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_URL, url);
        args.putString(ARG_PARAM_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(ARG_PARAM_URL);
            title = getArguments().getString(ARG_PARAM_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_building_rest_room, container, false);
        if(MainActivity.refreshMenu != null) {
            MainActivity.refreshMenu.setEnabled(false);
            MainActivity.refreshMenu.setVisible(false);
        }
        getActivity().setTitle(title);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.building_rest_room_layout);
        server = new BuildingRestRoomServer(getContext(), getActivity(), linearLayout);
        if(url.contains("favorite")){
            DBHelper dbHelper = new DBHelper(getContext(), "Toi.db",null,1);
            List<String> rids = dbHelper.getResult();
            String favoriteStr = "";
            for(int i = 0; i < rids.size(); i++) {
                String rid = rids.get(i);
                if(i != 0) {
                    favoriteStr += ", ";
                }
                favoriteStr += rid;
            }
            List<PostParam> postParams = new ArrayList<PostParam>();
            PostParam postParam = new PostParam("favorite", favoriteStr);
            postParams.add(postParam);
            server.getData(url, postParams);
        } else {
            server.getData(url, new ArrayList<PostParam>());
        }
        return view;
    }
}
