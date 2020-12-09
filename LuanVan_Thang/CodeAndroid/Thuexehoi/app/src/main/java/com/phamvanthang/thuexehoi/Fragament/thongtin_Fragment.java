package com.phamvanthang.thuexehoi.Fragament;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.phamvanthang.thuexehoi.Activity_main.CapnhatThongTin;
import com.phamvanthang.thuexehoi.Activity_main.Dangky;
import com.phamvanthang.thuexehoi.Adapter.Adapter_xe_dathue;
import com.phamvanthang.thuexehoi.Adapter.Adapter_xe_trangchu;
import com.phamvanthang.thuexehoi.Connection.Class_bien_duongdan;
import com.phamvanthang.thuexehoi.Model.Xe_dathue;
import com.phamvanthang.thuexehoi.Model.Xe_trangchu;
import com.phamvanthang.thuexehoi.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class thongtin_Fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    Button btn_login, btn_dangxuat;
    TextView txt_dangky,txt_capnhat_tt;
    LinearLayout ln_noidungthongtin, ln_dangnhap;
    Dialog dialog_dangxuat;
    Button btn_huy_dangxuat, btn_dongy_dangxuat;

    SwipeRefreshLayout setOnRefreshListener;

    TextView txt_tennguoidung,txt_giaypheplx_tt,txt_email_tt,txt_sdt_tt,txt_diachi_tt,txt_cmnd_tt,txt_loaikh_tt;
    ImageView image_nguoidung;

    MaterialEditText dangnhap_mk, dangnhap_sdt;
    String user_chinh, pass_chinh, Ketquatruyvan, MaTk;
    boolean nhapvao = false;
    SharedPreferences sharedPreferences_dangnhap,sharedPreferences_thongtinnguoidung;
    Boolean dangnhap = false;

    String ma_tk_DaDangNhap_sharedPreferences,makh_DaDangNhap_sharedPreferences;
    private RecyclerView rcv_danhsachthongtinxe_thue;
    private Adapter_xe_dathue adapter_xe_dathue;
    private List<Xe_dathue> mXe_dathue;

    // biến đường dẫn tới server
    String url_xedathue= Class_bien_duongdan.ip_server + "/get_danhsachxedathue.php";
    String url_dangnhap = Class_bien_duongdan.ip_server + "/dangnhap.php";
    String url_laythongtinnguoidung = Class_bien_duongdan.ip_server + "/get_thongtinnguoidung.php";

    //___________________________________________________________________________________________//


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_thongtin, container, false );
        anhxa( view );
        sharedPreferences_dangnhap = getContext().getSharedPreferences( "dataLogin", getContext().MODE_PRIVATE );
        sharedPreferences_thongtinnguoidung = getContext().getSharedPreferences( "dataUser", getContext().MODE_PRIVATE );
        lay_ThongTin_tuTaiKhoan();

        //..
        btn_login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // kiểm tra mật khẫu đúng không
                if (kiemTraThongTinNhap()) {
                    dangnhap( user_chinh, pass_chinh );
                } else {
                    Toast.makeText( getContext(), "Vui lòng kiểm tra thông tin nhập vào..!", Toast.LENGTH_SHORT ).show();
                }
            }
        } );

        //..
        txt_dangky.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getActivity(), Dangky.class ) );
            }
        } );
        //..
        txt_capnhat_tt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makh_DaDangNhap_sharedPreferences = sharedPreferences_thongtinnguoidung.getString( "makh", "" );
                Intent i = new Intent( getContext(), CapnhatThongTin.class );
                i.putExtra( "hoten", txt_tennguoidung.getText().toString());
                i.putExtra( "giaypheplx", txt_giaypheplx_tt.getText().toString().trim());
                i.putExtra( "diachi", txt_diachi_tt.getText().toString());
                i.putExtra( "cmnd", txt_cmnd_tt.getText().toString());
                i.putExtra( "makh", makh_DaDangNhap_sharedPreferences);
                startActivity( i );
            }
        } );
        //..
        btn_dangxuat.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_dangxuat.show();
                XuLynoidung_dialog_dangxuat();
            }
        } );

        setOnRefreshListener = view.findViewById( R.id.refresh_fragment );
        setOnRefreshListener.setOnRefreshListener(this);
        setOnRefreshListener.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        return view;
    }
    //___________________________________________________________________________________________//

    //.. ham anh xa
    private void anhxa(View view) {
        btn_login = view.findViewById( R.id.btn_login );
        btn_dangxuat = view.findViewById( R.id.btn_dangxuat );
        txt_dangky = view.findViewById( R.id.txt_dangky );
        txt_capnhat_tt = view.findViewById( R.id.txt_capnhat_tt );
        ln_noidungthongtin = view.findViewById( R.id.ln_noidungthongtin );
        ln_dangnhap = view.findViewById( R.id.ln_dangnhapthongtin );



        dangnhap_mk = view.findViewById( R.id.dangnhap_mk );
        dangnhap_sdt = view.findViewById( R.id.dangnhap_sdt );

        rcv_danhsachthongtinxe_thue = view.findViewById( R.id.rcv_danhsachthongtinxe_thue );
        rcv_danhsachthongtinxe_thue.setLayoutManager( new LinearLayoutManager( getContext() ) );
        rcv_danhsachthongtinxe_thue.setHasFixedSize( true );
        rcv_danhsachthongtinxe_thue.setFocusable( false );
        rcv_danhsachthongtinxe_thue.setNestedScrollingEnabled( false );

        dialog_dangxuat =new Dialog(getActivity(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog_dangxuat.setContentView(R.layout.b_dialog_xacnhan_dangxuat);

        btn_huy_dangxuat = dialog_dangxuat.findViewById( R.id.btn_huy_dangxuat );
        btn_dongy_dangxuat = dialog_dangxuat.findViewById( R.id.btn_dongy_dangxuat );

        image_nguoidung = view.findViewById( R.id.image_nguoidung );
        txt_tennguoidung = view.findViewById( R.id.txt_tennguoidung );
        txt_giaypheplx_tt = view.findViewById( R.id.txt_giaypheplx_tt );
        txt_email_tt = view.findViewById( R.id.txt_email_tt );
        txt_sdt_tt = view.findViewById( R.id.txt_sdt_tt );
        txt_diachi_tt = view.findViewById( R.id.txt_diachi_tt );
        txt_cmnd_tt = view.findViewById( R.id.txt_cmnd_tt );
        txt_loaikh_tt = view.findViewById( R.id.txt_loaikh_tt );
        mXe_dathue = new ArrayList<>();
    }
    //___________________________________________________________________________________________//

    // hàm xữ lý công việc trong dialog đăng xuất
    private void XuLynoidung_dialog_dangxuat() {
        //ham set buttom huy
        btn_huy_dangxuat.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_dangxuat.cancel();
            }
        } );
        //ham set buttom dangnhap
        btn_dongy_dangxuat.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dangnhap == true) {
                    SharedPreferences.Editor editor = sharedPreferences_dangnhap.edit();
                    editor.remove( "taikhoan" );
                    editor.remove( "matkhau" );
                    editor.remove( "ma_tk" );
                    editor.remove( "dangnhap" );
                    editor.commit();
                    setUserVisibleHint( true );
                    Snackbar.make( getView(), "Ban đã đăng xuất thành công !", Snackbar.LENGTH_SHORT ).show();
                    ln_dangnhap.setVisibility( View.VISIBLE );
                    ln_noidungthongtin.setVisibility( View.GONE );
                    dialog_dangxuat.cancel();
                }
            }
        } );
    }
    //__________________________________________________________________________________________________________________________________________//

    //... hàm hiển thị danh sách lay_danhsach_thongtinxedathue
    private void lay_danhsach_thongtinxedathue(String urlserver, final String ma_khs) {

        final RequestQueue requestQueue = Volley.newRequestQueue( getContext() );
        StringRequest stringRequest = new StringRequest( Request.Method.POST, urlserver,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mXe_dathue.clear();
                        try {
                            JSONArray jsonArray = new JSONArray( response );
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject op = jsonArray.getJSONObject( i );
                                mXe_dathue.add( new Xe_dathue(
                                        op.getString( "img" ),
                                        op.getString( "tenxe" ),
                                        op.getString( "tonggia" )
                                ) );
                                System.out.println("Thong tin xe da thue: " +op.getString( "img" )+ op.getString( "tenxe" )+op.getString( "tonggia" ));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter_xe_dathue.notifyDataSetChanged();
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
                pagrams.put( "ma_kh", ma_khs );
                return pagrams;
            }
        };
        requestQueue.add( stringRequest );
    }
    //___________________________________________________________________________________________//

    //.. hàm đăng nhập
    private void dangnhap(final String users, final String passs) {
        RequestQueue requestQueue = Volley.newRequestQueue( getActivity() );
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

                                ln_dangnhap.setVisibility( View.GONE );
                                ln_noidungthongtin.setVisibility( View.VISIBLE );
                                setUserVisibleHint( true );
//                                Toast.makeText( getContext(), "Đăng nhập thành công !", Toast.LENGTH_SHORT ).show();
                                Snackbar.make( getView(), "Đăng nhập thành công !", Snackbar.LENGTH_SHORT ).show();
                            }
                            if (Ketquatruyvan.trim().equals( "thatbai" )) {
                                System.out.println( "______________________trieu: " + "Đăng nhập KHÔNG thành công..!" );

                                ln_dangnhap.setVisibility( View.VISIBLE );
                                ln_noidungthongtin.setVisibility( View.GONE );
                                Snackbar.make( getView(), "Đăng nhập thất bại!", Snackbar.LENGTH_SHORT ).show();
                            }
//                            System.out.println( Ketquatruyvan + '-' + Hoten + '-' + Hoten + '-' + Hinh );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make( getView(), "Server đang gập sự cố...!", Snackbar.LENGTH_SHORT ).show();
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

    // hàm lấy kiểm tra nhập đầy đủ không khi nhập thông tin đăng nhập
    private boolean kiemTraThongTinNhap() {
        String user = dangnhap_sdt.getText().toString().trim();
        String pass = dangnhap_mk.getText().toString().trim();
        if (user.isEmpty()) {
            dangnhap_sdt.setError( "Vui lòng nhập đúng số điện thoại !" );
            dangnhap_sdt.setFocusable( true );
            nhapvao = false;
            return nhapvao;
        } else if (pass.isEmpty()) {
            dangnhap_mk.setError( "Vui lòng nhập mật khẩu !" );
            dangnhap_mk.setFocusable( true );
            nhapvao = false;
            return nhapvao;
        } else {
            nhapvao = true;
            user_chinh = dangnhap_sdt.getText().toString().trim();
            pass_chinh = dangnhap_mk.getText().toString().trim();
        }
        return nhapvao;
    }
    //__________________________________________________________________________________________________________________________________________//

    // hàm refesh lai fragment
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint( isVisibleToUser );
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach( this ).attach( this ).commit();
        }
    }
    //__________________________________________________________________________________________________________________________________________//

    //.. hàm xủ lý để lấy thông tin khi đã đăng nhập
    private void lay_ThongTin_tuTaiKhoan() {
        dangnhap = sharedPreferences_dangnhap.getBoolean( "dangnhap", false );
        if (dangnhap == true) {
            ma_tk_DaDangNhap_sharedPreferences = sharedPreferences_dangnhap.getString( "ma_tk", "" );
            makh_DaDangNhap_sharedPreferences = sharedPreferences_thongtinnguoidung.getString( "makh", "" );
            //...
            ln_dangnhap.setVisibility( View.GONE );
            ln_noidungthongtin.setVisibility( View.VISIBLE );
            LayThongTinCaNhan( ma_tk_DaDangNhap_sharedPreferences );

            System.out.println("Ma khách hàng: "+makh_DaDangNhap_sharedPreferences);
            lay_danhsach_thongtinxedathue( url_xedathue,makh_DaDangNhap_sharedPreferences );
            adapter_xe_dathue = new Adapter_xe_dathue( getContext(), mXe_dathue );
            rcv_danhsachthongtinxe_thue.setAdapter( adapter_xe_dathue );
            adapter_xe_dathue.notifyDataSetChanged();
        }
    }
    //__________________________________________________________________________________________________________________________________________//

      // hàm lây thông tin người dùng đã đăng nhập
    private void LayThongTinCaNhan(final String maTK_DaDangNhap_sharedPreferences) {
        RequestQueue requestQueue = Volley.newRequestQueue( getActivity() );
        StringRequest stringRequest = new StringRequest( Request.Method.POST, url_laythongtinnguoidung,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray( response );
                            JSONObject jsonObject = jsonArray.getJSONObject( 0 );
                            String makh = jsonObject.getString( "makh" );
                            String stk = jsonObject.getString( "stk" );
                            txt_tennguoidung.setText( jsonObject.getString( "ten" ) );
                            String giayphep_lx = jsonObject.getString( "gplx" );
                            txt_email_tt .setText(jsonObject.getString( "email" ));
                            txt_sdt_tt .setText(jsonObject.getString( "sdt" ));
                            String dichi = jsonObject.getString( "diachi" );
                            String cmnd = jsonObject.getString( "cmnd" );
                            String mlkh=jsonObject.getString( "maloaikh" );
                            String Hinh = jsonObject.getString( "Hinh" );

                            if (giayphep_lx.equals( "null" ) || giayphep_lx.equals( "" )) {
                                txt_giaypheplx_tt .setText("");
                            } else {
                                txt_giaypheplx_tt .setText(jsonObject.getString( "gplx" ));
                            }

                            if (dichi.equals( "null" ) || dichi.equals( "" )) {
                                txt_diachi_tt .setText("");
                            } else {
                                txt_diachi_tt .setText(jsonObject.getString( "diachi" ));
                            }
                            if (cmnd.equals( "null" ) || cmnd.equals( "" )) {
                                txt_cmnd_tt .setText("");
                            } else {
                                txt_cmnd_tt .setText(jsonObject.getString( "cmnd" ));
                            }

                            if (mlkh.equals( "null" ) || mlkh.equals( "" )) {
                                txt_loaikh_tt.setText( "Thuê xe" );
                            } else {
                                txt_loaikh_tt.setText(mlkh);
                            }

                            if (Hinh.equals( "" )|| Hinh.equals( null )|| Hinh.equals( "null" )) {
                                image_nguoidung.setImageResource( R.drawable.noimage2 );
                            } else {
                                Glide.with( getContext() ).load( Class_bien_duongdan.ip_server + Hinh.trim() ).into( image_nguoidung );
                            }

                            // lưu vào bộ nhớ tạm thời các thông tin cần kiểm tra lần mở app sau
                            SharedPreferences.Editor editor = sharedPreferences_thongtinnguoidung.edit();
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
                        Snackbar.make( getView(), "Server đang gập sự cố...!", Snackbar.LENGTH_SHORT ).show();
                    }
                } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pagrams = new HashMap<>();
                pagrams.put( "mstk_ss", maTK_DaDangNhap_sharedPreferences.trim() );
                return pagrams;
            }
        };
        requestQueue.add( stringRequest );
    }

    @Override
    public void onRefresh() {
        setOnRefreshListener.setRefreshing(false);
        lay_ThongTin_tuTaiKhoan();
        System.out.println("___________________cc8");
    }



    //__________________________________________________________________________________________________________________________________________//
}
