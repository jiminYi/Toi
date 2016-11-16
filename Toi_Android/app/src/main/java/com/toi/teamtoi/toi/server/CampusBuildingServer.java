package com.toi.teamtoi.toi.server;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ListView;

import com.toi.teamtoi.toi.R;
import com.toi.teamtoi.toi.adapter.CampusAdapter;
import com.toi.teamtoi.toi.data.Building;
import com.toi.teamtoi.toi.data.Campus;

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

public class CampusBuildingServer {
    private static final String TAG_BID = "bid";
    private static final String TAG_NAME = "name";
    private static final String TAG_START_FLOOR = "start_floor";
    private static final String TAG_END_FLOOR = "end_floor";
    private String json;
    private Context context;
    private FragmentActivity fragmentActivity;
    private ListView listView;

    public CampusBuildingServer(Context context, FragmentActivity fragmentActivity, ListView listView) {
        this.context = context;
        this.fragmentActivity = fragmentActivity;
        this.listView = listView;
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
                showCampusBuildingList();
            }
        }
        GetDataJSON server = new GetDataJSON(params);
        server.execute(url);
    }

    private void showCampusBuildingList() {
        try {
            JSONObject jsonObj = new JSONObject(json);
            Iterator keys = jsonObj.keys();
            List<Campus> campusList = new ArrayList<Campus>();
            while (keys.hasNext()) {
                final String campus = (String) keys.next();
                List<Building> buildingList = new ArrayList<Building>();
                JSONArray buildingArray = jsonObj.getJSONArray(campus);
                for (int i = 0; i < buildingArray.length(); i++) {
                    JSONObject c = buildingArray.getJSONObject(i);
                    String bid = c.getString(TAG_BID);
                    String name = c.getString(TAG_NAME);
                    String start_floor = c.getString(TAG_START_FLOOR);
                    String end_floor = c.getString(TAG_END_FLOOR);
                    Building building = new Building(bid, name, Integer.parseInt(start_floor), Integer.parseInt(end_floor));
                    buildingList.add(building);
                }
                campusList.add(new Campus(campus, buildingList));
            }
            CampusAdapter campusAdapter = new CampusAdapter(context, R.layout.campus_item, campusList, fragmentActivity);
            listView.setAdapter(campusAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
