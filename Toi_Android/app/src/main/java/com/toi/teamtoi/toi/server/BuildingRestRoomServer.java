package com.toi.teamtoi.toi.server;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.toi.teamtoi.toi.R;
import com.toi.teamtoi.toi.RestRoomDetailFragment;
import com.toi.teamtoi.toi.adapter.RestRoomAdapter;
import com.toi.teamtoi.toi.data.RestRoom;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.*;

import java.io.*;
import java.util.*;

public class BuildingRestRoomServer {
    private static final String TAG_RID = "rid";
    private static final String TAG_POSITION = "position";
    private static final String TAG_WAITING_TIME = "waiting_time";
    private static final String TAG_FLOOR = "floor";
    private static final String TAG_MAX_NUM_OF_PEOPLE = "max_num_of_people";
    private static final String TAG_NUM_OF_SPACE = "num_of_space";
    private static final String TAG_NUM_OF_EMPTY_SPACE = "num_of_empty_space";
    private static final String TAG_VENDING_MACHINE = "vending_machine";
    private static final String TAG_POWDER_ROOM = "powder_room";
    private static final String TAG_IMAGE = "image";
    private String json;
    private JSONArray buildings = null;
    private JSONArray restRooms = null;
    private ArrayList<RestRoom> restRoomList;
    private Context context;
    private FragmentActivity fragmentActivity;
    private LinearLayout linearLayout;

    public BuildingRestRoomServer(Context context, FragmentActivity fragmentActivity, LinearLayout linearLayout) {
        this.context = context;
        this.fragmentActivity = fragmentActivity;
        this.linearLayout = linearLayout;
    }

    public void getData(String url, List<PostParam> params){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            public GetDataJSON(List<PostParam> params) {
                for(int i = 0; i < params.size(); i++) {
                    nameValuePairs.add(new BasicNameValuePair(params.get(i).getKey(), params.get(i).getValue()));
                }
            }

            @Override
            protected String doInBackground(String... params) {
                String result = null;
                try{
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost= new HttpPost(params[0]);
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity responseResultEntity=response.getEntity();
                    if(responseResultEntity != null){
                        InputStream is = responseResultEntity.getContent();
                        BufferedReader reader;
                        reader = new BufferedReader(new InputStreamReader(is,"UTF-8"),8);
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        is.close();
                        result = sb.toString();
                    }
                }catch(Exception e){
                    System.out.println("Exception : " + e.getMessage());
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                json = result;
                showBuildingList();
            }
        }
        GetDataJSON server = new GetDataJSON(params);
        server.execute(url);
    }

    private void showBuildingList() {
        try {
            JSONObject jsonObj = new JSONObject(json);
            Iterator keys = jsonObj.keys();
            while(keys.hasNext()){
                final String buildingName = (String)keys.next();
                final LinearLayout subLayout = new LinearLayout(fragmentActivity);
                subLayout.setOrientation(LinearLayout.VERTICAL);
                FrameLayout.LayoutParams pm = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                pm.gravity = Gravity.CENTER;
                final Button btnBuilding = new Button(context);
                final ListView lvRestRoom = new ListView(context);
                btnBuilding.setText(buildingName + "(펼치기)");
                btnBuilding.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(fragmentActivity, "건물: " + buildingName, Toast.LENGTH_SHORT).show();
                        if (btnBuilding.getText().toString().contains("펼치기")) {
                            lvRestRoom.setVisibility(View.VISIBLE);
                            btnBuilding.setText(buildingName + "(숨기기)");
                        } else {
                            lvRestRoom.setVisibility(View.GONE);
                            btnBuilding.setText(buildingName + "(펼치기)");
                        }
                    }
                });
                JSONArray restRooms = jsonObj.getJSONArray(buildingName);
                final List<RestRoom> restRoomList = new ArrayList<RestRoom>();
                for(int i = 0; i< restRooms.length(); i++) {
                    JSONObject c = restRooms.getJSONObject(i);
                    String rid = String.valueOf(c.getInt(TAG_RID));
                    String position = c.getString(TAG_POSITION);
                    String waitingTime = c.getString(TAG_WAITING_TIME);
                    int floor = c.getInt(TAG_FLOOR);
                    int maxNumOfPeople = c.getInt(TAG_MAX_NUM_OF_PEOPLE);
                    int numOfSpace = c.getInt(TAG_NUM_OF_SPACE);
                    int numOfEmptySpace = c.getInt(TAG_NUM_OF_EMPTY_SPACE);
                    boolean hasVendingMachine = c.getInt(TAG_VENDING_MACHINE) > 0 ? true : false;
                    boolean isPowderRoom = c.getInt(TAG_POWDER_ROOM) > 0 ? true : false;
                    String imagePath = c.getString(TAG_IMAGE);
                    RestRoom restRoom = new RestRoom(rid, buildingName, position, waitingTime, floor, maxNumOfPeople, numOfSpace, numOfEmptySpace, hasVendingMachine, isPowderRoom, imagePath);
                    Log.d("server", restRoom.toString());
                    restRoomList.add(restRoom);
                }
                RestRoomAdapter restRoomAdapter1 = new RestRoomAdapter(context, R.layout.restroom_item, restRoomList, fragmentActivity);
                lvRestRoom.setAdapter(restRoomAdapter1);
                lvRestRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Fragment restRoomDetail = RestRoomDetailFragment.newInstance(restRoomList.get(position));
                        FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_main, restRoomDetail);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
                lvRestRoom.setVisibility(View.GONE);
                subLayout.addView(btnBuilding);
                subLayout.addView(lvRestRoom);
                linearLayout.addView(subLayout);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
