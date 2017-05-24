package com.example.l_5411.boread.homepage;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.l_5411.boread.R;
import com.example.l_5411.boread.service.CacheService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MainFragment mainFragment;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        if(savedInstanceState != null) {
            mainFragment = (MainFragment) getSupportFragmentManager().getFragment(savedInstanceState, "MainFragment");
        } else {
            mainFragment = MainFragment.newInstance();
        }

        if(!mainFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.layout_fragment, mainFragment, "MainFragment")
                    .commit();
        }

        showMainFragment();
        startService(new Intent(this, CacheService.class));
    }

    private void showMainFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.show(mainFragment);
        transaction.commit();

        toolbar.setTitle(getResources().getString(R.string.app_name));
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        int id = item.getItemId();
        if(id == R.id.nav_home) {
            showMainFragment();
        }else if(id == R.id.nav_bookmarks) {

        }else if(id == R.id.nav_change_theme) {

        }else if(id == R.id.nav_settings) {

        }else if(id == R.id.nav_about) {

        }

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mainFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "MainFragment", mainFragment);
        }
    }

    @Override
    protected void onDestroy() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (CacheService.class.getName().equals(service.service.getClassName())) {
                stopService(new Intent(this, CacheService.class));
            }
        }
        super.onDestroy();
    }
}
