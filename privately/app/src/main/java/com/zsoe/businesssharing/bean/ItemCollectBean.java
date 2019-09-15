package com.zsoe.businesssharing.bean;

import java.util.List;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-09-15 09:18
 * @version:
 * @类说明:
 */
public class ItemCollectBean {

    private List<ItemInsdustry> companyist;
    private List<ItemInsdustry> productlist;
    private List<ItemInsdustry> communicationlist;
    private List<ItemInsdustry> joinmerchantlist;
    private List<ItemInsdustry> loanfinancelist;

    public List<ItemInsdustry> getCompanyist() {
        return companyist;
    }

    public void setCompanyist(List<ItemInsdustry> companyist) {
        this.companyist = companyist;
    }

    public List<ItemInsdustry> getProductlist() {
        return productlist;
    }

    public void setProductlist(List<ItemInsdustry> productlist) {
        this.productlist = productlist;
    }

    public List<ItemInsdustry> getCommunicationlist() {
        return communicationlist;
    }

    public void setCommunicationlist(List<ItemInsdustry> communicationlist) {
        this.communicationlist = communicationlist;
    }

    public List<ItemInsdustry> getJoinmerchantlist() {
        return joinmerchantlist;
    }

    public void setJoinmerchantlist(List<ItemInsdustry> joinmerchantlist) {
        this.joinmerchantlist = joinmerchantlist;
    }

    public List<ItemInsdustry> getLoanfinancelist() {
        return loanfinancelist;
    }

    public void setLoanfinancelist(List<ItemInsdustry> loanfinancelist) {
        this.loanfinancelist = loanfinancelist;
    }
}
