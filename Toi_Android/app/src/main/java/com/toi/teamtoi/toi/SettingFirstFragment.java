package com.toi.teamtoi.toi;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingFirstFragment extends Fragment {
    private String first = "전체 화장실";

    public SettingFirstFragment() {

    }

    public static SettingFirstFragment newInstance() {
        SettingFirstFragment fragment = new SettingFirstFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_first, container, false);
        getActivity().setTitle("첫 화면 설정");
        if(MainActivity.refreshMenu != null) {
            MainActivity.refreshMenu.setEnabled(false);
            MainActivity.refreshMenu.setVisible(false);
        }
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.rg_first);
        SharedPreferences prefs = getActivity().getSharedPreferences("Personal", getActivity().MODE_PRIVATE);
        first = prefs.getString(MainActivity.KEY_FIRST, "");
        switch (first) {
            case "전체 화장실":
                RadioButton whole = (RadioButton) view.findViewById(R.id.first_whole);
                radioGroup.check(whole.getId());
                break;
            case "즐겨찾기":
                RadioButton favorite = (RadioButton) view.findViewById(R.id.first_favorite);
                radioGroup.check(favorite.getId());
                break;
            case "빈 화장실":
                RadioButton empty = (RadioButton) view.findViewById(R.id.first_empty);
                radioGroup.check(empty.getId());
                break;
            case "가까운 화장실":
                RadioButton near = (RadioButton) view.findViewById(R.id.first_near);
                radioGroup.check(near.getId());
                break;
            case "파우더룸":
                RadioButton powderRoome = (RadioButton) view.findViewById(R.id.first_powder_room);
                radioGroup.check(powderRoome.getId());
                break;
            case "자판기":
                RadioButton vendingMachine = (RadioButton) view.findViewById(R.id.first_vending_machine);
                radioGroup.check(vendingMachine.getId());
                break;
            default:
                RadioButton default_whole = (RadioButton) view.findViewById(R.id.first_whole);
                radioGroup.check(default_whole.getId());
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.first_whole:
                        first = "전체 화장실";
                        break;
                    case R.id.first_favorite:
                        first = "즐겨찾기";
                        break;
                    case R.id.first_empty:
                        first = "빈 화장실";
                        break;
                    case R.id.first_near:
                        first = "가까운 화장실";
                        break;
                    case R.id.first_powder_room:
                        first = "파우더룸";
                        break;
                    case R.id.first_vending_machine:
                        first = "자판기";
                        break;
                }
            }
        });
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getActivity().getSharedPreferences("Personal", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(MainActivity.KEY_FIRST, first);
                editor.commit();
                getActivity().getSupportFragmentManager().popBackStack("first", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        return view;
    }
}
