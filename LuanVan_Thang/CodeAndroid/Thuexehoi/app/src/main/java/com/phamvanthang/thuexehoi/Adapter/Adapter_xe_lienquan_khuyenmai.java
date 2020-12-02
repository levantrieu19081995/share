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
import com.phamvanthang.thuexehoi.Model.Xe_lienquan_khuyenmai;
import com.phamvanthang.thuexehoi.Model.Xe_lienquan_trangchu;
import com.phamvanthang.thuexehoi.R;

import java.util.List;

public class Adapter_xe_lienquan_khuyenmai extends RecyclerView.Adapter<Adapter_xe_lienquan_khuyenmai.ViewHolder> {
    private Context mContext;
    private List<Xe_lienquan_khuyenmai> mXe;


    public Adapter_xe_lienquan_khuyenmai(Context mContext, List<Xe_lienquan_khuyenmai> mXe) {
        this.mContext = mContext;
        this.mXe = mXe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( mContext ).inflate( R.layout.item_xelienquan_khuyenmai, parent, false );
        return new Adapter_xe_lienquan_khuyenmai.ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Xe_lienquan_khuyenmai xe_lienquan_khuyenmai = mXe.get( position );
        holder.txt_tenxe.setText( xe_lienquan_khuyenmai.getTenxe() );
        holder.txt_gia.setText( xe_lienquan_khuyenmai.getGiaxe() );
        holder.txt_gia.setPaintFlags(  holder.txt_gia.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );

        holder.txt_giagiam.setText( ""+tinhphantram(Double.parseDouble( xe_lienquan_khuyenmai.getPhantramgiam() ),Integer.parseInt(xe_lienquan_khuyenmai.getGiaxe()) ));

        if (xe_lienquan_khuyenmai.getDiachi().equals( "null" )) {
            holder.txt_diachi.setText( "Cập nhật chi tiết sau" );
        } else {
            holder.txt_diachi.setText( xe_lienquan_khuyenmai.getDiachi() );
        }


        if (xe_lienquan_khuyenmai.getImg().equals( "default" )) {
            holder.img_xe.setImageResource( R.drawable.xehoi2 );
        } else {
            Glide.with( mContext ).load( Class_bien_duongdan.ip_server + xe_lienquan_khuyenmai.getImg() ).into( holder.img_xe );
        }

        onclickItem( holder, xe_lienquan_khuyenmai.getMa_xe(), xe_lienquan_khuyenmai.getMa_sx() );
    }

    @Override
    public int getItemCount() {
        return mXe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_xe;
        TextView txt_tenxe;
        TextView txt_gia;
        TextView txt_diachi;
        TextView txt_giagiam;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            img_xe = itemView.findViewById( R.id.img_xe );
            txt_tenxe = itemView.findViewById( R.id.txt_tenxe );
            txt_gia = itemView.findViewById( R.id.txt_gia );
            txt_diachi = itemView.findViewById( R.id.txt_diachi );
            txt_giagiam = itemView.findViewById( R.id.txt_giagiam );

        }
    }

    //.. hamf olick
    private void onclickItem(ViewHolder holder, final String ma_xe, final String ma_sx) {
        holder.img_xe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( v.getContext().getApplicationContext(), ChitietXeKhuyenMai.class );
                intent.putExtra( "ma_xe", ma_xe );
                intent.putExtra( "ma_sx", ma_sx );
                v.getContext().startActivity( intent );
            }
        } );
        holder.txt_tenxe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( v.getContext().getApplicationContext(), ChitietXeKhuyenMai.class );
                intent.putExtra( "ma_xe", ma_xe );
                intent.putExtra( "ma_sx", ma_sx );
                v.getContext().startActivity( intent );
            }
        } );
        holder.txt_gia.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( v.getContext().getApplicationContext(), ChitietXeKhuyenMai.class );
                intent.putExtra( "ma_xe", ma_xe );
                intent.putExtra( "ma_sx", ma_sx );
                v.getContext().startActivity( intent );
            }
        } );
        holder.txt_giagiam.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( v.getContext().getApplicationContext(), ChitietXeKhuyenMai.class );
                intent.putExtra( "ma_xe", ma_xe );
                intent.putExtra( "ma_sx", ma_sx );
                v.getContext().startActivity( intent );
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
