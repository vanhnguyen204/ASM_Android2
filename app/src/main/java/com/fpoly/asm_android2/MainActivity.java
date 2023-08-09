package com.fpoly.asm_android2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Quản lý sản phẩm");
        drawerLayout = findViewById(R.id.drawerLayout);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new FragmentProductManagement()).commit();
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
                    FragmentSetting setting = new FragmentSetting();
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, setting).commit();
                    drawerLayout.close();
                    return true;
                } else if (item.getItemId() == R.id.logOut) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Do you want to logout?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            drawerLayout.close();
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();

                    return true;
                }
                return false;
            }
        });


    }

}