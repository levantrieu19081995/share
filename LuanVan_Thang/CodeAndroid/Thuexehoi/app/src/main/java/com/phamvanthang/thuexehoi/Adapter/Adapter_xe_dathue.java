package com.phamvanthang.thuexehoi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.phamvanthang.thuexehoi.Activity_main.ChitietXeTrangChu;
import com.phamvanthang.thuexehoi.Connection.Class_bien_duongdan;
import com.phamvanthang.thuexehoi.Model.Xe_dathue;
import com.phamvanthang.thuexehoi.Model.Xe_trangchu;
import com.phamvanthang.thuexehoi.R;

import java.util.List;

public class Adapter_xe_dathue extends RecyclerView.Adapter<Adapter_xe_dathue.ViewHolder> {
    private Context mContext;
    private List<Xe_dathue> mXe;


    public Adapter_xe_dathue(Context mContext, List<Xe_dathue> mXe) {
        this.mContext = mContext;
        this.mXe = mXe;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.item_xedathue, parent, false );
        return new Adapter_xe_dathue.ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Xe_dathue xe_dathue = mXe.get( position );
        holder.txt_tenxe.setText( xe_dathue.getTenxe() );
        holder.txt_giatien.setText( xe_dathue.getGiaxe()+" nghìn" );
//        holder.txt_xemchitiet.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext().getApplicationContext(), ChitietXeTrangChu.class);
//                intent.putExtra("ma_xe", xe_trangchu.getMa_xe());
//                intent.putExtra("ma_sx", xe_trangchu.getMa_sx());
//                v.getContext().startActivity(intent);
//            }
//        } );

        if (xe_dathue.getImg().equals( "default" )) {
            holder.img_xe.setImageResource( R.drawable.xehoi3 );
        } else {
            Glide.with( mContext ).load( Class_bien_duongdan.ip_server+xe_dathue.getImg()).into( holder.img_xe );
        }

//        onclickItem( holder, xe_dathue.getMa_xe(), xe_dathue.getMa_sx());
    }


    @Override
    public int getItemCount() {
        return mXe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_xe;
        TextView txt_tenxe;
        TextView txt_giatien;
//        TextView txt_xemchitiet;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            img_xe = itemView.findViewById( R.id.img_xe );
            txt_tenxe = itemView.findViewById( R.id.txt_tenxe );
            txt_giatien = itemView.findViewById( R.id.txt_giatien );
//            txt_xemchitiet = itemView.findViewById( R.id.txt_xemchitiet );

        }
    }

    //.. hamf olick
    private void onclickItem(ViewHolder holder, final String ma_xe , final  String ma_sx) {
//        holder.txt_xemchitiet.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext().getApplicationContext(), ChitietXeTrangChu.class);
//                intent.putExtra("ma_xe",ma_xe);
//                intent.putExtra("ma_sx",ma_sx);
//                v.getContext().startActivity(intent);
//            }
//        } );
        holder.img_xe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext().getApplicationContext(), ChitietXeTrangChu.class);
                intent.putExtra("ma_xe",ma_xe);
                intent.putExtra("ma_sx",ma_sx);
                v.getContext().startActivity(intent);
            }
        } );
        holder.txt_tenxe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext().getApplicationContext(), ChitietXeTrangChu.class);
                intent.putExtra("ma_xe",ma_xe);
                intent.putExtra("ma_sx",ma_sx);
                v.getContext().startActivity(intent);
            }
        } );
        holder.txt_giatien.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext().getApplicationContext(), ChitietXeTrangChu.class);
                intent.putExtra("ma_xe",ma_xe);
                intent.putExtra("ma_sx",ma_sx);
                v.getContext().startActivity(intent);
            }
        } );

    }

}
