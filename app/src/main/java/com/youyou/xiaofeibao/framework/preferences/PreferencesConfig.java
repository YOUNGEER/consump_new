package com.youyou.xiaofeibao.framework.preferences;


import android.content.Context;

import com.youyou.xiaofeibao.ConsumApplication;
import com.youyou.xiaofeibao.framework.preferences.item.BooleanPreferences;
import com.youyou.xiaofeibao.framework.preferences.item.FloatPreferences;
import com.youyou.xiaofeibao.framework.preferences.item.IntPreferences;
import com.youyou.xiaofeibao.framework.preferences.item.StringPreferences;


/**
 *
 */
public class PreferencesConfig {


    private static final SharedPreferencesWrapper SHARED_PREFERENCES_INIT = new SharedPreferencesWrapper(ConsumApplication.getInstance(), "init", Context.MODE_PRIVATE);

//	private static final SharedPreferencesWrapper SHARED_PREFERENCES_INIT_CONFIG_NAME = new SharedPreferencesWrapper(ConsumApplication.getInstance(), "navi_config", Context.MODE_PRIVATE);

    public static final IntPreferences MESSAGE_COUNT = new IntPreferences(SHARED_PREFERENCES_INIT, "message_count", 4);
    /**
     * 用户id
     */
    public static final StringPreferences memberId = new StringPreferences(SHARED_PREFERENCES_INIT, "memberId", "");
    /**
     * 用户名 name
     */
    public static final StringPreferences name = new StringPreferences(SHARED_PREFERENCES_INIT, "name", "");
    /**
     * phone;// 用户手机号
     */
    public static final StringPreferences phone = new StringPreferences(SHARED_PREFERENCES_INIT, "phone", "");
    /**
     * type;// 用户类型 0普通会员 1合伙人 2商人
     */
    public static final StringPreferences type = new StringPreferences(SHARED_PREFERENCES_INIT, "type", "");
    /**
     * 用户头像地址
     */
    public static final StringPreferences imgUrl = new StringPreferences(SHARED_PREFERENCES_INIT, "imgUrl", "");
    /**
     * loginName;// 用户登录名称
     */
    public static final StringPreferences loginName = new StringPreferences(SHARED_PREFERENCES_INIT, "loginName", "");
    /**
     * String sex;// 用户性别
     */
    public static final StringPreferences sex = new StringPreferences(SHARED_PREFERENCES_INIT, "sex", "");
    /**
     * int age;// 年龄
     */
    public static final IntPreferences age = new IntPreferences(SHARED_PREFERENCES_INIT, "age", 1);
    /**
     * String qq;// qq号
     */
    public static final StringPreferences qq = new StringPreferences(SHARED_PREFERENCES_INIT, "qq", "");
    /**
     * String email;// 邮箱
     */
    public static final StringPreferences email = new StringPreferences(SHARED_PREFERENCES_INIT, "email", "");
    /**
     * String provinceId;// 省份编码
     */
    public static final StringPreferences provinceId = new StringPreferences(SHARED_PREFERENCES_INIT, "provinceId", "");
    /**
     * String provinceName;// 省份名称
     */
    public static final StringPreferences provinceName = new StringPreferences(SHARED_PREFERENCES_INIT, "provinceName", "");
    /**
     * String cityId;// 城市编码
     */
    public static final StringPreferences cityId = new StringPreferences(SHARED_PREFERENCES_INIT, "cityId", "");
    /**
     * String cityName;// 城市名称
     */
    public static final StringPreferences cityName = new StringPreferences(SHARED_PREFERENCES_INIT, "cityName", "");
    /**
     * String addr;// 地址
     */
    public static final StringPreferences addr = new StringPreferences(SHARED_PREFERENCES_INIT, "addr", "");
    /**
     * int goldNum;// 智惠币数量
     */
    public static final FloatPreferences goldNum = new FloatPreferences(SHARED_PREFERENCES_INIT, "goldNum", 0);
    /**
     * String bankUsername;// 银行账号开户人姓名
     */
    public static final StringPreferences bankUsername = new StringPreferences(SHARED_PREFERENCES_INIT, "bankUsername", "");
    /**
     * String bankName;// 银行名称
     */
    public static final StringPreferences bankName = new StringPreferences(SHARED_PREFERENCES_INIT, "bankName", "");
    /**
     * String bankNo;// 银行卡号
     */
    public static final StringPreferences bankNo = new StringPreferences(SHARED_PREFERENCES_INIT, "bankNo", "");
    /**
     * String partnerAgencyMethod;// 合伙人支付方式 0线上 1线下
     */
    public static final StringPreferences partnerAgencyMethod = new StringPreferences(SHARED_PREFERENCES_INIT, "partnerAgencyMethod", "");
    /**
     * String partnerAgencyPayStatus;// 合伙人支付状态 0未支付 1已支付
     */
    public static final StringPreferences partnerAgencyPayStatus = new StringPreferences(SHARED_PREFERENCES_INIT, "partnerAgencyPayStatus", "");
    /**
     * int houseFund;// 房基金
     */
    public static final IntPreferences houseFund = new IntPreferences(SHARED_PREFERENCES_INIT, "houseFund", 1);
    /**
     * int carFund;// 车基金
     */
    public static final IntPreferences carFund = new IntPreferences(SHARED_PREFERENCES_INIT, "carFund", 1);
    /**
     * String parterLevel;// 合伙人等级
     */
    public static final StringPreferences parterLevel = new StringPreferences(SHARED_PREFERENCES_INIT, "parterLevel", "");
    /**
     * 合伙人等级名称 levelName
     */
    public static final StringPreferences levelName = new StringPreferences(SHARED_PREFERENCES_INIT, "levelName", "");
    /**
     * String inviteCode;// 邀请码
     */
    public static final StringPreferences inviteCode = new StringPreferences(SHARED_PREFERENCES_INIT, "inviteCode", "");
    /**
     * String idcardno;// 身份证号
     */
    public static final StringPreferences idcardno = new StringPreferences(SHARED_PREFERENCES_INIT, "idcardno", "");
    /**
     * bankAddr 银行开户所在地
     */
    public static final StringPreferences bankAddr = new StringPreferences(SHARED_PREFERENCES_INIT, "bankAddr", "");

    /**
     * 直营超市的起送价
     */
    public static final StringPreferences START_SEND_PRICE = new StringPreferences(SHARED_PREFERENCES_INIT, "startsendprice", "");
    /**
     * 直营超市的配送价
     */
    public static final StringPreferences SEND_PRICE = new StringPreferences(SHARED_PREFERENCES_INIT, "sendrice", "");
    /**
     * 合伙人邀请码
     */
    public static final StringPreferences PATNER_INVITE_CODE = new StringPreferences(SHARED_PREFERENCES_INIT, "PATNER_INVITE_CODE", "");
    /**
     * 商家邀请码
     */
    public static final StringPreferences BUSSINESS_INVITE_CODE = new StringPreferences(SHARED_PREFERENCES_INIT, "BUSSINESS_INVITE_CODE", "");
    public static BooleanPreferences IS_FIRST = new BooleanPreferences(SHARED_PREFERENCES_INIT, "isfrist", true);
    /**
     * 当前等级的加盟价格
     */
    public static final IntPreferences currentLevelFee = new IntPreferences(SHARED_PREFERENCES_INIT, "currentLevelFee", 0);
    /**
     * 账户余额
     */
    public static final FloatPreferences remainAccount = new FloatPreferences(SHARED_PREFERENCES_INIT, "remainAccount", 0);
    /**
     * 下一级合伙人数量
     */
    public static final IntPreferences FIRST_REFREE_NUM = new IntPreferences(SHARED_PREFERENCES_INIT, "firstRefreeNum", 0);
    /**
     * 下二级合伙人数量
     */
    public static final IntPreferences SECOND_RREFEE_NUM = new IntPreferences(SHARED_PREFERENCES_INIT, "secondRrefeeNum", 0);
    /**
     * 下三级合伙人数量
     */
    public static final IntPreferences THIRD_REFREE_NUM = new IntPreferences(SHARED_PREFERENCES_INIT, "thirdRefreeNum", 0);
    /**
     * 上级合伙人姓名
     */
    public static final StringPreferences LAST_REFREE_NAME = new StringPreferences(SHARED_PREFERENCES_INIT, "lastRefreeName", "");
    /**
     * 是否是联盟商家0不是 1是
     */
    public static final StringPreferences IS_SHOP = new StringPreferences(SHARED_PREFERENCES_INIT, "isShop", "");
    /**
     * 商家信息 最新账户余额
     */
    public static final StringPreferences AVAILABLERemainAccount = new StringPreferences(SHARED_PREFERENCES_INIT, "availableRemainAccount", "");

    //-----------------------------------------------------------------v2
    private static final SharedPreferencesWrapper v2_PREFERENCES_INIT = new SharedPreferencesWrapper(ConsumApplication.getInstance(), "init", Context.MODE_PRIVATE);

    public static final StringPreferences v2_cityname = new StringPreferences(v2_PREFERENCES_INIT, "cityname", "合肥市");
    public static final StringPreferences v2_isfirst = new StringPreferences(v2_PREFERENCES_INIT, "v2_isfirst", "true");

    public static final StringPreferences v2_citycode = new StringPreferences(v2_PREFERENCES_INIT, "citycode", "127");

    public static final StringPreferences v2_lat = new StringPreferences(v2_PREFERENCES_INIT, "lat", "");

    public static final StringPreferences v2_longt = new StringPreferences(v2_PREFERENCES_INIT, "longt", "");

    public static final StringPreferences v2_token = new StringPreferences(v2_PREFERENCES_INIT, "token", "");

    public static final StringPreferences v2_loginName = new StringPreferences(v2_PREFERENCES_INIT, "loginName", "未登陆");

    public static final StringPreferences v2_phone = new StringPreferences(v2_PREFERENCES_INIT, "phone", "");

    public static final StringPreferences v2_url = new StringPreferences(v2_PREFERENCES_INIT, "url", "");

    public static final StringPreferences v2_uesrid = new StringPreferences(v2_PREFERENCES_INIT, "userid", "");

    public static final StringPreferences v2_nickname = new StringPreferences(v2_PREFERENCES_INIT, "nickname", "");

    public static final StringPreferences v2_allmoney = new StringPreferences(v2_PREFERENCES_INIT, "allmoney", "");

    public static final StringPreferences v2_isshopchecked = new StringPreferences(v2_PREFERENCES_INIT, "isshopchecked", "");

    public static final StringPreferences v2_isproxychecked = new StringPreferences(v2_PREFERENCES_INIT, "isproxychecked", "");

    public static final StringPreferences v2_name = new StringPreferences(v2_PREFERENCES_INIT, "name", "");

    public static final BooleanPreferences v2_see=new BooleanPreferences(v2_PREFERENCES_INIT, "see", true);

    public static final StringPreferences v2_iswx=new StringPreferences(v2_PREFERENCES_INIT,"iswx","0");// 0 是没有绑定，1是绑定

    public static final StringPreferences v2_iszfb=new StringPreferences(v2_PREFERENCES_INIT,"iszfb","0");// 0 是没有绑定，1是绑定

}
