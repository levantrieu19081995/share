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
import com.phamvanthang.thuexehoi.Model.Xe_trangchu;
import com.phamvanthang.thuexehoi.R;

import java.util.List;

public class Adapter_xe_trangchu extends RecyclerView.Adapter<Adapter_xe_trangchu.ViewHolder> {
    private Context mContext;
    private List<Xe_trangchu> mXe;


    public Adapter_xe_trangchu(Context mContext, List<Xe_trangchu> mXe) {
        this.mContext = mContext;
        this.mXe = mXe;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.item_xechothue, parent, false );
        return new Adapter_xe_trangchu.ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Xe_trangchu xe_trangchu = mXe.get( position );
        holder.txt_loaixe.setText( xe_trangchu.getLoaixe() );
        holder.txt_giaxe.setText( xe_trangchu.getGiaxe()+"k/ngày" );
        holder.txt_xemchitiet.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext().getApplicationContext(), ChitietXeTrangChu.class);
                intent.putExtra("ma_xe", xe_trangchu.getMa_xe());
                intent.putExtra("ma_sx", xe_trangchu.getMa_sx());
                v.getContext().startActivity(intent);
            }
        } );

        if (xe_trangchu.getImg().equals( "default" )) {
            holder.img_xe.setImageResource( R.drawable.xehoi3 );
        }
        else if (xe_trangchu.getImg().equals( "null" )) {
            holder.img_xe.setImageResource( R.drawable.xehoi3 );
        }else {
            Glide.with( mContext ).load( Class_bien_duongdan.ip_server+xe_trangchu.getImg()).into( holder.img_xe );
        }

        onclickItem( holder, xe_trangchu.getMa_xe(), xe_trangchu.getMa_sx());
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

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            img_xe = itemView.findViewById( R.id.img_xe );
            txt_loaixe = itemView.findViewById( R.id.txt_loaixe );
            txt_giaxe = itemView.findViewById( R.id.txt_giaxe );
            txt_xemchitiet = itemView.findViewById( R.id.txt_xemchitiet );

        }
    }

    //.. hamf olick
    private void onclickItem(ViewHolder holder, final String ma_xe , final  String ma_sx) {
        holder.txt_xemchitiet.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext().getApplicationContext(), ChitietXeTrangChu.class);
                intent.putExtra("ma_xe",ma_xe);
                intent.putExtra("ma_sx",ma_sx);
                v.getContext().startActivity(intent);
            }
        } );
        holder.img_xe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext().getApplicationContext(), ChitietXeTrangChu.class);
                intent.putExtra("ma_xe",ma_xe);
                intent.putExtra("ma_sx",ma_sx);
                v.getContext().startActivity(intent);
            }
        } );
        holder.txt_loaixe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext().getApplicationContext(), ChitietXeTrangChu.class);
                intent.putExtra("ma_xe",ma_xe);
                intent.putExtra("ma_sx",ma_sx);
                v.getContext().startActivity(intent);
            }
        } );
        holder.txt_giaxe.setOnClickListener( new View.OnClickListener() {
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
