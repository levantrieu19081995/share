package com.phamvanthang.thuexehoi.Fragament;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.phamvanthang.thuexehoi.Adapter.Adapter_xe_trangchu;
import com.phamvanthang.thuexehoi.Connection.Class_bien_duongdan;
import com.phamvanthang.thuexehoi.Model.Xe_trangchu;
import com.phamvanthang.thuexehoi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class trangchu_Fragment extends Fragment {
    private RecyclerView rcv_xe_trangchu;
    private Adapter_xe_trangchu adapter_xe_trangchu;
    private List<Xe_trangchu> mXe_trangchu;
    AutoCompleteTextView auto_timkim;
    RelativeLayout cnt_thugon;
    //    private final String[] LoaiXe = new String[]{"Fortuner", "Mecedex", "Toyota", "honda", "hyndai", "Mxeasdasd", "Hábdakjsd"};
    List<String> LoaiXe = new ArrayList<>();
    Boolean nhan;
    String ok = "";

    ImageButton img_btn_tim;

    ImageButton imgbtn_xemthem, imgbtn_thugon;
    // biến đường dẫn tới server
    String url_dsXeTop5 = Class_bien_duongdan.ip_server + "/get_danhsachxetrangchuTop5.php";
    String url_dsXeDayDu = Class_bien_duongdan.ip_server + "/get_danhsachxetrangchuDayDu.php";
    String url_dsXeTimkiem = Class_bien_duongdan.ip_server + "/get_danhsachxe_timkiem.php";
    String url_ds_loaixe = Class_bien_duongdan.ip_server + "/get_danhsachloaixe.php";
    //___________________________________________________________________________________________//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate( R.layout.fragment_trangchu, container, false );
        Anhxa( view );

        mXe_trangchu = new ArrayList<>();
        lay_danhSachXetrangchu( url_dsXeTop5 );
        adapter_xe_trangchu = new Adapter_xe_trangchu( getContext(), mXe_trangchu );
        rcv_xe_trangchu.setAdapter( adapter_xe_trangchu );
        adapter_xe_trangchu.notifyDataSetChanged();

        //.. autocomplete
        ArrayAdapter<String> adapter = new ArrayAdapter<String>( getContext(), android.R.layout.simple_list_item_1, LoaiXe );
        auto_timkim.setAdapter( adapter );
        img_btn_tim.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println( "nhan: " + nhan );
                if (nhan == false) {
                    auto_timkim.setVisibility( View.VISIBLE );
                    if (auto_timkim.getText().toString().trim().equals( "" ) || auto_timkim.getText().toString().trim().equals( null )) {
                        lay_danhSachXetrangchu( url_dsXeTop5 );
                    } else {
                        lay_danhSachxe_TiemKiem( url_dsXeTimkiem, auto_timkim.getText().toString().trim() );
                    }
                }

            }
        } );
        imgbtn_xemthem.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_danhSachXetrangchu( url_dsXeDayDu );
                adapter_xe_trangchu = new Adapter_xe_trangchu( getContext(), mXe_trangchu );
                rcv_xe_trangchu.setAdapter( adapter_xe_trangchu );
                adapter_xe_trangchu.notifyDataSetChanged();
                cnt_thugon.setVisibility( View.VISIBLE );
                imgbtn_thugon.setVisibility( View.VISIBLE );
                imgbtn_xemthem.setVisibility( View.GONE );
            }
        } );
        imgbtn_thugon.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_danhSachXetrangchu( url_dsXeTop5 );
                adapter_xe_trangchu = new Adapter_xe_trangchu( getContext(), mXe_trangchu );
                rcv_xe_trangchu.setAdapter( adapter_xe_trangchu );
                adapter_xe_trangchu.notifyDataSetChanged();
                cnt_thugon.setVisibility( View.GONE );
                imgbtn_xemthem.setVisibility( View.VISIBLE );
            }
        } );

        return view;
    }

    // lấy danh sách xe
    private void lay_danhSachloaixe(String urlserver) {
        nhan = false;
        final RequestQueue requestQueue = Volley.newRequestQueue( getContext() );
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( Request.Method.GET, urlserver, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        LoaiXe.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject( i );
                                LoaiXe.add( jsonObject.getString( "tenhang" ) );
                                String mahangxe = jsonObject.getString( "mahangsx" );
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        adapter_xe_trangchu.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( getContext(), "trieu: " + error.toString(), Toast.LENGTH_SHORT ).show();
                        System.out.println( "trieu: " + error.toString() );
                    }
                } );
        requestQueue.add( jsonArrayRequest );
    }
    //___________________________________________________________________________________________//


    // lấy danh sách xe
    private void lay_danhSachXetrangchu(String urlserver) {
        final RequestQueue requestQueue = Volley.newRequestQueue( getContext() );
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( Request.Method.GET, urlserver, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mXe_trangchu.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject( i );
                                mXe_trangchu.add( new Xe_trangchu(
                                        jsonObject.getString( "img" ),
                                        jsonObject.getString( "loaixe" ),
                                        jsonObject.getString( "gia" ),
                                        jsonObject.getString( "ma_xe" ),
                                        jsonObject.getString( "ma_sx" )
                                ) );
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        adapter_xe_trangchu.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( getContext(), "trieu: " + error.toString(), Toast.LENGTH_SHORT ).show();
                        System.out.println( "trieu: " + error.toString() );
                    }
                } );
        requestQueue.add( jsonArrayRequest );
    }
    //___________________________________________________________________________________________//

    // lấy danh sách xe
    private void lay_danhSachxe_TiemKiem(String urlserver, final String loaixe) {
        RequestQueue requestQueue = Volley.newRequestQueue( getContext() );
        StringRequest stringRequest = new StringRequest( Request.Method.POST, urlserver,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mXe_trangchu.clear();
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray( response );

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject( i );
                                mXe_trangchu.add( new Xe_trangchu(
                                        jsonObject.getString( "img" ),
                                        jsonObject.getString( "loaixe" ),
                                        jsonObject.getString( "gia" ),
                                        jsonObject.getString( "ma_xe" ),
                                        jsonObject.getString( "ma_sx" )
                                ) );
                                System.out.println("\nloai xe : "+ jsonObject.getString( "loaixe" ));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter_xe_trangchu.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( getContext(), "Server đang gập sự cố...!", Toast.LENGTH_SHORT ).show();
                    }
                } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pagrams = new HashMap<>();
                pagrams.put( "loaixe", loaixe );

                return pagrams;
            }
        };
        requestQueue.add( stringRequest );
    }
    //___________________________________________________________________________________________//

    // hàm ánh xạ các view
    private void Anhxa(View view) {
        rcv_xe_trangchu = view.findViewById( R.id.rcv_xe_trangchu );
        rcv_xe_trangchu.setLayoutManager( new LinearLayoutManager( getContext() ) );
        rcv_xe_trangchu.setHasFixedSize( true );
        rcv_xe_trangchu.setFocusable( false );
        rcv_xe_trangchu.setNestedScrollingEnabled( false );
        auto_timkim = view.findViewById( R.id.auto_timkim );
        img_btn_tim = view.findViewById( R.id.img_btn_tim );
        imgbtn_xemthem = view.findViewById( R.id.imgbtn_xemthem );
        cnt_thugon = view.findViewById( R.id.cnt_thugon );
        imgbtn_thugon = view.findViewById( R.id.imgbtn_thugon );
        lay_danhSachloaixe( url_ds_loaixe );
    }
    //___________________________________________________________________________________________//

}
