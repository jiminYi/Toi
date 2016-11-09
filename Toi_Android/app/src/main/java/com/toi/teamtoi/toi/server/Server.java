package com.toi.teamtoi.toi.server;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;

import com.toi.teamtoi.toi.R;
import com.toi.teamtoi.toi.adapter.RestRoomAdapter1;
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

public class Server {
    private static final String TAG_RESULTS="result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADD ="address";
    private String json;
    private JSONArray restRooms = null;
    private ArrayList<RestRoom> restRoomList;
    private Context context;
    private FragmentActivity fragmentActivity;
    private ListView listView;

    public Server(Context context, FragmentActivity fragmentActivity, ListView listView) {
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
                    HttpPost httppost= new HttpPost("http://35.162.76.175/empty.php");
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
                showList();
            }
        }
        GetDataJSON server = new GetDataJSON(params);
        server.execute(url);
    }

    private void showList(){
        try {
            JSONObject jsonObj = new JSONObject(json);
            restRooms = jsonObj.getJSONArray(TAG_RESULTS);
            for(int i = 0;i < restRooms.length(); i++){
                JSONObject c = restRooms.getJSONObject(i);
                String position = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String address = c.getString(TAG_ADD);
                //String position, String waitingTime, String status, int floor, int maxNumOfPeople, int numOfSpace, int numOfEmptySpace, boolean hasVendingMachine, boolean isPowderRoom
                RestRoom persons = new RestRoom();
                restRoomList.add(persons);
            }
            RestRoomAdapter1 adapter = new RestRoomAdapter1(context, R.layout.restroom_item1, restRoomList, fragmentActivity);
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
