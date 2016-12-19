package com.toi.teamtoi.toi;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.toi.teamtoi.toi.util.NetworkUtil;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String KEY_FIRST = "first";
    public static final String SERVER_ADDR = "http://35.161.133.201/";
    public static final String RECO_UUID = "24DDF411-8CF1-440C-87CD-E368DAF9C93A";
    public static final boolean SCAN_RECO_ONLY = true;
    public static final boolean ENABLE_BACKGROUND_RANGING_TIMEOUT = true;
    public static final boolean DISCONTINUOUS_SCAN = false;
    private static final int REQUEST_LOCATION = 10;
    private Fragment fragment;
    public static MenuItem refreshMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int conn = NetworkUtil.getConnectivityStatus(getApplicationContext());
        if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            Toast.makeText(getApplicationContext(), "네트워크에 연결을 해주세요.", Toast.LENGTH_SHORT).show();
            finish();
        } else {

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            SharedPreferences prefs = getSharedPreferences("Personal", MODE_PRIVATE);
            String first = "전체 화장실";
            try {
                first = prefs.getString(KEY_FIRST, "");
            } catch (Exception e) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(MainActivity.KEY_FIRST, first);
                editor.commit();
            }
            fragment = BuildingListFragment.newInstance(SERVER_ADDR + "campus_building.php");
            switch (first) {
                case "전체 화장실":
                    fragment = BuildingListFragment.newInstance(SERVER_ADDR + "campus_building.php");
                    break;
                case "즐겨찾기":
                    setTitle("즐겨찾기");
                    fragment = BuildingRestRoomFragment.newInstance(SERVER_ADDR + "favorite.php", "즐겨찾기");
                    break;
                case "빈 화장실":
                    setTitle("빈 화장실");
                    fragment = BuildingRestRoomFragment.newInstance(SERVER_ADDR + "empty.php", "빈 화장실");
                    break;
                case "가까운 화장실":
                    setTitle("가까운 화장실");
                    fragment = NearRestRoomFragment.newInstance(SERVER_ADDR + "near.php");
                    break;
                case "파우더룸":
                    setTitle("파우더룸");
                    fragment = BuildingRestRoomFragment.newInstance(SERVER_ADDR + "powder_room.php", "파우더룸");
                    break;
                case "자판기":
                    setTitle("자판기");
                    fragment = BuildingRestRoomFragment.newInstance(SERVER_ADDR + "vending_machine.php", "자판기");
                    break;
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction().replace(R.id.fragment_main, fragment).commit();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.i("MainActivity", "The location permission (ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION) is not granted.");
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
                } else {
                    Log.i("MainActivity", "The location permission (ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION) is already granted.");
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        refreshMenu = menu.findItem(R.id.action_refresh);
        refreshMenu.setEnabled(false);
        refreshMenu.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_refresh:
                if(fragment != null && getTitle().toString().contains("화장실 선택")) {
                    FloorRestRoomFragment.getInstance().refresh();
                } else {
                    Toast.makeText(getApplicationContext(), getTitle(), Toast.LENGTH_SHORT).show();
                }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        fragment = BuildingListFragment.newInstance(SERVER_ADDR + "campus_building.php");;
        if (id == R.id.nav_whole) {
            fragment = BuildingListFragment.newInstance(SERVER_ADDR + "campus_building.php");
        } else if (id == R.id.nav_favorite) {
            setTitle("즐겨찾기");
            fragment = BuildingRestRoomFragment.newInstance(SERVER_ADDR+"favorite.php", "즐겨찾기");
        } else if (id == R.id.nav_empty) {
            setTitle("빈 화장실");
            fragment = BuildingRestRoomFragment.newInstance(SERVER_ADDR + "empty.php", "빈 화장실");
        } else if (id == R.id.nav_near) {
            setTitle("가까운 화장실");
            fragment = NearRestRoomFragment.newInstance(SERVER_ADDR+"near.php");
        } else if (id == R.id.nav_powder_room) {
            setTitle("파우더룸");
            fragment = BuildingRestRoomFragment.newInstance(SERVER_ADDR + "powder_room.php", "파우더룸");
        } else if (id == R.id.nav_vending_machine) {
            setTitle("자판기");
            fragment = BuildingRestRoomFragment.newInstance(SERVER_ADDR + "vending_machine.php", "자판기");
        } else if (id == R.id.nav_setting) {
            fragment = SettingFragment.newInstance();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction().replace(R.id.fragment_main, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
