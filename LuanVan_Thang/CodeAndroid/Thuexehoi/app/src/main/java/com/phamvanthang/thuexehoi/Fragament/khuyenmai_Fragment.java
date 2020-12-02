package com.phamvanthang.thuexehoi.Fragament;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.phamvanthang.thuexehoi.Adapter.Adapter_xe_khuyenmai;
import com.phamvanthang.thuexehoi.Adapter.Adapter_xe_trangchu;
import com.phamvanthang.thuexehoi.Connection.Class_bien_duongdan;
import com.phamvanthang.thuexehoi.Model.Xe_khuyenmai;
import com.phamvanthang.thuexehoi.Model.Xe_trangchu;
import com.phamvanthang.thuexehoi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class khuyenmai_Fragment extends Fragment {
    private RecyclerView rcv_thongtinkhuyenmai;

    private Adapter_xe_khuyenmai adapter_xe_khuyenmai;
    private List<Xe_khuyenmai> mXe_khuyenmai;
    RelativeLayout cnt_thugon;
    ImageButton imgbtn_xemthem, imgbtn_thugon;


    // biến đường dẫn tới server
    String url_XekmTop5 = Class_bien_duongdan.ip_server+"/get_danhsachxekhuyenmaiTop5.php";

    String url_dsXekmDayDu = Class_bien_duongdan.ip_server + "/get_danhsachxekhuyenmaiDayDu.php";
    //___________________________________________________________________________________________//


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_khuyenmai, container, false );
        Anhxa(view);

        mXe_khuyenmai = new ArrayList<>();
        lay_danhSachXekhuyenmai(url_XekmTop5);
        adapter_xe_khuyenmai = new Adapter_xe_khuyenmai(getContext(),mXe_khuyenmai);
        rcv_thongtinkhuyenmai.setAdapter(adapter_xe_khuyenmai);
        adapter_xe_khuyenmai.notifyDataSetChanged();

        imgbtn_xemthem.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_danhSachXekhuyenmai( url_dsXekmDayDu );
                adapter_xe_khuyenmai = new Adapter_xe_khuyenmai( getContext(), mXe_khuyenmai );
                rcv_thongtinkhuyenmai.setAdapter( adapter_xe_khuyenmai );
                adapter_xe_khuyenmai.notifyDataSetChanged();
                cnt_thugon.setVisibility( View.VISIBLE );
                imgbtn_thugon.setVisibility( View.VISIBLE );
                imgbtn_xemthem.setVisibility( View.GONE );
            }
        } );
        imgbtn_thugon.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_danhSachXekhuyenmai( url_XekmTop5 );
                adapter_xe_khuyenmai = new Adapter_xe_khuyenmai( getContext(), mXe_khuyenmai );
                rcv_thongtinkhuyenmai.setAdapter( adapter_xe_khuyenmai );
                adapter_xe_khuyenmai.notifyDataSetChanged();
                cnt_thugon.setVisibility( View.GONE );
            }
        } );

        return view;
    }
    //___________________________________________________________________________________________//
    // hàm ánh xạ các view
    private void Anhxa(View view) {
        rcv_thongtinkhuyenmai = view.findViewById( R.id.rcv_thongtinkhuyenmai );
        rcv_thongtinkhuyenmai.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_thongtinkhuyenmai.setHasFixedSize(true);
        rcv_thongtinkhuyenmai.setFocusable(false);
        rcv_thongtinkhuyenmai.setNestedScrollingEnabled(false);
        imgbtn_xemthem = view.findViewById( R.id.imgbtn_xemthem );
        // floatingButtom = view.findViewById( R.id.floatingButtom );
        cnt_thugon = view.findViewById( R.id.cnt_thugon );
        imgbtn_thugon = view.findViewById( R.id.imgbtn_thugon );

    }
    //___________________________________________________________________________________________//

    // lấy danh sách xe
    private void lay_danhSachXekhuyenmai(String urlserver) {
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( Request.Method.GET, urlserver, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        mXe_khuyenmai.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                mXe_khuyenmai.add(new Xe_khuyenmai(
                                        jsonObject.getString("img"),
                                        jsonObject.getString("loaixe"),
                                        jsonObject.getString("gia"),
                                        jsonObject.getString("phantram_km"),
                                        jsonObject.getString("ma_xe"),
                                        jsonObject.getString("ma_sx")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        adapter_xe_khuyenmai.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "trieu: " + error.toString(), Toast.LENGTH_SHORT).show();
                        System.out.println("trieu: " + error.toString());
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

}
