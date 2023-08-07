package com.fpoly.asm_android2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
  public   Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,
                drawerLayout,
                toolbar,
                R.string.nav_open,
                R.string.nav_close
        );

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.product) {
                    FragmentProductManagement productManagement = new FragmentProductManagement();
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, productManagement).commit();
                    drawerLayout.close();
                    return true;
                } else if (item.getItemId() == R.id.introduce) {
                    toolbar.setTitle("Giới thiệu");
                    FragmentIntroduce fragmentIntroduce = new FragmentIntroduce();
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragmentIntroduce).commit();
                    drawerLayout.close();
                    return true;
                } else if (item.getItemId() == R.id.setting) {
                    drawerLayout.close();
                    return true;
                } else if (item.getItemId() == R.id.logOut) {
                    finish();
                    drawerLayout.close();
                    return true;
                }
                return false;
            }
        });


    }

}