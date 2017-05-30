package com.example.l_5411.boread.homepage;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.l_5411.boread.R;
import com.example.l_5411.boread.service.CacheService;

import java.lang.reflect.Method;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public interface OnSearchListener {
        void onSearch(String str);
        void cancelSearch();
    }

    private MainFragment mainFragment;

    private OnSearchListener callBack;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private SearchView searchView;
    private View clickView;
    private SearchView.SearchAutoComplete searchAutoComplete;
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
        callBack = (OnSearchListener) mainFragment;
        if(!mainFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.layout_fragment, mainFragment, "MainFragment")
                    .commit();
        }

        showMainFragment();
        startService(new Intent(this, CacheService.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);

        searchAutoComplete.setHintTextColor(getResources().getColor(R.color.colorSecondaryText));
        searchAutoComplete.setTextColor(getResources().getColor(R.color.white));
        searchAutoComplete.setTextSize(15);

        // 设置搜索框有字时显示叉叉，无字时隐藏叉叉
        searchView.onActionViewExpanded();
        searchView.setIconified(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white);
        searchAutoComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickView.setVisibility(View.VISIBLE);
                clickView.bringToFront();
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setNavigationIcon(R.drawable.ic_back_white);
                clickView.setVisibility(View.VISIBLE);
                clickView.bringToFront();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callBack.onSearch(query);
                clickView.setVisibility(GONE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        item.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    private void showMainFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.show(mainFragment);
        transaction.commit();

        toolbar.setTitle(getResources().getString(R.string.app_name));
    }

    private void initViews() {
        clickView = (View) findViewById(R.id.click_view);
        clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchView.isShown()) {
                    closeSearchView();
                }
            }
        });
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchAutoComplete.isShown()) {
                    closeSearchView();
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
//                toolbar,
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
    public void onBackPressed() {
        // 抽屉打开
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if(searchAutoComplete.isShown()) {   // 搜索框在显示
            closeSearchView();
        } else {
            finish();
        }
    }
    public boolean searchViewIsOpen() {
        return searchAutoComplete.isShown();
    }
    public void closeSearchView() {
        Method method = null;
        try {
            searchAutoComplete.setText("");
            method = searchView.getClass().getDeclaredMethod("onCloseClicked");
            method.setAccessible(true);
            method.invoke(searchView);
            toolbar.setNavigationIcon(R.drawable.ic_menu_white);
            clickView.setVisibility(GONE);
            callBack.cancelSearch();
        } catch (Exception  e) {
            e.printStackTrace();
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

    public Toolbar getToolbar() {
        return toolbar;
    }

}
