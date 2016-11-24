package com.toi.teamtoi.toi;

import android.app.Activity;
import android.bluetooth.*;
import android.content.*;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.perples.recosdk.*;
import com.toi.teamtoi.toi.server.*;

import java.util.*;

public class NearRestRoomFragment extends RecoFragment implements RECORangingListener {
    private static final String ARG_PARAM_URL = "arg_param_url";
    private String url;
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;
    private RECOBeacon minBeacon;
    private BuildingRestRoomServer server;

    public static NearRestRoomFragment newInstance(String url) {
        NearRestRoomFragment fragment = new NearRestRoomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("NearRestRoomFragment", "onCreate");
        if (getArguments() != null) {
            url = getArguments().getString(ARG_PARAM_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_building_rest_room, container, false);
        Log.d("NearRestRoomFragment", "onCreateView");
        mRecoManager.setRangingListener(this);
        mRecoManager.bind(this);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.building_rest_room_layout);
        server = new BuildingRestRoomServer(getContext(), getActivity(), linearLayout);
        mBluetoothManager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
        }
        return view;
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
            Log.i("NearRestRoomFragment", "Remote Exception");
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(getContext(), "블루투스를 켜지 않으면 가까운화장실 기능 사용불가", Toast.LENGTH_SHORT).show();
            return;
        } else {
            mRecoManager.setRangingListener(this);
            mRecoManager.bind(this);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void didRangeBeaconsInRegion(Collection<RECOBeacon> recoBeacons, RECOBeaconRegion recoRegion) {
        ArrayList<RECOBeacon> beaconArrayList = new ArrayList<RECOBeacon>(recoBeacons);
        Log.i("NearRestRoomFragment", "didRangeBeaconsInRegion() region: " + recoRegion.getUniqueIdentifier() + ", number of beacons ranged: " + recoBeacons.size());
        if(minBeacon == null) {
            if (beaconArrayList.size() > 0) {
                minBeacon = beaconArrayList.get(0);

                for (int i = 0; i < recoBeacons.size(); i++) {
                    if (beaconArrayList.get(i).getAccuracy() <= minBeacon.getAccuracy())
                        minBeacon = beaconArrayList.get(i);
                }
                Log.d("NearRestRoomFragment", "major = " + minBeacon.getMajor() + ", minor = " + minBeacon.getMinor());
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
        Log.i("NearRestRoomFragment", "error code = " + errorCode);
        return;
    }

    @Override
    protected void start(ArrayList<RECOBeaconRegion> regions) {
        for(RECOBeaconRegion region : regions) {
            try {
                mRecoManager.startRangingBeaconsInRegion(region);
            } catch (RemoteException e) {
                Log.i("NearRestRoomFragment", "Remote Exception");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.i("NearRestRoomFragment", "Null Pointer Exception");
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
                Log.i("NearRestRoomFragment", "Remote Exception");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.i("NearRestRoomFragment", "Null Pointer Exception");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onServiceConnect() {
        Log.i("NearRestRoomFragment", "onServiceConnect()");
        mRecoManager.setDiscontinuousScan(MainActivity.DISCONTINUOUS_SCAN);
        this.start(mRegions);
    }

    @Override
    public void onServiceFail(RECOErrorCode recoErrorCode) {
        return;
    }
}
