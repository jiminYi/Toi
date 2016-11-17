package com.toi.teamtoi.toi.server;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.toi.teamtoi.toi.R;
import com.toi.teamtoi.toi.RestRoomDetailFragment;
import com.toi.teamtoi.toi.adapter.ButtonListAdapter;
import com.toi.teamtoi.toi.adapter.RestRoomAdapter;
import com.toi.teamtoi.toi.data.FloorRestRoom;
import com.toi.teamtoi.toi.data.RestRoom;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FloorRestRoomServer {
    private static final String TAG_POSITION = "position";
    private static final String TAG_WAITING_TIME = "waiting_time";
    private static final String TAG_FLOOR = "floor";
    private static final String TAG_MAX_NUM_OF_PEOPLE = "max_num_of_people";
    private static final String TAG_NUM_OF_SPACE = "num_of_space";
    private static final String TAG_NUM_OF_EMPTY_SPACE = "num_of_empty_space";
    private static final String TAG_VENDING_MACHINE = "vending_machine";
    private static final String TAG_POWDER_ROOM = "powder_room";
    private static final String TAG_IMAGE = "image";
    private List<FloorRestRoom> resultList;
    private String json, buildingName;
    private Context context;
    private FragmentActivity fragmentActivity;
    private ListView listView;

    public FloorRestRoomServer(Context context, FragmentActivity fragmentActivity, ListView listView) {
        this.context = context;
        this.fragmentActivity = fragmentActivity;
        this.listView = listView;
        resultList = new ArrayList<FloorRestRoom>();

    }

    public void getData(String url, List<PostParam> params){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            public GetDataJSON(List<PostParam> params) {
                for(int i = 0; i < params.size(); i++) {
                    nameValuePairs.add(new BasicNameValuePair(params.get(i).getKey(), params.get(i).getValue()));
                    buildingName = params.get(i).getValue();
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
                parseJSON();
                showRestRoomList();
            }
        }
        GetDataJSON server = new GetDataJSON(params);
        server.execute(url);
    }

    private void parseJSON() {
        try {
            JSONObject jsonObj = new JSONObject(json);
            Iterator keys = jsonObj.keys();
            while (keys.hasNext()) {
                final String floor = (String) keys.next();
                List<RestRoom> restRoomList = new ArrayList<RestRoom>();
                JSONArray restRoomJSONArray = jsonObj.getJSONArray(floor);
                for (int i = 0; i < restRoomJSONArray.length(); i++) {
                    JSONObject c = restRoomJSONArray.getJSONObject(i);
                    String position = c.getString(TAG_POSITION);
                    String waitingTime = c.getString(TAG_WAITING_TIME);
                    int maxNumOfPeople = c.getInt(TAG_MAX_NUM_OF_PEOPLE);
                    int numOfSpace = c.getInt(TAG_NUM_OF_SPACE);
                    int numOfEmptySpace = c.getInt(TAG_NUM_OF_EMPTY_SPACE);
                    boolean hasVendingMachine = c.getInt(TAG_VENDING_MACHINE) > 0 ? true : false;
                    boolean isPowderRoom = c.getInt(TAG_POWDER_ROOM) > 0 ? true : false;
                    String imagePath = c.getString(TAG_IMAGE);
                    RestRoom restRoom = new RestRoom(buildingName, position, waitingTime, Integer.parseInt(floor), maxNumOfPeople, numOfSpace, numOfEmptySpace, hasVendingMachine, isPowderRoom, imagePath);
                    restRoomList.add(restRoom);
                }
                FloorRestRoom floorRestRoom = new FloorRestRoom(floor, restRoomList);
                resultList.add(floorRestRoom);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showRestRoomList() {
        ButtonListAdapter buttonListAdapter = new ButtonListAdapter(context, R.layout.button_list_item, resultList, fragmentActivity);
        listView.setAdapter(buttonListAdapter);
    }
}
