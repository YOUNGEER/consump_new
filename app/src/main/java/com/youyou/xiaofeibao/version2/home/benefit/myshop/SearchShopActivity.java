package com.youyou.xiaofeibao.version2.home.benefit.myshop;

import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.net.NetCallBack;
import com.youyou.xiaofeibao.framework.net.ResponseBuilder;
import com.youyou.xiaofeibao.version2.Config;
import com.youyou.xiaofeibao.version2.request.searchshop.SearchShopRequestParam;
import com.youyou.xiaofeibao.version2.request.searchshop.SearchShopRequstObject;
import com.youyou.xiaofeibao.version2.response.myshop.MyshopResponseObject;
import com.youyou.xiaofeibao.version2.response.myshop.MyshopResponseParam;
import com.youyou.xiaofeibao.version2.tool.ACache;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;


/**
 * 作者：young on 2017/2/13 13:40
 */


public class SearchShopActivity extends AppCompatActivity{

    @ViewInject(R.id.ll_title)
    LinearLayout ll_title;
    @ViewInject(R.id.tv_back)
    TextView tv_back;
    @ViewInject(R.id.tv_search)
    EditText tv_search;
    @ViewInject(R.id.id_flowlayout)
    TagFlowLayout mFlowLayout;
    @ViewInject(R.id.lv_result)
    PullToRefreshListView lv_result;
    @ViewInject(R.id.search)
    TextView search;
    @ViewInject(R.id.tv_delete)
    TextView tv_delete;

    private int pos;

    private MyShopAdapter madapter;
    private ACache mACache;

    private int page=1;
    private int count=10;
    private String keyword;

    private ArrayList<String> data=new ArrayList<>();
    private TagAdapter<String> tag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.v2_searchresult);

        ViewUtils.inject(this);

        RelativeLayout rl= (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.emptyview,null);
        madapter=new MyShopAdapter(this,R.layout.v2_listview_myshop);

        lv_result.setEmptyView(rl);

        madapter.setList(new ArrayList<MyshopResponseParam>());

        lv_result.setAdapter(madapter);

        mACache = ACache.get(this);

        setlistener();

    }

    private void getKeys() {
       data.clear();
        String keys=mACache.getAsString("keys2");
        if(null!=keys&&!"".equals(keys)){
            String[] strings=keys.split(",");
            for(int i=0;i<strings.length;i++){
                String str=strings[i];
               data.add(str);
            }
            getAObject();
        }
    }

    private void setlistener() {
        lv_result.setMode(PullToRefreshBase.Mode.BOTH);
        lv_result.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
               getDataByUrl();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                getDataByUrl();

            }
        });

        lv_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(SearchShopActivity.this, SubscribeActivity.class);
//                intent.setAction(madapter.getList().get(position - 1).getMemid());
//                startActivity(intent);
                MyshopResponseParam param=madapter.getItem(position-1);
                String memid=param.getMemid();
                MyShopMoneyActivity.getData(SearchShopActivity.this,memid);


            }
        });

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getKeys();

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener()
        {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent)
            {
                keyword=data.get(position);
                tv_search.setText(keyword);
                tv_search.setSelection(keyword.length());
                getDataByUrl();

                return true;
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword=tv_search.getText().toString().trim();
                if(!"".equals(keyword)){
                    page=1;
                    getDataByUrl();
                    putKey();
                }
            }
        });

        tv_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                keyword=s.toString();
                if(s.toString().equals("")||null==s.toString()){
                    setResultGone();
                    getKeys();
                }
            }
        });

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                data.clear();
                String keys=mACache.getAsString("keys2");

                if(null!=keys&&!"".equals(keys)){
                    mACache.put("keys2","",ACache.TIME_DAY*365);
                }

                getAObject();
            }
        });
    }

    private void getAObject() {
        tag=null;
        tag=new TagAdapter<String>(data)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(SearchShopActivity.this).inflate(R.layout.bg_search_item,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        };

        mFlowLayout.setAdapter(tag);
    }

    private void putKey() {
        String key=tv_search.getText().toString().trim();
        String keys=mACache.getAsString("keys2");

        if(null==keys||"".equals(keys)){
            mACache.put("keys2",key,ACache.TIME_DAY*365);
        }else if(null!=key&&!"".equals(key)){
            String[] strings=keys.split(",");
            for(int i=0;i<strings.length;i++){
               String str=strings[i];
                if(key.equals(str)){
                   return;
                }
            }
            mACache.put("keys2",keys+","+key,ACache.TIME_DAY*365);
        }
    }

    private void getDataByUrl() {
        SearchShopRequstObject requestObject=new SearchShopRequstObject();
        SearchShopRequestParam param=new SearchShopRequestParam();
        param.setPageNum(page+"");
        param.setPageOffset(count+"");
        param.setShopName(keyword);
        requestObject.setParam(param);

        ResponseBuilder<SearchShopRequstObject, MyshopResponseObject> builder =
                new ResponseBuilder<>(requestObject, Config.PROXYSHOP,MyshopResponseObject.class);

        builder.setCallBack(new NetCallBack<MyshopResponseObject>() {
            @Override
            public void onSuccess(MyshopResponseObject reponse) {

                if(lv_result.isRefreshing()){
                    lv_result.onRefreshComplete();
                }
                setResultVisibly();

                if(page==1){
                    madapter.setList(reponse.getData().getShopList());
                }else {
                    madapter.addList(reponse.getData().getShopList());
                }
                if(reponse.getCode().equals("1")){
                    if(reponse.getData().getShopList().size()!=0){
                        page++;
                    }
                }
            }

            @Override
            public void onNetFailure(String str) {
                setResultVisibly();
                if(lv_result.isRefreshing()){
                    lv_result.onRefreshComplete();
                }
            }

            @Override
            public void onServerFailure(String str) {
                setResultVisibly();
                if(lv_result.isRefreshing()){
                    lv_result.onRefreshComplete();
                }
            }

            @Override
            public void onFailure(MyshopResponseObject searchResponseObject) {
                setResultVisibly();
                if(lv_result.isRefreshing()){
                    lv_result.onRefreshComplete();
                }
            }
        }).send();
    }

    public void setResultVisibly(){
        ll_title.setVisibility(View.GONE);
        mFlowLayout.setVisibility(View.GONE);
        lv_result.setVisibility(View.VISIBLE);
    }

    public void setResultGone(){
        ll_title.setVisibility(View.VISIBLE);
        mFlowLayout.setVisibility(View.VISIBLE);
        lv_result.setVisibility(View.GONE);
    }

    // 获取点击事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                HideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    // 判定是否需要隐藏
    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    // 隐藏软键盘
    private void HideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
