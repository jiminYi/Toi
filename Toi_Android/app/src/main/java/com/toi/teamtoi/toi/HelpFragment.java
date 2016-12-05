package com.toi.teamtoi.toi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HelpFragment extends Fragment {
    public HelpFragment() {

    }

    public static HelpFragment newInstance() {
        HelpFragment fragment = new HelpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        getActivity().setTitle("도움말");
        Button q1 = (Button) view.findViewById(R.id.help_q1);
        q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment helpFragment = Q1Fragment.newInstance("q1");
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_main, helpFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        Button q2 = (Button) view.findViewById(R.id.help_q2);
        q2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment helpFragment = Q1Fragment.newInstance("q2");
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_main, helpFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        Button q3 = (Button) view.findViewById(R.id.help_q3);
        q3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment helpFragment = Q2Fragment.newInstance("q3");
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_main, helpFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        Button q4 = (Button) view.findViewById(R.id.help_q4);
        q4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment helpFragment = Q2Fragment.newInstance("q4");
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_main, helpFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        Button q5  = (Button) view.findViewById(R.id.help_q5);
        q5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment helpFragment = Q1Fragment.newInstance("q5");
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_main, helpFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }
}
