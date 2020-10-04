package com.example.catalogue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class TaskListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    int checkId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Intent intent = getIntent();
        checkId = intent.getIntExtra("ID_V",1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if(savedInstanceState == null && checkId == 1){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new TaskFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_task);
        Log.i("Lifecycle","Oncreate called" + checkId);
        }
        else if(R.id.nav_diary == checkId){

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new DiaryFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_diary);
        }
    }
    public void onStart() {


        super.onStart();
        Log.i("Lifecycle","OnStart called(");

        System.out.println("Hello iam");
    }
    public void onResume(){

        super.onResume();
        Log.i("Lifecycle","onResume called()");
    }
    public void onRestart(){

        super.onRestart();
        Log.i("Lifecycle","onRestart called()");
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_task:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TaskFragment()).commit();
                break;
            case R.id.nav_diary:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DiaryFragment()).commit();
                break;
            case R.id.nav_wishlist:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Wish_ListFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
        super.onBackPressed();
        }
    }


}