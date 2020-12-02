package com.phamvanthang.thuexehoi.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.phamvanthang.thuexehoi.Fragament.khuyenmai_Fragment;
import com.phamvanthang.thuexehoi.Fragament.thongtin_Fragment;
import com.phamvanthang.thuexehoi.Fragament.trangchu_Fragment;

public class topTab_Main_Adapter extends FragmentPagerAdapter {

    int numofTab;

    public topTab_Main_Adapter(@NonNull FragmentManager fm, int numofTab) {
        super( fm );
        this.numofTab = numofTab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new trangchu_Fragment();
            case 1:
                return new khuyenmai_Fragment();
            case 2:
                return new thongtin_Fragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numofTab;
    }
}
