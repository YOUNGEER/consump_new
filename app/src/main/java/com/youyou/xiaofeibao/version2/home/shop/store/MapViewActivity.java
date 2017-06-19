package com.youyou.xiaofeibao.version2.home.shop.store;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
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
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiAddrInfo;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.youyou.xiaofeibao.ConsumApplication;
import com.youyou.xiaofeibao.common.LocationHelper;
import com.youyou.xiaofeibao.framework.Log.LogDoumee;
import com.youyou.xiaofeibao.framework.Log.LogTag;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.view.PageIndexView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.R;


import java.util.List;


/**
 *
 */
public class MapViewActivity extends BaseTitleActivity{
    public static final int RESULT_CODE = 1;
    public static final String ADDRESS = "address";
    public static final String REMARKS = "remarks";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String LATITUDE = "LATITUDE";

    @ViewInject(R.id.mapView)
    MapView mapView;
    @ViewInject(R.id.tv_city_mapView)
    TextView tv_addr;
    @ViewInject(R.id.tv_lat)
    TextView tv_lat;
    @ViewInject(R.id.tv_long)
    TextView tv_long;
    @ViewInject(R.id.tv_sure)
    TextView tv_sure;

    private BDLocationListener mLocationListener;
    private BaiduMap mBaiduMap;
    private GeoCoder mgeoCode;
    private OnGetGeoCoderResultListener onGeoListener;
    private Marker marker;
    private LocationHelper.LocationSucessListener locationSucessListener;

    private String latitude="";
    private String longitude="";

    @Override
    protected int getTitleText() {
        return R.string.text_locationAddress_collectionBusiness;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mapview;
    }

    @Override
    protected void initView() {
        super.initView();

        mBaiduMap = mapView.getMap();
        mgeoCode = GeoCoder.newInstance();
    }


    @Override
    protected void setListener() {
        super.setListener();

        LocationHelper.getInstance().startLocationAutoClose();

        locationSucessListener = new LocationHelper.LocationSucessListener() {
            @Override
            public void onLocation(BDLocation bdLocation) {
                LatLng point = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                setOverly(point);
                changCenterPoint1(point);
                tv_addr.setText(bdLocation.getAddrStr());
                tv_lat.setText("经度："+bdLocation.getLatitude());
                tv_long.setText("维度："+bdLocation.getLongitude());
                latitude=bdLocation.getLatitude()+"";
                longitude=bdLocation.getLongitude()+"";
            }
        };
        LocationHelper.getInstance().addListener(locationSucessListener);
        //逆地理的回调

        onGeoListener = new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {

                changCenterPoint(result.getLocation());
                tv_addr.setText(result.getAddress());
                tv_lat.setText("经度："+result.getLocation().latitude);
                tv_long.setText("维度："+result.getLocation().longitude);
                latitude=result.getLocation().latitude+"";
                longitude=result.getLocation().longitude+"";
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

                changCenterPoint(result.getLocation());
                tv_addr.setText(result.getAddress());
                tv_lat.setText("经度："+result.getLocation().latitude);
                tv_long.setText("维度："+result.getLocation().longitude);
                latitude=result.getLocation().latitude+"";
                longitude=result.getLocation().longitude+"";
            }
        };

        mgeoCode.setOnGetGeoCodeResultListener(onGeoListener);

        //百度map点击的回调
        mBaiduMap.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        setOverly(mBaiduMap.getMapStatus().target);
                        mgeoCode.reverseGeoCode(
                                new ReverseGeoCodeOption().location
                                        (mBaiduMap.getMapStatus().target));
                        break;
                }
            }
        });

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra(LATITUDE,latitude);
                intent.putExtra(LONGITUDE,longitude);
                setResult(1,intent);
                finish();
            }
        });
    }



    @Override
    protected void initData() {
        super.initData();
    }

    private void changCenterPoint1(LatLng point) {
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(point)
                .zoom(15)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);

    }

    private void changCenterPoint(LatLng point) {
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(point)
                .zoom(mBaiduMap.getMapStatus().zoom)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);

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
        //在地图上添加Marker，并显示

        marker = (Marker) mBaiduMap.addOverlay(option);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
    }
}
