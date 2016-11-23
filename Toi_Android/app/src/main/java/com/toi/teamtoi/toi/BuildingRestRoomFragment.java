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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BuildingRestRoomFragment extends RecoFragment implements RECORangingListener {
    private static final String ARG_PARAM_URL = "arg_param_url";
    private String url;
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;
    private RECOBeacon minBeacon;
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

        mRecoManager.setRangingListener(this);
        mRecoManager.bind(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_building_rest_room, container, false);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.building_rest_room_layout);
        server = new BuildingRestRoomServer(getContext(), getActivity(), linearLayout);
        if(url.contains("near")){
            mBluetoothManager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if(mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
            }
        }
        else {

            server.getData(url, new ArrayList<PostParam>());
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.stop(mRegions);
        this.unbind();

    }
    private void unbind() {
        try {
            mRecoManager.unbind();
        } catch (RemoteException e) {
            Log.i("RECORangingActivity", "Remote Exception");
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            //If the request to turn on bluetooth is denied, the app will be finished.
            //사용자가 블루투스 요청을 허용하지 않았을 경우, 어플리케이션은 종료됩니다.
            Toast.makeText(getContext(), "블루투스를 켜지 않으면 가까운화장실 기능 사용불가", Toast.LENGTH_SHORT).show();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void didRangeBeaconsInRegion(Collection<RECOBeacon> recoBeacons, RECOBeaconRegion recoRegion) {
        ArrayList<RECOBeacon> beaconArrayList = new ArrayList<RECOBeacon>(recoBeacons);
        Log.i("RECORangingActivity", "didRangeBeaconsInRegion() region: " + recoRegion.getUniqueIdentifier() + ", number of beacons ranged: " + recoBeacons.size());
        if(minBeacon == null) {
            if (beaconArrayList.size() > 0) {
                minBeacon = beaconArrayList.get(0);

                for (int i = 0; i < recoBeacons.size(); i++) {
                    if (beaconArrayList.get(i).getAccuracy() <= minBeacon.getAccuracy())
                        minBeacon = beaconArrayList.get(i);
                }
                Log.d("beacon", "major = " + minBeacon.getMajor() + ", minor = " + minBeacon.getMinor());
                List<PostParam> postParams = new ArrayList<PostParam>();
                PostParam major = new PostParam("major", minBeacon.getMajor() + "");
                postParams.add(major);
                PostParam minor = new PostParam("minor", minBeacon.getMinor() + "");
                postParams.add(minor);
                server.getData(url, postParams);
            } else {
                Toast.makeText(getContext(), "현재 위치를 알 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void rangingBeaconsDidFailForRegion(RECOBeaconRegion recoBeaconRegion, RECOErrorCode errorCode) {
        Log.i("RECORangingActivity", "error code = " + errorCode);
        //Write the code when the RECOBeaconService is failed to range beacons in the region.
        //See the RECOErrorCode in the documents.
        return;
    }

    @Override
    protected void start(ArrayList<RECOBeaconRegion> regions) {

        for(RECOBeaconRegion region : regions) {
            try {
                mRecoManager.startRangingBeaconsInRegion(region);
            } catch (RemoteException e) {
                Log.i("RECORangingActivity", "Remote Exception");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.i("RECORangingActivity", "Null Pointer Exception");
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void stop(ArrayList<RECOBeaconRegion> regions) {
        for(RECOBeaconRegion region : regions) {
            try {
                mRecoManager.stopRangingBeaconsInRegion(region);
            } catch (RemoteException e) {
                Log.i("RECORangingActivity", "Remote Exception");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.i("RECORangingActivity", "Null Pointer Exception");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onServiceConnect() {

        Log.i("RECORangingActivity", "onServiceConnect()");
        mRecoManager.setDiscontinuousScan(MainActivity.DISCONTINUOUS_SCAN);
        this.start(mRegions);
    }

    @Override
    public void onServiceFail(RECOErrorCode recoErrorCode) {
        return;
    }
}
