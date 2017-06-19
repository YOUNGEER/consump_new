package com.youyou.xiaofeibao.version2.response.pay;

/**
 * 作者：young on 2016/11/1 15:55
 */

public class PayResponseData {

    /**
     * appid : wxbbcf236b07638282
     * noncestr : LWHEU1789TDI0GE5I2MN
     * packageStr : Sign=WXPay
     * partnerid : 1311054701
     * prepayid : wx201611031536547bdc9dabae0020803353
     * sign : BDBC4E863648AA429A9FD2163CF4ADBC
     * timestamp : 1478158613
     */

    private String appid;
    private String noncestr;
    private String packageStr;
    private String partnerid;
    private String prepayid;
    private String sign;
    private String timestamp;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageStr() {
        return packageStr;
    }

    public void setPackageStr(String packageStr) {
        this.packageStr = packageStr;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "PayResponseData{" +
                "appid='" + appid + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", packageStr='" + packageStr + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", sign='" + sign + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
