package com.youyou.xiaofeibao.version2.home;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 作者：young on 2016/12/30 17:13
 */

public class MapViewShowAddrActivity extends BaseTitleActivity {
    private Double latitude=0.00;
    private Double longitude=0.00;
    private String addrs="";
    private String shopname="";

    @ViewInject(R.id.mv_baidumap)
    MapView mv_baidumap;
    @ViewInject(R.id.tv_togo)
    TextView tv_togo;

    private BaiduMap mBaiduMap;

    private Marker marker;
    // 定位相关
    LocationClient mLocClient;

    @Override
    protected int getTitleText() {
        return R.string.app_name;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_viewaddr;
    }

    @Override
    protected void initData() {
        super.initData();
        latitude=Double.parseDouble(getIntent().getStringExtra("lat"));
        longitude=Double.parseDouble(getIntent().getStringExtra("lon"));
        addrs=getIntent().getStringExtra("addr");
        shopname=getIntent().getStringExtra("name");

        setTitle(shopname);
        mBaiduMap=mv_baidumap.getMap();

        LatLng point = new LatLng(latitude, longitude);
        setOverly(point);

        tv_togo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGo();
            }
        });
    }

    private void setOverly(LatLng mPoint) {

        if (marker != null) {
            marker.remove();
        }
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.location);
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(mPoint)
                .icon(bitmap)
                .zIndex(9)  //设置marker所在层级
                .draggable(true);  //设置手势拖拽;
        marker = (Marker) mBaiduMap.addOverlay(option);

        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(mPoint)
                .zoom(15)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);

    }

    private void toGo(){
        Uri mUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + addrs);
        Intent mIntent = new Intent(Intent.ACTION_VIEW, mUri);
        startActivity(mIntent);
    }
}
