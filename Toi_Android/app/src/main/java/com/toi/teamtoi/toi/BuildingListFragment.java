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

import java.util.ArrayList;
import java.util.List;

public class BuildingListFragment extends Fragment {
    public BuildingListFragment() {

    }

    public static BuildingListFragment newInstance() {
        BuildingListFragment fragment = new BuildingListFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("전체 화장실 - 건물 선택");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_building_list, container, false);
        ListView lv_campus = (ListView) view.findViewById(R.id.lv_campus);
        List<Campus> campusList = new ArrayList<Campus>();
        List<Building> building1 = new ArrayList<Building>();
        building1.add(new Building("명신관", -2, 7));
        building1.add(new Building("순헌관", -1, 9));
        campusList.add(new Campus("제 1 캠퍼스", building1));
        List<Building> building2 = new ArrayList<Building>();
        building2.add(new Building("과학관", 1, 8));
        building2.add(new Building("미술대학", -1, 6));
        building2.add(new Building("약학대학", -1, 6));
        campusList.add(new Campus("제 2 창학 캠퍼스", building2));
        CampusAdapter campusAdapter = new CampusAdapter(getContext(), R.layout.campus_item, campusList, getActivity());
        lv_campus.setAdapter(campusAdapter);
        return view;
    }
}
