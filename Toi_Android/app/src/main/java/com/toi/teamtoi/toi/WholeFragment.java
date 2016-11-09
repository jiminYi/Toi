package com.toi.teamtoi.toi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WholeFragment extends Fragment {
    public WholeFragment() {
    }

    public static WholeFragment newInstance() {
        WholeFragment fragment = new WholeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("전체 화장실");
        return inflater.inflate(R.layout.fragment_whole, container, false);
    }
}
