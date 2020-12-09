package com.phamvanthang.thuexehoi.Activity_main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.phamvanthang.thuexehoi.Connection.Class_bien_duongdan;
import com.phamvanthang.thuexehoi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CapnhatThongTin extends AppCompatActivity {
    String hoten, giaypheplx, diachi, cmnd,makh;
    EditText edit_hoten,edit_giaypheplx,edit_diachi,edit_cmnd;
    TextView txt_capnhat,txt_huycapnhat;
    String url_capnhat = Class_bien_duongdan.ip_server + "/capnhatthongtincanhan.php";
    String Ketquatcapnhat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_capnhat_thong_tin );
        AnhXa();
        txt_capnhat.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten = edit_hoten.getText().toString().trim();
                String gplx = edit_giaypheplx.getText().toString().trim();
                String dichi = edit_diachi.getText().toString().trim();
                String cmnd = edit_cmnd.getText().toString().trim();
                capnhatThongTin(hoten,gplx,dichi,cmnd,makh);

            }
        } );
        txt_huycapnhat.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
            }
        } );

    }

    //. hàm cập nhật thông tin cá nhân
    private void capnhatThongTin(final String hoten, final String giaypheplx, final String diachi, final String cmnd,final String makh) {
        RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
        StringRequest stringRequest = new StringRequest( Request.Method.POST, url_capnhat,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray( response );
                            JSONObject jsonObject = jsonArray.getJSONObject( 0 );
                            Ketquatcapnhat = jsonObject.getString( "ketqua" ).replace( "\r", "" ).replace( "\n", "" ).trim();
                            //..
                            if (Ketquatcapnhat.trim().equals( "thanhcong" )) {
                                System.out.println("______________________ thanhcong");
                                finish();
                            }
                            //..
                            if (Ketquatcapnhat.trim().equals( "thatbai" )) {
                                Toast.makeText( CapnhatThongTin.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT ).show();
                                System.out.println("______________________ thatbai");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( CapnhatThongTin.this, "Server đang gập sự cố...!", Toast.LENGTH_SHORT ).show();
                    }
                } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pagrams = new HashMap<>();
                pagrams.put( "hoten", hoten );
                pagrams.put( "giaypheplx", giaypheplx );
                pagrams.put( "diachi", diachi );
                pagrams.put( "cmnd", cmnd );
                pagrams.put( "makh", makh );
                return pagrams;
            }
        };
        requestQueue.add( stringRequest );
    }

    private void AnhXa() {
        Intent i = getIntent();
        hoten = i.getStringExtra( "hoten" );
        giaypheplx = i.getStringExtra( "giaypheplx" );
        diachi = i.getStringExtra( "diachi" );
        cmnd = i.getStringExtra( "cmnd" );
        makh = i.getStringExtra( "makh" );
        System.out.println(" getIntent: "+hoten+"/"+makh);

        edit_hoten = findViewById( R.id.edit_hoten );
        edit_giaypheplx = findViewById( R.id.edit_giaypheplayxe );
        edit_diachi = findViewById( R.id.edit_diachi );
        edit_cmnd = findViewById( R.id.edit_socmnd );
        txt_capnhat = findViewById( R.id.txt_capnhat );
        txt_huycapnhat = findViewById( R.id.txt_huycapnhat );


        edit_hoten.setText( hoten );
        edit_giaypheplx.setText( giaypheplx );
        edit_diachi.setText( diachi );
        edit_cmnd.setText( cmnd );
    }


}
