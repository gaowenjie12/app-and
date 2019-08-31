package com.zsoe.businesssharing.bean;

import java.util.List;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-08-31 13:18
 * @version:
 * @类说明:
 */
public class IndustryRoot {
    private IndustryInfo industry_info;
    private List<ItemCompany> companylist;
    private List<ItemProduct> productlist;

    public IndustryInfo getIndustry_info() {
        return industry_info;
    }

    public void setIndustry_info(IndustryInfo industry_info) {
        this.industry_info = industry_info;
    }

    public List<ItemCompany> getCompanylist() {
        return companylist;
    }

    public void setCompanylist(List<ItemCompany> companylist) {
        this.companylist = companylist;
    }

    public List<ItemProduct> getProductlist() {
        return productlist;
    }

    public void setProductlist(List<ItemProduct> productlist) {
        this.productlist = productlist;
    }
}
