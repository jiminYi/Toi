package com.toi.teamtoi.toi;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String KEY_FIRST = "first";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences prefs = getSharedPreferences("Personal", MODE_PRIVATE);
        String first = "전체 화장실";
        try {
            first = prefs.getString(KEY_FIRST, "");
        } catch (Exception e) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(MainActivity.KEY_FIRST, first);
            editor.commit();
        }
        Log.d("fist", first);
        Fragment fragment = BuildingListFragment.newInstance();
        switch (first) {
            case "전체 화장실":
                fragment = BuildingListFragment.newInstance();
                break;
            case "즐겨찾기":
                setTitle("즐겨찾기");
                fragment = BuildingRestRoomFragment.newInstance();
                break;
            case "빈 화장실":
                setTitle("빈 화장실");
                fragment = BuildingRestRoomFragment.newInstance();
                break;
            case "가까운 화장실":
                setTitle("가까운 화장실");
                fragment = BuildingRestRoomFragment.newInstance();
                break;
            case "파우더룸":
                setTitle("파우더룸");
                fragment = BuildingRestRoomFragment.newInstance();
                break;
            case "자판기":
                setTitle("자판기");
                fragment = BuildingRestRoomFragment.newInstance();
                break;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction().replace(R.id.fragment_main, fragment).commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_whole) {
            Fragment wholeFragment = BuildingListFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_main, wholeFragment).commit();
        } else if (id == R.id.nav_favorite) {
            setTitle("즐겨찾기");
            Fragment favoriteFragment = BuildingRestRoomFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_main, favoriteFragment).commit();
        } else if (id == R.id.nav_empty) {
            setTitle("빈 화장실");
            Fragment emptyFragment = BuildingRestRoomFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_main, emptyFragment).commit();
        } else if (id == R.id.nav_near) {
            setTitle("가까운 화장실");
            Fragment nearFragment = BuildingRestRoomFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_main, nearFragment).commit();
        } else if (id == R.id.nav_powder_room) {
            setTitle("파우더룸");
            Fragment powderRoomFragment = BuildingRestRoomFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_main, powderRoomFragment).commit();
        } else if (id == R.id.nav_vending_machine) {
            setTitle("자판기");
            Fragment vendingMachineFragment = BuildingRestRoomFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_main, vendingMachineFragment).commit();
        } else if (id == R.id.nav_setting) {
            Fragment settingFragment = SettingFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_main, settingFragment).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
