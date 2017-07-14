package com.youyou.xiaofeibao.version2.alliance;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseFragment;
import com.youyou.xiaofeibao.framework.net.BaseNetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.framework.preferences.PreferencesConfig;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.alliance.adapter.CustomSelectAdapter;
import com.youyou.xiaofeibao.version2.alliance.adapter.CustomSelectBean;
import com.youyou.xiaofeibao.version2.alliance.search.SearchResultActivity;
import com.youyou.xiaofeibao.version2.home.hot.SubscribeActivity;
import com.youyou.xiaofeibao.version2.request.BaseRequestParam;
import com.youyou.xiaofeibao.version2.request.district.DistrictRequestObject;
import com.youyou.xiaofeibao.version2.request.district.DistrictRequestParam;
import com.youyou.xiaofeibao.version2.request.shoplist.ShopListRequestObject;
import com.youyou.xiaofeibao.version2.request.shoplist.ShopListRequestParam;
import com.youyou.xiaofeibao.version2.response.district.DistrictResponseList;
import com.youyou.xiaofeibao.version2.response.district.DistrictResponseObject;
import com.youyou.xiaofeibao.version2.response.shopcategory.ShopCategoryResponseObject;
import com.youyou.xiaofeibao.version2.response.shoplist.ShopListResponseObject;
import com.youyou.xiaofeibao.version2.response.shoplist.ShopListResponseParam;
import com.youyou.xiaofeibao.version2.tool.Progress;
import com.youyou.xiaofeibao.view.ScreenUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：young on 2016/10/4 09:03
 */

public class AllicanceFragment extends BaseFragment implements View.OnClickListener {
    @ViewInject(R.id.view_shadow)
    TextView view_shadow;
    @ViewInject(R.id.tv_category)
    TextView tv_category;
    @ViewInject(R.id.tv_paixu)
    TextView tv_paixu;
    @ViewInject(R.id.tv_shaixuan)
    TextView tv_shaixuan;
    @ViewInject(R.id.tv_nodata)
    TextView tv_nodata;
    @ViewInject(R.id.iv_img1)
    ImageView   iv_img1;
    @ViewInject(R.id.iv_img2)
    ImageView   iv_img2;
    @ViewInject(R.id.iv_img3)
    ImageView   iv_img3;
    @ViewInject(R.id.tv_search)
    TextView tv_search;

    @ViewInject(R.id.listview)
    PullToRefreshListView pr_listview;

    private ShopDataAdapter mshopAdapter;

    private CustomSelectAdapter adapter;

    private List<CustomSelectBean> districList = new ArrayList<>();
    private List<CustomSelectBean> categoryList = new ArrayList<>();
    private List<CustomSelectBean> sortList = new ArrayList<>();

    private String distirctid = PreferencesConfig.v2_citycode.get();
    private String distirctname = PreferencesConfig.v2_cityname.get();
    private String categoryid = "";
    private String categoryname = "全部";
    private String sortId = "0";
    private String sortName = "筛选";

    private int delte;
//    private Drawable drawableSelect;
//    private Drawable drawableUnselect;

    private View categoryPopupView;
    private PopupWindow popupWindow;//前三个筛选条件的pop
    private ListView listView;

    private int page = 1;

    @Override
    protected void initData() {
        adapter = new CustomSelectAdapter(getActivity(), R.layout.list_select_pop);
        mshopAdapter = new ShopDataAdapter(getActivity(), R.layout.v2_listview_shoplist);

        pr_listview.setAdapter(mshopAdapter);

        pr_listview.setMode(PullToRefreshBase.Mode.BOTH);

        delte = ScreenUtils.dp2px(getActivity(), 1);

        categoryPopupView = View.inflate(getActivity(), R.layout.pop_select_money, null);
        popupWindow = new PopupWindow(categoryPopupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        listView = (ListView) categoryPopupView.findViewById(R.id.listview_pop);
        adapter = new CustomSelectAdapter(getActivity(), R.layout.list_select_pop);
        listView.setAdapter(adapter);

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(false);

        getDistrictData();
        getCategoryData();

        tv_category.setText(distirctname);
        tv_paixu.setText(categoryname);
        tv_shaixuan.setText(sortName);

        sortList.add(new CustomSelectBean("", "筛选"));
        sortList.add(new CustomSelectBean("0", "距离由近到远"));
        sortList.add(new CustomSelectBean("1", "评分由高到低"));

        pr_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SubscribeActivity.class);
                intent.setAction(mshopAdapter.getList().get(position - 1).getMemid());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void setListener() {
        tv_category.setOnClickListener(this);
        tv_paixu.setOnClickListener(this);
        tv_shaixuan.setOnClickListener(this);

        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchResultActivity.class));
            }
        });

        pr_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                getLoanListData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                getLoanListData();
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.v2_activity_category;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_category:
                showMoneyPopupWindows(0);
                break;
            case R.id.tv_paixu:
                showMoneyPopupWindows(1);
                break;
            case R.id.tv_shaixuan:
                showMoneyPopupWindows(2);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void showMoneyPopupWindows(final int type) {


        switch (type) {

            case 0:
                adapter.setList(districList, tv_category.getText().toString().trim());
                popupWindow.showAsDropDown(tv_category, 0, 0);
//                tv_category.setCompoundDrawables(null, null, drawableSelect, null);
                iv_img1.setBackground(getResources().getDrawable(R.drawable.up_sj));
                view_shadow.setVisibility(View.VISIBLE);
                break;
            case 1:
                adapter.setList(categoryList, tv_paixu.getText().toString().trim());
                popupWindow.showAsDropDown(tv_category, 0, 0);
//                tv_paixu.setCompoundDrawables(null, null, drawableSelect, null);
                iv_img2.setBackground(getResources().getDrawable(R.drawable.up_sj));
                view_shadow.setVisibility(View.VISIBLE);
                break;
            case 2:
                adapter.setList(sortList, tv_shaixuan.getText().toString().trim());
                popupWindow.showAsDropDown(tv_shaixuan, 0, 0);
//                tv_shaixuan.setCompoundDrawables(null, null, drawableSelect, null);
                iv_img3.setBackground(getResources().getDrawable(R.drawable.up_sj));
                view_shadow.setVisibility(View.VISIBLE);

                break;
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewItem, int position, long id) {

                CustomSelectBean bean = adapter.getList().get(position);
                switch (type) {
                    case 0:
                        tv_category.setText(bean.getName());
                        tv_category.setTextColor(getResources().getColor(R.color.had_select));
                        distirctid = bean.getCategoryId();
                        break;
                    case 1:
                        tv_paixu.setText(bean.getName());
                        tv_paixu.setTextColor(getResources().getColor(R.color.had_select));
                        categoryid = bean.getCategoryId();
                        break;
                    case 2:
                        tv_shaixuan.setText(bean.getName());
                        tv_shaixuan.setTextColor(getResources().getColor(R.color.had_select));
                        sortId = bean.getCategoryId();
                        break;
                }
                page = 1;
                getLoanListData();
                popupWindow.dismiss();
            }
        });


        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                switch (type) {
                    case 0:
//                        tv_category.setCompoundDrawables(null, null, drawableUnselect, null);
                        iv_img1.setBackground(getResources().getDrawable(R.drawable.down_sj));
                        view_shadow.setVisibility(View.GONE);
                        break;
                    case 1:
//                        tv_paixu.setCompoundDrawables(null, null, drawableUnselect, null);
                        iv_img2.setBackground(getResources().getDrawable(R.drawable.down_sj));
                        view_shadow.setVisibility(View.GONE);
                        break;
                    case 2:
//                        tv_shaixuan.setCompoundDrawables(null, null, drawableUnselect, null);
                        iv_img3.setBackground(getResources().getDrawable(R.drawable.down_sj));
                        view_shadow.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }


    private void getLoanListData() {
        if(getActivity()==null){
            return;

        }

        final android.app.Dialog dialog= Progress.createLoadingDialog(getActivity(),"");

        dialog.show();

        ShopListRequestObject requestObject = new ShopListRequestObject();
        ShopListRequestParam param = new ShopListRequestParam();
        param.setShopDistrictId(distirctid);
        param.setCategoryId(categoryid);
        param.setLatitude(PreferencesConfig.v2_lat.get());
        param.setLongitude(PreferencesConfig.v2_longt.get());
        param.setSorting(sortId);
        param.setPageNum(page + "");
        param.setPageOffest("10");
        requestObject.setParam(param);

        ResponseBuilder<ShopListRequestObject, ShopListResponseObject> builder=new ResponseBuilder<>(requestObject, Config.SHOPLIST,ShopListResponseObject.class);

        builder.setCallBack(new BaseNetCallBack<ShopListResponseObject>() {

            @Override
            public void onNetFailure(String str) {
                super.onNetFailure(str);
                if (pr_listview.isRefreshing()) {
                    pr_listview.onRefreshComplete();
                }
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
            }

            @Override
            public void onServerFailure(String str) {
                super.onServerFailure(str);
                if (pr_listview.isRefreshing()) {
                    pr_listview.onRefreshComplete();
                }
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(ShopListResponseObject shopListResponseObject) {
                super.onFailure(shopListResponseObject);
                if (pr_listview.isRefreshing()) {
                    pr_listview.onRefreshComplete();
                }
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
            }

            @Override
            public void onSuccess(ShopListResponseObject Object) {
                if (pr_listview.isRefreshing()) {
                    pr_listview.onRefreshComplete();
                }
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                List<ShopListResponseParam> shoplist = Object.getData().getShoplist();

                if (page == 1) {
                    mshopAdapter.setList(shoplist);
                    if (shoplist.size() == 0) {
                        tv_nodata.setVisibility(View.VISIBLE);
                    } else {
                        page++;
                        tv_nodata.setVisibility(View.GONE);
                    }
                } else {
                    if (shoplist.size() != 0) {
                        page++;
                    }
                    mshopAdapter.addList(shoplist);
                }
            }
        }).send();
    }

    //获取区域信息
    public void getDistrictData() {
        DistrictRequestObject requestObject = new DistrictRequestObject();
        DistrictRequestParam param = new DistrictRequestParam();
        param.setCityCode(PreferencesConfig.v2_citycode.get());
        requestObject.setParam(param);
        ResponseBuilder<DistrictRequestObject, DistrictResponseObject> builder = new ResponseBuilder<>(requestObject, Config.DISTRICT,DistrictResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<DistrictResponseObject>() {
            @Override
            public void onSuccess(DistrictResponseObject reponse) {
                distirctname = PreferencesConfig.v2_cityname.get();
                distirctid=PreferencesConfig.v2_citycode.get();
                if(tv_category!=null){
                    tv_category.setText(distirctname);
                }
                page = 1;
                getLoanListData();
                districList.clear();
                districList.add(new CustomSelectBean(PreferencesConfig.v2_citycode.get(), PreferencesConfig.v2_cityname.get()));

                List<DistrictResponseList> lists = reponse.getData().getDistricts();
                for (DistrictResponseList list : lists) {
                    districList.add(new CustomSelectBean(list.getDistrictId(), list.getName()));
                }
            }
        }).send();
    }

    //获取类别信息
    private void getCategoryData() {

        ResponseBuilder<BaseRequestParam, ShopCategoryResponseObject> builder =
                new ResponseBuilder<>(new BaseRequestParam(), Config.CATEGORY,ShopCategoryResponseObject.class);
        builder.setCallBack(new BaseNetCallBack<ShopCategoryResponseObject>() {
            @Override
            public void onSuccess(ShopCategoryResponseObject reponse) {
                categoryList.clear();
                categoryList.add(new CustomSelectBean("", "全部"));
                categoryList.addAll(reponse.getData().getCategorys());
            }
        }).send();
    }

}
