package com.phamvanthang.thuexehoi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.phamvanthang.thuexehoi.Connection.Class_bien_duongdan;
import com.phamvanthang.thuexehoi.R;

import java.util.List;

public class Adapter_slider  extends PagerAdapter {
    List<String> list;
    Context context;

    public Adapter_slider(Context context,List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        final View view = LayoutInflater.from(container.getContext()).inflate( R.layout.item_slider,container,false);
        ImageView img_viewpage = view.findViewById(R.id.img_slider);

        Glide.with(context).load( Class_bien_duongdan.ip_server+list.get(position)).into(img_viewpage);
        img_viewpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view,"image"+ position,Snackbar.LENGTH_SHORT).show();
            }
        });

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
