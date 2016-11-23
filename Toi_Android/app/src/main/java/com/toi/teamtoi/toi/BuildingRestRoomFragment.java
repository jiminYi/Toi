package com.toi.teamtoi.toi;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOErrorCode;
import com.perples.recosdk.RECORangingListener;
import com.toi.teamtoi.toi.server.PostParam;
import com.toi.teamtoi.toi.server.BuildingRestRoomServer;
import com.toi.teamtoi.toi.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BuildingRestRoomFragment extends Fragment {
    private static final String ARG_PARAM_URL = "arg_param_url";
    private String url;
    private BuildingRestRoomServer server;

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
