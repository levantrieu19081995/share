package com.phamvanthang.thuexehoi.Activity_main;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.phamvanthang.thuexehoi.Adapter.Adapter_slider;
import com.phamvanthang.thuexehoi.Adapter.Adapter_xe_lienquan_trangchu;
import com.phamvanthang.thuexehoi.Connection.Class_bien_duongdan;
import com.phamvanthang.thuexehoi.Model.Xe_lienquan_trangchu;
import com.phamvanthang.thuexehoi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

import static java.time.LocalDateTime.of;

public class ChitietXeTrangChu extends AppCompatActivity {

    LinearLayout cnt_sokmphuthu, cnt_sokmtoida, cnt_khonggioihan;
    TextView txt_khonggioihan;
    TextView tenxe, txt_tenxe, txt_diadiem, txt_so_km_toida, txt_so_tienphuthuTren1km, txt_giathue, txt_giagiam;
    TextView txt_tg_batdau, txt_tg_ketthuc, txt_ngay_batdau, txt_ngay_ketthuc, txt_chiphi, txt_so_tienTongcong;
    TextView txt_soghe, txt_nhienlieu, txt_truyendong;
    Button btn_datxe;


    Toolbar toolbar;
    CircleIndicator circleIndicator;
    ViewPager viewPagers_fragment_trangchu;
    List<Integer> imagelist;
    List<String> hinhSlider;
    Adapter_slider adapter_slider;
    Timer timer;
    Handler handler;

    //.. danh sách xe liên quan
    private RecyclerView rcv_xelienquan;
    private Adapter_xe_lienquan_trangchu adapter_xe_lienquanTrangchu;
    private List<Xe_lienquan_trangchu> mXe_lienquanTrangchu;

    String urlserver_thongthinchitietxe_trangchu = Class_bien_duongdan.ip_server + "/get_thongtinchitietxe_trangchu.php";
    String urlserver_hinhSlider = Class_bien_duongdan.ip_server + "/get_hinhslider.php";
    String urlserver_danhsachxelienquan = Class_bien_duongdan.ip_server + "/get_danhsachxelienquan_trangchu.php";
    String urlserver_datdiemxe = Class_bien_duongdan.ip_server + "/get_datdiem.php";

    String ma_xe = "";
    String ma_sx = "";
    String ma_kh = "";
    String loai_xe ="";
    String hinh_xe = "";
    String diachi_xe ="";
    String ten_xe ="";
    static SharedPreferences sharedPreferences_dangnhap,sharedPreferences_thongtin;

    // diglog đăng nhập
    Dialog dialog_dangnhap;
    boolean nhapvao = false;
    EditText ed_use, ed_pass;
    Button btn_dangnhap_dl;
    Button btn_huydangnhap_dl;
    String user_chinh;
    String pass_chinh;
    String Ketquatruyvan, MaTk;
    String url_dangnhap = Class_bien_duongdan.ip_server + "/dangnhap.php";
    String url_laythongtinnguoidung = Class_bien_duongdan.ip_server + "/get_thongtinnguoidung.php";

    //___________________________________________________________________________________________//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_chitiet_xe_trangchu );
        Mapping();
        slider();
        danhsach_xe_lienquan();
        lay_thongtingiaxe( urlserver_thongthinchitietxe_trangchu );
        onclick();
        DatDiem();
    }
    //___________________________________________________________________________________________//

    // hàm ánh xạ các view
    private void Mapping() {
        AnhXa_dialog_dangnhap();
        txt_diadiem = (TextView) findViewById( R.id.txt_diadiem );
        txt_so_km_toida = (TextView) findViewById( R.id.txt_so_km_toida );
        txt_so_tienphuthuTren1km = (TextView) findViewById( R.id.txt_so_tienphuthuTren1km );
        txt_giathue = (TextView) findViewById( R.id.txt_giathue );
        txt_chiphi = (TextView) findViewById( R.id.txt_chiphi );
        txt_so_tienTongcong = (TextView) findViewById( R.id.txt_so_tienTongcong );
        txt_tenxe = (TextView) findViewById( R.id.txt_tenxe );
        tenxe = (TextView) findViewById( R.id.tenxe );
        btn_datxe = findViewById( R.id.btn_datxe );

        txt_tg_batdau = (TextView) findViewById( R.id.txt_tg_batdau );
        txt_tg_ketthuc = (TextView) findViewById( R.id.txt_tg_ketthuc );
        txt_ngay_batdau = (TextView) findViewById( R.id.txt_ngay_batdau );
        txt_ngay_ketthuc = (TextView) findViewById( R.id.txt_ngay_ketthuc );

        txt_soghe = (TextView) findViewById( R.id.txt_soghe );
        txt_nhienlieu = (TextView) findViewById( R.id.txt_nhienlieu );
        txt_truyendong = (TextView) findViewById( R.id.txt_truyendong );

        cnt_sokmphuthu = findViewById( R.id.cnt_sokmphuthu );
        cnt_sokmtoida = findViewById( R.id.cnt_sokmtoida );

        cnt_khonggioihan = findViewById( R.id.cnt_khonggioihan );
        txt_khonggioihan = findViewById( R.id.txt_khonggioihan );

        rcv_xelienquan = findViewById( R.id.rcv_xelienquan );
//        txt_giathue.setPaintFlags( txt_giathue.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );


        viewPagers_fragment_trangchu = findViewById( R.id.viewpagers_fragment_trangchu );
        circleIndicator = findViewById( R.id.circleIndicators_fragment_trangchu );
        toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle( "" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        toolbar.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent( getApplicationContext(), Main.class ).setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP ) );
            }
        } );

        Intent intent = getIntent();
        ma_xe = intent.getStringExtra( "ma_xe" );
        ma_sx = intent.getStringExtra( "ma_sx" );
        System.out.println( "______________________ ma_xe trang chu: " + ma_xe );

    }

    Double gia_xethue;

    // lấy danh sách xe
    private void lay_thongtingiaxe(String urlserver) {
        final RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
        StringRequest stringRequest = new StringRequest( Request.Method.POST, urlserver,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray( response );
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject op = jsonArray.getJSONObject( i );
                                txt_tenxe.setText( op.getString( "ten_lx" ) );
                                tenxe.setText( op.getString( "ten_lx" ) );
                                ten_xe =op.getString( "ten_lx" );
                                if (op.getString( "diachi_lx" ).equals( "null" ) || op.getString( "diachi_lx" ).equals( "" )) {
                                    txt_diadiem.setText( "Cập nhật sau" );
                                    diachi_xe = "Cập nhật sau";
                                } else {
                                    txt_diadiem.setText( op.getString( "diachi_lx" ) );
                                    diachi_xe =  op.getString( "diachi_lx" ) ;
                                }
                                if (op.getString( "sokmtoida_lx" ).equals( "null" ) || op.getString( "sokmtoida_lx" ).equals( "" ) || op.getString( "phikmvuot_lx" ).equals( "null" ) || op.getString( "phikmvuot_lx" ).equals( "" )) {
                                    cnt_sokmphuthu.setVisibility( View.GONE );
                                    cnt_sokmtoida.setVisibility( View.GONE );
                                    cnt_khonggioihan.setVisibility( View.VISIBLE );
                                    txt_khonggioihan.setText( "Không giới hạng" );
                                } else {
                                    txt_so_km_toida.setText( op.getString( "sokmtoida_lx" ) + "/ngày" );
                                    txt_so_tienphuthuTren1km.setText( "phí: " + op.getString( "phikmvuot_lx" ) + "đ Trên 1 km vươt giới hạn" );
                                }
                                txt_giathue.setText( op.getString( "gia_lx" ) + "K" );
                                txt_chiphi.setText( op.getString( "gia_lx" ) + "K X " + 1 + " ngày" );
                                gia_xethue = Double.valueOf( op.getString( "gia_lx" ) );
                                txt_so_tienTongcong.setText( gia_xethue * 1 + "K" );

                                hinh_xe = op.getString( "hinh" );
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pagrams = new HashMap<>();
                pagrams.put( "ma_xe", ma_xe );
                return pagrams;
            }
        };
        requestQueue.add( stringRequest );
    }

    // hàm tính phần trâm
    private Double tinhphantram(Double pt, int gia) {
        Double kq;
        kq = gia - (gia * (pt / 100));
        return kq;
    }

    // hàm ánh xạ các view
    private void slider() {
        //thòi gian cho Slider
        handler = new Handler();
        timer = new Timer();
        // mảng chứa hình slider
        imagelist = new ArrayList<>();
        hinhSlider = new ArrayList<>();
        hinhslider();

        // nội dung xự kiện slider fragment trang chủ
        circleIndicator.setViewPager( viewPagers_fragment_trangchu );
        adapter_slider = new Adapter_slider( getApplicationContext(), hinhSlider );
        viewPagers_fragment_trangchu.setAdapter( adapter_slider );
        circleIndicator.setViewPager( viewPagers_fragment_trangchu );


        // xét thời gian chạy trong slider
        timer.schedule( new TimerTask() {
            @Override
            public void run() {
                handler.post( new Runnable() {
                    @Override
                    public void run() {
                        int i = viewPagers_fragment_trangchu.getCurrentItem();
                        if (i == (hinhSlider.size() - 1)) {
                            i = 0;
                            viewPagers_fragment_trangchu.setCurrentItem( i, true );
                        } else {
                            i++;
                            viewPagers_fragment_trangchu.setCurrentItem( i, true );
                        }
                    }
                } );
            }
        }, 0, 5000 );

    }
    //___________________________________________________________________________________________//

    //... hàm lấy hình slider
    private void hinhslider() {
        final RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
        StringRequest stringRequest = new StringRequest( Request.Method.POST, urlserver_hinhSlider,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray( response );
                            hinhSlider.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject op = jsonArray.getJSONObject( i );
                                    hinhSlider.add( op.getString( "hinh" ) );
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter_slider.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pagrams = new HashMap<>();
                pagrams.put( "ma_xe", ma_xe );
                return pagrams;
            }
        };
        requestQueue.add( stringRequest );

    }

    //... hàm hiển thị danh sách xe liên quan
    private void danhsach_xe_lienquan() {
        LinearLayoutManager linearLayoutManager_khnh = new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.HORIZONTAL, false );
        rcv_xelienquan.setLayoutManager( linearLayoutManager_khnh );
        rcv_xelienquan.setHasFixedSize( true );
        rcv_xelienquan.setFocusable( false );
        rcv_xelienquan.setNestedScrollingEnabled( false );

        mXe_lienquanTrangchu = new ArrayList<>();
        danhsach_xe_lienquans();
        adapter_xe_lienquanTrangchu = new Adapter_xe_lienquan_trangchu( getApplicationContext(), mXe_lienquanTrangchu );
        rcv_xelienquan.setAdapter( adapter_xe_lienquanTrangchu );
        adapter_xe_lienquanTrangchu.notifyDataSetChanged();
    }

    //... hàm lấy danh sách xe liên quan
    private void danhsach_xe_lienquans() {
        final RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
        StringRequest stringRequest = new StringRequest( Request.Method.POST, urlserver_danhsachxelienquan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray( response );
                            mXe_lienquanTrangchu.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject op = jsonArray.getJSONObject( i );
                                mXe_lienquanTrangchu.add( new Xe_lienquan_trangchu(
                                        op.getString( "hinh" ),
                                        op.getString( "loaixe" ),
                                        op.getString( "gia" ),
                                        op.getString( "diachi" ),
                                        op.getString( "ma_xe" ),
                                        op.getString( "ma_sx" )
                                ) );

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter_xe_lienquanTrangchu.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> pagrams = new HashMap<>();
                pagrams.put( "ma_xe", ma_xe );
                pagrams.put( "ma_sx", ma_sx );
                return pagrams;
            }
        };
        requestQueue.add( stringRequest );
    }
    //____________________________________________________________________________________________________________________//

    // sét ngày tháng hiện tại cho Edit thời gian
    String ngaybd = "", ngaykt = "";
    String ngaybd_datxe = "", ngaykt_datxe = "";
    Calendar calendar;
    int ngay, thang, nam, gio, phut, thanghientai;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    int ngaybds, thangbds, nambds, giobds, phutbds, giaybds = 0;
    int ngaykts, thangkts, namkts, giokts, phutkts, giaykts = 0;

    //.. hàm quản lý các xự kiện onclick của các view
    private void onclick() {
        LayNgayThangNamGioPhut();

        // hàm Click hiện hộp thoại Giờ ở Edit thời gian
        txt_tg_batdau.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog_bd( txt_tg_batdau );
            }
        } );
        // hàm Click hiện hộp thoại Giờ ở Edit thời gian
        txt_tg_ketthuc.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog_kt( txt_tg_ketthuc );
            }
        } );

        // hàm Click hiện hộp thoại ngày tháng ở Edit thời gian bắt đầu
        txt_ngay_batdau.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog_bd( txt_ngay_batdau );
            }
        } );

        // hàm Click hiện hộp thoại ngày tháng ở Edit thời gian kết thúc
        txt_ngay_ketthuc.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog_kt( txt_ngay_ketthuc );
            }
        } );
        txt_so_tienTongcong.setOnClickListener( new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    tinhSoNgay();
                    tinhSoNgay2( nambds, thangbds, ngaybds, giobds, phutbds, giaybds, namkts, thangkts, ngaykts, giokts, phutkts, giaykts );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } );

        btn_datxe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin( getApplicationContext() );
            }
        } );
    }

    static Boolean dangnhap = false;

    // hàm kiểm tra đang nhập chưa
    public void checkLogin(Context context) {
        sharedPreferences_dangnhap = context.getSharedPreferences( "dataLogin", context.getApplicationContext().MODE_PRIVATE );
        sharedPreferences_thongtin = context.getSharedPreferences( "dataUser", context.getApplicationContext().MODE_PRIVATE );
        dangnhap = sharedPreferences_dangnhap.getBoolean( "dangnhap", false );
        ma_kh = sharedPreferences_thongtin.getString( "makh","");
        if (dangnhap == true) {
//            thongTin_DatXe(ma_xe,ma_kh, ngaybds+"/"+thangbds+"/"+nambds+" "+giobds+"-"+phutbds, ngaykts+"/"+thangkts+"/"+namkts+" "+giokts+"-"+phutkts, gia_xethue+"");
            thongTin_DatXe(ma_xe,ma_kh, ngaybd_datxe+" "+giobds+":"+phutbds, ngaykt_datxe+" "+giokts+":"+phutkts, gia_xethue+"",ten_xe,hinh_xe,diachi_xe);
        } else {
            dialog_dangnhap.show();
            XuLynoidung_dialog_dangnhap();
        }
    }
    //__________________________________________________________________________________________________________________________________________//

    // hàm lấy các thông tinh cần thiết khi đặt xe
    private void thongTin_DatXe(String Maxe, String MaKh, String ngay_gioBD, String ngay_gioKT, String chiPhi,String ten_xe, String hinh_xe, String diachi_xe){
        if(ngay_gioBD.equals( null )||ngay_gioKT.equals( null )|| ngay_gioBD.equals( "" )||ngay_gioKT.equals( "" )){
            Toast.makeText( this, "Bận cần phải nhập thông tin ngày giờ đầy đủ ", Toast.LENGTH_SHORT ).show();
        } else{
            Intent i = new Intent( ChitietXeTrangChu.this, Datxe.class );
            i.putExtra( "Maxe",Maxe );
            i.putExtra( "MaKh",MaKh );
            i.putExtra( "ngayBD",ngay_gioBD );
            i.putExtra( "ngayKT",ngay_gioKT );
            i.putExtra( "chiPhi",chiPhi );
            i.putExtra( "loai_xe",ten_xe );
            i.putExtra( "hinh_xe",hinh_xe );
            i.putExtra( "diachi_xe",diachi_xe );
            startActivity( i );
        }
    }

    //.. ánh xạ cá view dialog đăng nhập
    private void AnhXa_dialog_dangnhap() {
        dialog_dangnhap = new Dialog( ChitietXeTrangChu.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen );
        dialog_dangnhap.setContentView( R.layout.b_diglog_dangnhap );
        ed_use = (EditText) dialog_dangnhap.findViewById( R.id.ed_use );
        ed_pass = (EditText) dialog_dangnhap.findViewById( R.id.ed_pass );
        btn_dangnhap_dl = dialog_dangnhap.findViewById( R.id.btn_dangnhap );
        btn_huydangnhap_dl = dialog_dangnhap.findViewById( R.id.btn_huydangnhap );
    }

    // hàm xữ lý công việc trong dialog đăng nhập
    private void XuLynoidung_dialog_dangnhap() {
        //ham set buttom huy
        btn_huydangnhap_dl.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_dangnhap.cancel();
            }
        } );
        //ham set buttom dangnhap
        btn_dangnhap_dl.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // kiểm tra mật khẫu đúng không
                if (kiemTraThongTinNhap()) {
                    dangnhap( user_chinh, pass_chinh );
                } else {
                    Toast.makeText( getApplicationContext(), "Vui lòng kiểm tra thông tin nhập vào..!", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
    }
    //__________________________________________________________________________________________________________________________________________//

    // hàm lấy kiểm tra nhập đầy đủ không khi nhập thông tin đăng nhập
    private boolean kiemTraThongTinNhap() {
        String user = ed_use.getText().toString().trim();
        String pass = ed_pass.getText().toString().trim();
        if (user.isEmpty()) {
            ed_use.setError( "Vui lòng nhập tài khoản !" );
            ed_use.setFocusable( true );
            nhapvao = false;
            return nhapvao;
        } else if (pass.isEmpty()) {
            ed_pass.setError( "Vui lòng nhập mật khẩu !" );
            ed_pass.setFocusable( true );
            nhapvao = false;
            return nhapvao;
        } else {
            nhapvao = true;
            user_chinh = ed_use.getText().toString().trim();
            pass_chinh = ed_pass.getText().toString().trim();
        }
        return nhapvao;
    }
    //__________________________________________________________________________________________________________________________________________//

    //.. hàm đăng nhập
    private void dangnhap(final String users, final String passs) {
        RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
        StringRequest stringRequest = new StringRequest( Request.Method.POST, url_dangnhap,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray( response );
                            JSONObject jsonObject = jsonArray.getJSONObject( 0 );
                            Ketquatruyvan = jsonObject.getString( "Ketquatruyvan" ).replace( "\r", "" ).replace( "\n", "" ).trim();
                            MaTk = jsonObject.getString( "ma_tk" ).replace( "\r", "" ).replace( "\n", "" ).trim();
                            if (Ketquatruyvan.trim().equals( "thanhcong" )) {
                                // lưu vào bộ nhớ tạm thời các thông tin cần kiểm tra lần mở app sau
                                SharedPreferences.Editor editor = sharedPreferences_dangnhap.edit();
                                editor.putString( "taikhoan", user_chinh );
                                editor.putString( "matkhau", pass_chinh );
                                editor.putString( "ma_tk", MaTk );
                                editor.putBoolean( "dangnhap", true );
                                editor.commit();

                                LayThongTinCaNhan(MaTk);
                                dialog_dangnhap.cancel();

                                Toast.makeText( getApplicationContext(), "Đăng nhập thành công !", Toast.LENGTH_SHORT ).show();
                            }
                            if (Ketquatruyvan.trim().equals( "thatbai" )) {
                                Toast.makeText( getApplicationContext(), "Đăng nhập thất bại!", Toast.LENGTH_SHORT ).show();
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
                pagrams.put( "sodienthoai", users );
                pagrams.put( "passss", passs );
                return pagrams;
            }
        };
        requestQueue.add( stringRequest );
    }
    //__________________________________________________________________________________________________________________________________________//

    //hàm mã khách hàng sau khi đăng nhập thành công
    private void LayThongTinCaNhan(final String matk) {

        RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
        StringRequest stringRequest = new StringRequest( Request.Method.POST, url_laythongtinnguoidung,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray( response );
                            JSONObject jsonObject = jsonArray.getJSONObject( 0 );
                            String makh = jsonObject.getString( "makh" );
                            String stk = jsonObject.getString( "stk" );

                            String txt_tennguoidung = jsonObject.getString( "ten" );
                            String txt_giaypheplx_tt = jsonObject.getString( "gplx" );
                            String txt_email_tt = jsonObject.getString( "email" );
                            String txt_sdt_tt = jsonObject.getString( "sdt" );
                            String txt_diachi_tt = jsonObject.getString( "diachi" );
                            String txt_cmnd_tt = jsonObject.getString( "cmnd" );
                            String mlkh=jsonObject.getString( "maloaikh" );
                            String Hinh = jsonObject.getString( "Hinh" );

                            ma_kh = makh;
                            // lưu vào bộ nhớ tạm thời các thông tin cần kiểm tra lần mở app sau
                            SharedPreferences.Editor editor = sharedPreferences_thongtin.edit();
                            editor.putString( "makh", makh );
                            editor.commit();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( ChitietXeTrangChu.this,  "Server đang gập sự cố...!", Toast.LENGTH_SHORT ).show();
                    }
                } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pagrams = new HashMap<>();
                pagrams.put( "mstk_ss", matk.trim() );
                return pagrams;
            }
        };
        requestQueue.add( stringRequest );
    }
    //____________________________________________________________________________________________________________________//

    //hàm tính số ngày giữa 2 ngày
    private void tinhSoNgay() throws ParseException {
        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
        Date date = sdf.parse( ngaybd );
        cal1.setTime( date );
        date = sdf.parse( ngaykt );
        cal2.setTime( date );
    }

    //hàm tính số ngày giữa 2 ngày
    public int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void tinhSoNgay2( int yearbd, int monthbd, int dayofMonthbd, int hourbd, int minutebd, int secondbd, int yearkt, int monthkt, int dayofMonthkt, int hourkt, int minutekt, int secondkt) throws ParseException {
        LocalDateTime NgayBatDau = of( yearbd, monthbd, dayofMonthbd, hourbd, minutebd, secondbd );
        LocalDateTime NgayKetThuc = of( yearkt, monthkt, dayofMonthkt, hourkt, minutekt, secondkt );
        long differentInSeconds = Duration.between( NgayBatDau, NgayKetThuc ).getSeconds();
        // chuyển giây về giờ sử dụng hàm toHours()
        long differentInHours = Duration.between( NgayBatDau, NgayKetThuc ).toHours();
        // chuyển giây về phút sử dụng hàm toMinutes()
        long differentInMinutes = Duration.between( NgayBatDau, NgayKetThuc ).toMinutes();
        System.out.println( "__________________Sự chênh lệch giữa Ngày bắt đầu và Ngày kết thúc là: " +
                differentInSeconds + "/giây - " + differentInMinutes +
                "/phút - " + differentInHours + "/giờ." );
        songay( differentInMinutes );
    }
    public void songay(Long phut) {
        int ngay = Integer.valueOf( (int) (phut / 1440) );
        int du = Integer.valueOf( (int) (phut % 1440) );
        if (du > 0 && du < 1439) {
            System.out.println( "__________________số ngày là: " + (ngay + 1) );
            txt_chiphi.setText( gia_xethue + "đ X " + (ngay + 1) + " ngày" );
            txt_so_tienTongcong.setText( gia_xethue * (ngay + 1) + "đ" );
        } else {
            System.out.println( "__________________số ngày là: " + ngay );
            txt_chiphi.setText( gia_xethue + "đ X " + ngay + " ngày" );
            txt_so_tienTongcong.setText( gia_xethue * ngay + "đ" );
        }
    }
    //____________________________________________________________________________________________________________________//

    //hàm lấy giờ, phút, ngày, thắng, năm hiện tại
    private void LayNgayThangNamGioPhut() {
        calendar = Calendar.getInstance();
        ngay = calendar.get( Calendar.DATE );
        thang = calendar.get( Calendar.MONTH );
        nam = calendar.get( Calendar.YEAR );
        gio = calendar.get( Calendar.HOUR_OF_DAY );
        phut = calendar.get( Calendar.MINUTE );
        thanghientai = thang + 1;

        txt_ngay_batdau.setText( ngay + "/" + thanghientai + "/" + nam );
        txt_ngay_ketthuc.setText( ngay + "/" + thanghientai + "/" + nam );

        ngaybd = ngay + "/" + thanghientai + "/" + nam;
        ngaykt = ngay + "/" + thanghientai + "/" + nam;
        ngaykt_datxe = nam + "/" + thanghientai + "/" +ngay ;
        ngaybd_datxe = nam + "/" + thanghientai + "/" +ngay;

        ngaybds = ngay;
        thangbds = thanghientai;
        nambds = nam;
        ngaykts = ngay;
        thangkts = thanghientai;
        namkts = nam;

        if (phut == 0 && gio < 10) {
            txt_tg_batdau.setText( "0" + gio + "h:" + "00" );
            txt_tg_ketthuc.setText( "0" + gio + "h:" + "00" );
            giobds = gio;
            phutbds = 0;
            giokts = gio;
            phutkts = 0;
        } else if (phut < 10) {
            txt_tg_batdau.setText( gio + "h:" + "00" );
            txt_tg_ketthuc.setText( gio + "h:" + "00" );
            giobds = gio;
            phutbds = 0;
            giokts = gio;
            phutkts = 0;
        } else if (gio < 10) {
            txt_tg_batdau.setText( "0" + gio + "h:" + phut );
            txt_tg_ketthuc.setText( "0" + gio + "h:" + phut );
            giobds = gio;
            phutbds = phut;
            giokts = gio;
            phutkts = phut;
        } else {
            txt_tg_batdau.setText( gio + "h:" + phut );
            txt_tg_ketthuc.setText( gio + "h:" + phut );
            giobds = gio;
            phutbds = phut;
            giokts = gio;
            phutkts = phut;
        }
    }
    //____________________________________________________________________________________________________________________//

    // giao diện đồng hồ
    private void TimePickerDialog_bd(final TextView txt_tgbd) {
        timePickerDialog = new TimePickerDialog( this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (minute == 0 && hourOfDay < 10) {
                    txt_tgbd.setText( "0" + hourOfDay + "h:" + "00" );
                    giobds = hourOfDay;
                    phutbds = 0;
                } else if (minute == 0) {
                    txt_tgbd.setText( hourOfDay + "h:" + "00" );
                    giobds = hourOfDay;
                    phutbds = 0;

                } else if (hourOfDay < 10) {
                    txt_tgbd.setText( "0" + hourOfDay + "h:" + minute );
                    giobds = hourOfDay;
                    phutbds = minute;

                } else {
                    txt_tgbd.setText( hourOfDay + "h:" + minute );
                    giobds = hourOfDay;
                    phutbds = minute;
                }
            }
        }, gio, phut, true );
        timePickerDialog.show();
    }

    private void TimePickerDialog_kt(final TextView txt_tgkt) {
        timePickerDialog = new TimePickerDialog( this, new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (minute == 0 && hourOfDay < 10) {
                    txt_tgkt.setText( "0" + hourOfDay + "h:" + "00" );
                    giokts = hourOfDay;
                    phutkts = 0;
                } else if (minute == 0) {
                    txt_tgkt.setText( hourOfDay + "h:" + "00" );
                    giokts = hourOfDay;
                    phutkts = 0;

                } else if (hourOfDay < 10) {
                    txt_tgkt.setText( "0" + hourOfDay + "h:" + minute );
                    giokts = hourOfDay;
                    phutkts = minute;

                } else {
                    txt_tgkt.setText( hourOfDay + "h:" + minute );
                    giokts = hourOfDay;
                    phutkts = minute;
                }
                try {
                    tinhSoNgay2( nambds, thangbds, ngaybds, giobds, phutbds, giaybds, namkts, thangkts, ngaykts, giokts, phutkts, giaykts );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, gio, phut, true );
        timePickerDialog.show();
    }
    //____________________________________________________________________________________________________________________//

    // giao diện lịch
    private void DatePickerDialog_bd(final TextView txt_tg) {
        datePickerDialog = new DatePickerDialog( this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                thanghientai = month + 1;
                txt_tg.setText( dayOfMonth + "/" + thanghientai + "/" + year );

                ngaybd = dayOfMonth + "/" + thanghientai + "/" + year;
                ngaybd_datxe = year + "/" + thanghientai + "/" +dayOfMonth;
                ngaybds = dayOfMonth;
                thangbds = thanghientai;
                nambds = year;
            }
        }, nam, thang, ngay );
        datePickerDialog.show();
    }

    private void DatePickerDialog_kt(final TextView txt_tg) {
        datePickerDialog = new DatePickerDialog( this, new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                thanghientai = month + 1;
                txt_tg.setText( dayOfMonth + "/" + thanghientai + "/" + year );

                ngaykt = dayOfMonth + "/" + thanghientai + "/" + year;
                ngaykt_datxe = year + "/" + thanghientai + "/" +dayOfMonth ;

                ngaykts = dayOfMonth;
                thangkts = thanghientai;
                namkts = year;
                try {
                    tinhSoNgay2( nambds, thangbds, ngaybds, giobds, phutbds, giaybds, namkts, thangkts, ngaykts, giokts, phutkts, giaykts );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, nam, thang, ngay );
        datePickerDialog.show();
    }
    //____________________________________________________________________________________________________________________//

    //... hàm lấy danh sách xe liên quan
    private void DatDiem() {
        final RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
        StringRequest stringRequest = new StringRequest( Request.Method.POST, urlserver_datdiemxe,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray( response );

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject op = jsonArray.getJSONObject( i );

                                txt_soghe.setText( op.getString( "socho" ) + "ghế" );
                                txt_nhienlieu.setText( op.getString( "nhienlieu" ) );
                                txt_truyendong.setText( op.getString( "truyendong" ) );
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pagrams = new HashMap<>();
                pagrams.put( "malaoi_xe", String.valueOf( 4 ) );
                return pagrams;
            }
        };
        requestQueue.add( stringRequest );
    }
    //____________________________________________________________________________________________________________________//

}
