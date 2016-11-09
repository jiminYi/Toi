package com.toi.teamtoi.toi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class RestRoomDetailFragment extends Fragment {
    public RestRoomDetailFragment() {

    }

    public static RestRoomDetailFragment newInstance() {
        RestRoomDetailFragment fragment = new RestRoomDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rest_room_detail, container, false);
        getActivity().setTitle(getActivity().getTitle().toString().split("-")[0] + "- 화장실 세부정보");
        return view;
    }
}
