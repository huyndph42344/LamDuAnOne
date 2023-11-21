package com.example.lamduanone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.lamduanone.fragment.CaNhanFragmet;
import com.example.lamduanone.fragment.DonHangFragment;
import com.example.lamduanone.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    BottomNavigationView view;
    HomeFragment homeFragment;
    FragmentManager fm;

    DonHangFragment donHangFragment;
    CaNhanFragmet caNhanFragmet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        donHangFragment = new DonHangFragment();
        caNhanFragmet = new CaNhanFragmet();
        frameLayout = findViewById(R.id.frameLayout);
        view = findViewById(R.id.bottomNavigationView);

        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frameLayout, homeFragment).commit();


        view.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.HomePage) {
                    fm.beginTransaction().replace(R.id.frameLayout, homeFragment).commit();

                } else if (item.getItemId() == R.id.DonHang) {
                    fm.beginTransaction().replace(R.id.frameLayout, donHangFragment).commit();
                } else if (item.getItemId() == R.id.Account) {
                    fm.beginTransaction().replace(R.id.frameLayout, caNhanFragmet).commit();
                }
                return true;
            }
        });
    }
}