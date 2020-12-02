package com.phamvanthang.thuexehoi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.phamvanthang.thuexehoi.Activity_main.ChitietXeKhuyenMai;
import com.phamvanthang.thuexehoi.Activity_main.ChitietXeTrangChu;
import com.phamvanthang.thuexehoi.Connection.Class_bien_duongdan;
import com.phamvanthang.thuexehoi.Model.Xe_khuyenmai;
import com.phamvanthang.thuexehoi.R;

import java.util.List;

public class Adapter_xe_khuyenmai extends RecyclerView.Adapter<Adapter_xe_khuyenmai.ViewHolder> {
    private Context mContext;
    private List<Xe_khuyenmai> mXe;


    public Adapter_xe_khuyenmai(Context mContext, List<Xe_khuyenmai> mXe) {
        this.mContext = mContext;
        this.mXe = mXe;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.item_xekhuyenmai, parent, false );
        return new Adapter_xe_khuyenmai.ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Xe_khuyenmai xe_km = mXe.get( position );
        holder.txt_loaixe.setText( xe_km.getLoaixe() );
        holder.txt_giaxe.setText( xe_km.getGiaxe() );
        holder.txt_giaxe.setPaintFlags(  holder.txt_giaxe.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );

        holder.txt_giakm.setText(""+tinhphantram(Double.parseDouble( xe_km.getPhantram() ),Integer.parseInt(xe_km.getGiaxe())));
        holder.txt_xemchitiet.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext().getApplicationContext(), ChitietXeKhuyenMai.class);
                intent.putExtra("ma_xe", xe_km.getMa_xe());
                intent.putExtra("ma_sx", xe_km.getMa_sx());
                v.getContext().startActivity(intent);
            }
        } );

        if (xe_km.getImg().equals( "default" )) {
            holder.img_xe.setImageResource( R.drawable.xehoi3 );
        } else {
            Glide.with( mContext ).load( Class_bien_duongdan.ip_server+xe_km.getImg()).into( holder.img_xe );
        }

        onclickItem( holder , xe_km.getMa_xe(), xe_km.getMa_sx());
    }


    @Override
    public int getItemCount() {
        return mXe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_xe;
        TextView txt_loaixe;
        TextView txt_giaxe;
        TextView txt_xemchitiet;
        TextView txt_giakm;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            img_xe = itemView.findViewById( R.id.img_xe );
            txt_loaixe = itemView.findViewById( R.id.txt_loaixe );
            txt_giaxe = itemView.findViewById( R.id.txt_giaxe );
            txt_xemchitiet = itemView.findViewById( R.id.txt_xemchitiet );
            txt_giakm = itemView.findViewById( R.id.txt_giakm );
            txt_giaxe.setPaintFlags(txt_giaxe.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }
    }

    //.. hamf olick
    private void onclickItem(ViewHolder holder,final String Ma_xe,final String Ma_sx) {
        holder.txt_xemchitiet.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext().getApplicationContext(), ChitietXeKhuyenMai.class);
                intent.putExtra("ma_xe", Ma_xe);
                intent.putExtra("ma_sx", Ma_sx);
                v.getContext().startActivity(intent);
            }
        } );
        holder.img_xe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext().getApplicationContext(), ChitietXeKhuyenMai.class);
                intent.putExtra("ma_xe", Ma_xe);
                intent.putExtra("ma_sx", Ma_sx);
                v.getContext().startActivity(intent);
            }
        } );
        holder.txt_loaixe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext().getApplicationContext(), ChitietXeKhuyenMai.class);
                intent.putExtra("ma_xe", Ma_xe);
                intent.putExtra("ma_sx", Ma_sx);
                v.getContext().startActivity(intent);
            }
        } );
        holder.txt_giaxe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext().getApplicationContext(), ChitietXeKhuyenMai.class);
                intent.putExtra("ma_xe", Ma_xe);
                intent.putExtra("ma_sx", Ma_sx);
                v.getContext().startActivity(intent);
            }
        } );
        holder.txt_giakm.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext().getApplicationContext(), ChitietXeKhuyenMai.class);
                intent.putExtra("ma_xe", Ma_xe);
                intent.putExtra("ma_sx", Ma_sx);
                v.getContext().startActivity(intent);
            }
        } );

    }
    // hàm tính phần trâm
    private Double tinhphantram (Double pt, int gia){
        Double kq;
        kq = gia -(gia * (pt/100));
        return  kq;
    }

}
