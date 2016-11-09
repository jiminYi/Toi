package com.toi.teamtoi.toi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PowderRoomFragment extends Fragment {
    public PowderRoomFragment() {

    }

    public static PowderRoomFragment newInstance() {
        PowderRoomFragment fragment = new PowderRoomFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("파우더룸");
        return inflater.inflate(R.layout.fragment_powder_room, container, false);
    }
}
