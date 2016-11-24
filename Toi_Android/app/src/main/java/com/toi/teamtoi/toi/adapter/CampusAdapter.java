package com.toi.teamtoi.toi.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.toi.teamtoi.toi.FloorRestRoomFragment;
import com.toi.teamtoi.toi.R;
import com.toi.teamtoi.toi.data.Building;
import com.toi.teamtoi.toi.data.Campus;

import java.util.List;

public class CampusAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Campus> campusList;
    private int layout;
    private FragmentActivity fragmentActivity;

    public CampusAdapter(Context context, int layout, List<Campus> campusList, FragmentActivity fragmentActivity) {
        this.context = context;
        this.layout = layout;
        this.campusList = campusList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.fragmentActivity = fragmentActivity;
    }

    @Override
    public int getCount() {
        return campusList.size();
    }

    @Override
    public Object getItem(int position) {
        return campusList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CampusViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);

            viewHolder = new CampusViewHolder();
            viewHolder.campusName = (TextView) convertView.findViewById(R.id.tv_campus_name);
            viewHolder.lvBuilding = (ListView) convertView.findViewById(R.id.lv_building);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CampusViewHolder) convertView.getTag();
        }

        viewHolder.campusName.setText(campusList.get(position).getName());
        final List<Building> buildings = campusList.get(position).getBuildings();
        BuildingAdapter buildingAdapter = new BuildingAdapter(context, R.layout.building_item, buildings);
        viewHolder.lvBuilding.setAdapter(buildingAdapter);
        ViewGroup.LayoutParams params = viewHolder.lvBuilding.getLayoutParams();
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 51 * campusList.get(position).getBuildings().size(), r.getDisplayMetrics());
        params.height = (int) px;
        viewHolder.lvBuilding.setLayoutParams(params);
        viewHolder.lvBuilding.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int startFloor = buildings.get(position).getStartFloor();
                int endFloor = buildings.get(position).getEndFloor();
                Fragment floorRestFragment = FloorRestRoomFragment.newInstance(buildings.get(position).getName(), startFloor, endFloor);
                FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_main, floorRestFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return convertView;
    }

    public class CampusViewHolder {
        public TextView campusName;
        public ListView lvBuilding;
    }
}