package com.phamvanthang.thuexehoi.Activity_main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Dangky extends AppCompatActivity {

    MaterialEditText edit_tennguoidung, edit_sdt, edit_email, edit_password, edit_re_password;
    Button btn_dangky, btn_huydangky;
    String url_kiemtradangky = Class_bien_duongdan.ip_server + "/kiemtradangky.php";
    String url_dangky = Class_bien_duongdan.ip_server + "/dangky.php";
    String Ketquatruyvan, Ketquadangky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dangky );
        Mapping();


        //.. xử lý khi nhấn btn_dangky
        btn_dangky.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuly_dangky();
                kiemTraThongTinNhap();
            }
        } );

        //.. xử lý khi nhấn btn_huydangky
        btn_huydangky.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
    }

    // hàm ánh xạ các view
    private void Mapping() {
        edit_tennguoidung = findViewById( R.id.edit_tennguoidung );
        edit_sdt = findViewById( R.id.edit_sdt );
        edit_email = findViewById( R.id.edit_email );
        edit_password = findViewById( R.id.edit_password );
        edit_re_password = findViewById( R.id.edit_re_password );
        btn_dangky = findViewById( R.id.btn_dangky );
        btn_huydangky = findViewById( R.id.btn_huydangky );

    }

    String tennguoidung, sdt, email, password, re_password;
    boolean nhapvao = false;

    // hàm xu lý dang ky
    private void xuly_dangky() {
        sdt = edit_sdt.getText().toString().trim();
        if (sdt.isEmpty()) {
            edit_sdt.setError( "Vui lòng nhập số điện!" );
            edit_sdt.setFocusable( true );
        } else {
            kiemtra_trung_tenDangNhap( sdt );
        }
    }

    //...
    private void kiemtra_trung_tenDangNhap(final String edit_sdt) {
        RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
        StringRequest stringRequest = new StringRequest( Request.Method.POST, url_kiemtradangky,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray( response );
                            JSONObject jsonObject = jsonArray.getJSONObject( 0 );
                            Ketquatruyvan = jsonObject.getString( "Ketquatruyvan" ).replace( "\r", "" ).replace( "\n", "" ).trim();
                            //..
                            if (Ketquatruyvan.trim().equals( "cothedangky" )) {
                                dangky();
                            }
                            //..
                            if (Ketquatruyvan.trim().equals( "tendangky_datontai" )) {
                                trungTenNGuoiDung_khidangky();
                                Toast.makeText( Dangky.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( Dangky.this, "Server đang gập sự cố...!", Toast.LENGTH_SHORT ).show();
                    }
                } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pagrams = new HashMap<>();
                pagrams.put( "tendangnhap", edit_sdt );
                return pagrams;
            }
        };
        requestQueue.add( stringRequest );

    }
    // _____________________________________________________________________________________________________________//

    //..
    private void trungTenNGuoiDung_khidangky() {
        edit_sdt.setError( "số điện thoại đã tồn tại!" );
        edit_sdt.setFocusable( true );
    }
    // _____________________________________________________________________________________________________________//

    //..
    private boolean kiemtra_Pass_Repass() {
        String passwords = edit_password.getText().toString().trim();
        String re_passwords = edit_re_password.getText().toString().trim();
        if (passwords.equals( re_passwords )) {
            return true;
        } else {
            edit_re_password.setError( "Mật Khẩu không giống nhau !" );
            edit_re_password.setFocusable( true );
            return false;
        }
    }
    // _____________________________________________________________________________________________________________//

    //..
    private void dangky() {

        if(kiemTraThongTinNhap()){
            RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
            StringRequest stringRequest = new StringRequest( Request.Method.POST, url_dangky,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray( response );
                                JSONObject jsonObject = jsonArray.getJSONObject( 0 );
                                Ketquadangky = jsonObject.getString( "ketqua" ).replace( "\r", "" ).replace( "\n", "" ).trim();
                                //..
                                if (Ketquadangky.trim().equals( "thanhcong" )) {
                                    Toast.makeText( Dangky.this, "Đăng ký thành công !", Toast.LENGTH_SHORT ).show();
                                    finish();
                                }
                                //..
                                if (Ketquatruyvan.trim().equals( "thatbai" )) {
                                    Toast.makeText( Dangky.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT ).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText( Dangky.this, "Server đang gập sự cố...!", Toast.LENGTH_SHORT ).show();
                        }
                    } ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> pagrams = new HashMap<>();
                    pagrams.put( "tennguoidung_dk", tennguoidung );
                    pagrams.put( "sdt_dk", sdt );
                    pagrams.put( "email_dk", email );
                    pagrams.put( "pass_dk", password );
                    return pagrams;
                }
            };
            requestQueue.add( stringRequest );
        }
    }
    // _____________________________________________________________________________________________________________//

    // hàm lấy kiểm tra nhập đầy đủ không khi nhập thông tin đăng nhập
    private boolean kiemTraThongTinNhap() {
        String tenngouidungs = edit_tennguoidung.getText().toString();
        String sdts = edit_sdt.getText().toString();
        String emails = edit_email.getText().toString();
        String passwords = edit_password.getText().toString();
        String re_passwords = edit_re_password.getText().toString();

        if (sdts.isEmpty()) {
            edit_sdt.setError( "Vui lòng nhập số điện thoại !" );
            edit_sdt.setFocusable( true );
            nhapvao = false;
            return nhapvao;
        } else if (tenngouidungs.isEmpty()) {
            edit_tennguoidung.setError( "Vui lòng nhập tên người dùng !" );
            edit_tennguoidung.setFocusable( true );
            nhapvao = false;
            return nhapvao;
        } else if (emails.isEmpty()) {
            edit_email.setError( "Vui lòng nhập email !" );
            edit_email.setFocusable( true );
            nhapvao = false;
            return nhapvao;
        } else if (passwords.isEmpty()) {
            edit_password.setError( "Vui lòng nhập mật khẩu !" );
            edit_password.setFocusable( true );
            nhapvao = false;
            return nhapvao;
        } else if (re_passwords.isEmpty()) {
            edit_re_password.setError( "Vui lòng nhập xác nhận mật khẩu !" );
            edit_re_password.setFocusable( true );
            nhapvao = false;
            return nhapvao;
        } else {
            if (kiemtra_Pass_Repass()) {
                nhapvao = true;
                tennguoidung = edit_tennguoidung.getText().toString().trim();
                sdt = edit_sdt.getText().toString().trim();
                email = edit_email.getText().toString().trim();
                password = edit_password.getText().toString().trim();
                re_password = edit_re_password.getText().toString().trim();
            } else {
                nhapvao = false;
                return nhapvao;
            }

        }
        return nhapvao;
    }
    //__________________________________________________________________________________________________________________________________________//
}
