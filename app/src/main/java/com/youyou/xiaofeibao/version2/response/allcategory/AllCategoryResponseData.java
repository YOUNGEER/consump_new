package com.youyou.xiaofeibao.version2.response.allcategory;


import java.util.List;

/**
 * 作者：young on 2017/7/14 10:48
 */

public class AllCategoryResponseData {
    private List<AddressTypeList> addressTypeList;
    private List<BusinessList> businessList;
    private List<ContactTypeList> contactTypeList;
    private List<CategoryList> list;

    public List<AddressTypeList> getAddressTypeList() {
        return addressTypeList;
    }

    public void setAddressTypeList(List<AddressTypeList> addressTypeList) {
        this.addressTypeList = addressTypeList;
    }

    public List<BusinessList> getBusinessList() {
        return businessList;
    }

    public void setBusinessList(List<BusinessList> businessList) {
        this.businessList = businessList;
    }

    public List<ContactTypeList> getContactTypeList() {
        return contactTypeList;
    }

    public void setContactTypeList(List<ContactTypeList> contactTypeList) {
        this.contactTypeList = contactTypeList;
    }

    public List<CategoryList> getList() {
        return list;
    }

    public void setList(List<CategoryList> list) {
        this.list = list;
    }
}
