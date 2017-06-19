package com.youyou.xiaofeibao.version2.home.city;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.request.EmptyRequestObject;
import com.youyou.xiaofeibao.version2.response.citylist.CityListResponseObject;
import com.youyou.xiaofeibao.version2.response.citylist.CityListResponsePro;
import com.youyou.xiaofeibao.version2.response.citylist.CityListResponseTranData;
import com.youyou.xiaofeibao.version2.response.citylist.CityResponseCities;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;

/**
 * 作者：young on 2016/12/23 09:10
 */

public class CityActivity extends BaseTitleActivity {
    public static int CITYRESULT = 111;
    private CityAdapter mAdapter;

    @ViewInject(R.id.list)
    PinnedSectionListView mListView;

    private TextView tv_cityname;

    @Override
    protected int getTitleText() {
        return R.string.city;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_citylist;
    }

    @Override
    protected void initData() {
        super.initData();
        getData();
        LinearLayout ll = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.v2_header_city, null);
        tv_cityname = (TextView) ll.findViewById(R.id.tv_cityname);
        mAdapter = new CityAdapter(mActivity);
        mListView.addHeaderView(ll);
        mListView.setAdapter(mAdapter);

        tv_cityname.setText(PreferencesConfig.v2_cityname.get());

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int po = position - 1;
                if (po <= 0) {
                    return;
                }
                CityListResponseTranData data = mAdapter.getData().get(po);//因为加了header

                if (data.getType() == data.ITEM) {
                    PreferencesConfig.v2_cityname.set(data.getObject().getCity());
                    PreferencesConfig.v2_citycode.set(data.getObject().getCityCode());
                    setResult(CITYRESULT);
                    finish();
                }
            }
        });

        tv_cityname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData() {
        ResponseBuilder<EmptyRequestObject, CityListResponseObject> builder = new ResponseBuilder<>(new EmptyRequestObject(), Config.PROVINCECITY,CityListResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<CityListResponseObject>() {
            @Override
            public void onSuccess(CityListResponseObject Object) {
                List<CityListResponseTranData> translist = new ArrayList<>();
                List<CityListResponsePro> data = Object.getData().getPcList();

                for (int i = 0; i < data.size(); i++) {
                    List<CityResponseCities> clist = data.get(i).getClist();
                    CityResponseCities time = new CityResponseCities();
                    time.setCity(data.get(i).getProvince());
                    translist.add(new CityListResponseTranData(CityListResponseTranData.SECTION, time));
                    for (int j = 0; j < clist.size(); j++) {
                        translist.add(new CityListResponseTranData(CityListResponseTranData.ITEM, clist.get(j)));
                    }
                }
                mAdapter.setData(translist);
            }
        }).send();
    }
}
