package com.toi.teamtoi.toi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.toi.teamtoi.toi.data.RestRoom;
import com.toi.teamtoi.toi.server.ImageServer;
import com.toi.teamtoi.toi.sqlite.DBHelper;

public class RestRoomDetailFragment extends Fragment {
    private static final String ARG_PARAM_RESTROOM = "restroom";
    private RestRoom restRoom;
    private DBHelper dbHelper;
    private boolean favorite = false;

    public RestRoomDetailFragment() {

    }

    public static RestRoomDetailFragment newInstance(RestRoom restRoom) {
        RestRoomDetailFragment fragment = new RestRoomDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_RESTROOM, restRoom);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            restRoom = (RestRoom) getArguments().get(ARG_PARAM_RESTROOM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rest_room_detail, container, false);
        getActivity().setTitle(getActivity().getTitle().toString().split("-")[0] + "- 화장실 세부정보");
        if(MainActivity.refreshMenu != null) {
            MainActivity.refreshMenu.setEnabled(false);
            MainActivity.refreshMenu.setVisible(false);
        }
        dbHelper = new DBHelper(getContext(), "Toi.db",null,1);
        final Button btnFavorite=(Button) view.findViewById(R.id.btn_favorite);
        if(dbHelper.hasRid(restRoom.getRid())){
            favorite = true;
            btnFavorite.setBackgroundResource(R.drawable.favorite_remove);
        }
        else{
            favorite = false;
            btnFavorite.setBackgroundResource(R.drawable.favorite_add);
        }
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!favorite){
                    dbHelper.insert(restRoom.getRid());
                    favorite = true;
                    btnFavorite.setBackgroundResource(R.drawable.favorite_remove);
                    Toast.makeText(getContext(), "즐겨찾기에 추가되었습니다", Toast.LENGTH_SHORT).show();
                }
                else{
                    dbHelper.delete(restRoom.getRid());
                    favorite = false;
                    btnFavorite.setBackgroundResource(R.drawable.favorite_add);
                    Toast.makeText(getContext(), "즐겨찾기에서 제거되었습니다", Toast.LENGTH_SHORT).show();
                }
                Log.d("favorite", "count = " + dbHelper.getResult().size());
            }
        });

        TextView position = (TextView) view.findViewById(R.id.tv_building_and_floor);
        String floor = "";
        if (restRoom.getFloor() > 0) {
            floor = restRoom.getFloor() + "층";
        } else {
            floor = "B" + restRoom.getFloor() + "층";
        }
        position.setText(restRoom.getBuildingName() + " " + floor + "\n" + restRoom.getPosition());
        TextView powderRoom = (TextView) view.findViewById(R.id.tv_powder_room);
        if (restRoom.isPowderRoom()) {
            powderRoom.setText("파우더룸");
        } else {
            powderRoom.setText("-");
        }
        TextView vendingMachine = (TextView) view.findViewById(R.id.tv_vending_machine);
        if (restRoom.isHasVendingMachine()) {
            vendingMachine.setText("자판기");
        } else {
            vendingMachine.setText("-");
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_image_rest_room);
        ImageServer server = new ImageServer(imageView);
        server.getData(MainActivity.SERVER_ADDR + "images/" + restRoom.getImagePath());
        return view;
    }
}
