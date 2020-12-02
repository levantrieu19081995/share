package com.phamvanthang.thuexehoi.Activity_main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.phamvanthang.thuexehoi.Connection.Class_bien_duongdan;
import com.phamvanthang.thuexehoi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Datxe extends AppCompatActivity {
   String  ma_xe , ma_kh , ngay_gioBD , ngay_gioKT, chiPhi,ten_xe,hinh_xe,diachi_xe;
   TextView txt_txt,tenxe,txt_tenxe,txt_tg_batdau,txt_tg_ketthuc,txt_diadiem,txt_tongchiphi;
   EditText edit_noidung;
    ImageView img_hinh;
    Button btn_xn_datxe;
    String url_xacnhan_datxe = Class_bien_duongdan.ip_server + "/datxe.php";
    String Ketquatruyvan ="";
    String noidung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.b_activity_datxe );
        MapPing();
        btn_xn_datxe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_noidung.getText().toString().equals( "" )){
                    Toast.makeText( Datxe.this, "Bàn bỏ lở phần nhập ghi chú kìa !", Toast.LENGTH_SHORT ).show();
                }else {
                    noidung = edit_noidung.getText().toString().trim();
                    xacnhan_datxe(ngay_gioBD,ngay_gioKT,ma_kh,ma_xe,noidung,chiPhi);
                }

            }
        } );
    }



    // hàm ánh xạ các view
    private void MapPing() {
//        txt_txt = findViewById( R.id.txt_txt );
        tenxe = findViewById( R.id.tenxe );
        txt_tenxe = findViewById( R.id.txt_tenxe );
        txt_tg_batdau = findViewById( R.id.txt_tg_batdau );
        txt_tg_ketthuc = findViewById( R.id.txt_tg_ketthuc );
        txt_diadiem = findViewById( R.id.txt_diadiem );
        txt_tongchiphi = findViewById( R.id.txt_tongchiphi );
        img_hinh = findViewById( R.id.img_hinh );
        btn_xn_datxe = findViewById( R.id.btn_xn_datxe );
        edit_noidung = findViewById( R.id.edit_noidung );

        Intent intent = getIntent();
        ma_xe = intent.getStringExtra( "Maxe" );
        ma_kh = intent.getStringExtra( "MaKh" );
        ngay_gioBD = intent.getStringExtra( "ngayBD" );
        ngay_gioKT = intent.getStringExtra( "ngayKT" );
        chiPhi = intent.getStringExtra( "chiPhi" );
        ten_xe = intent.getStringExtra( "loai_xe" );
        hinh_xe = intent.getStringExtra( "hinh_xe" );
        diachi_xe = intent.getStringExtra( "diachi_xe" );
        if(diachi_xe.equals( "" )||diachi_xe.equals( "null" )){
            diachi_xe = "cập nhật sao";
        }
//        txt_txt.setText( ma_xe+"\n"+ma_kh+"\n"+ngay_gioBD+"\n"+ngay_gioKT+"\n"+chiPhi+"\n"+loai_xe+"\n"+hinh_xe+"\n"+diachi_xe);
        tenxe.setText( ten_xe );
        txt_tenxe.setText( ten_xe);
        txt_tg_batdau.setText(ngay_gioBD );
        txt_tg_ketthuc.setText(ngay_gioKT );
        txt_diadiem.setText(diachi_xe );
        txt_tongchiphi.setText( chiPhi);
        Glide.with(getApplicationContext()).load( Class_bien_duongdan.ip_server+hinh_xe).into(img_hinh);
    }

    /// hàm xác nhận đặt xe
    private void xacnhan_datxe(final String ngaybd, final String ngaykt, final String makh, final String maxe, final String noidung, final String gia) {
        RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
        StringRequest stringRequest = new StringRequest( Request.Method.POST, url_xacnhan_datxe,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray( response );
                            JSONObject jsonObject = jsonArray.getJSONObject( 0 );
                            Ketquatruyvan = jsonObject.getString( "Ketquatruyvan" ).replace( "\r", "" ).replace( "\n", "" ).trim();
                            if(Ketquatruyvan.equals( "thatbai" )){
                                Toast.makeText( Datxe.this, "Ban không thể đặt xe!", Toast.LENGTH_SHORT ).show();
                            }if(Ketquatruyvan.equals( "thanhcong" )) {
                                Toast.makeText( Datxe.this, "Ban đặt xe thành công", Toast.LENGTH_SHORT ).show();
                                finish();
                                edit_noidung.setText( "" );
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( getApplicationContext(), "Server đang gập sự cố...!", Toast.LENGTH_SHORT ).show();
                    }
                } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pagrams = new HashMap<>();
                pagrams.put( "ngaybd", ngaybd );
                pagrams.put( "ngaykt", ngaykt );
                pagrams.put( "makh", makh );
                pagrams.put( "maxe", maxe );
                pagrams.put( "noidung", noidung );
                pagrams.put( "gia", gia );
                return pagrams;
            }
        };
        requestQueue.add( stringRequest );
    }
}
