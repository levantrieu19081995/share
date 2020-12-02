package com.phamvanthang.thuexehoi.Activity_main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.phamvanthang.thuexehoi.Adapter.topTab_Main_Adapter;
import com.phamvanthang.thuexehoi.R;

public class Main extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    TabItem tabTrangChu, tabThongTin;
    topTab_Main_Adapter topTab_main_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.main );

        AnhXa();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    topTab_main_adapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 1) {
                    topTab_main_adapter.notifyDataSetChanged();
                }
                else if (tab.getPosition() == 2) {
                    topTab_main_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @SuppressLint("RestrictedApi")
    private void AnhXa() {
        // ... ánh xạ các view của TablayOut
        tabLayout = (TabLayout) findViewById(R.id.tablayout);

        tabTrangChu = (TabItem) findViewById(R.id.tabTrangChu);
        tabThongTin = (TabItem) findViewById(R.id.tabMenu);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        // ...
        topTab_main_adapter = new topTab_Main_Adapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(topTab_main_adapter);
        //...
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    // hàm nhấn 2 lần thoát ứng dụng
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            // super.onBackPressed();
            moveTaskToBack(true);
            return;
        } else {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Nhấn lần nữa để thoát!", Toast.LENGTH_SHORT).show();
//            Snackbar.make( getCurrentFocus(),"Nhấn lần nữa để thoát!",Snackbar.LENGTH_SHORT ).show();
            new Handler().postDelayed( new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }

    }

}
