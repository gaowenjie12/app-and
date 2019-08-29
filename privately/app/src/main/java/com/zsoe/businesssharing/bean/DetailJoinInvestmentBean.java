package com.zsoe.businesssharing.bean;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-08-29 20:32
 * @version:
 * @类说明:
 */
public class DetailJoinInvestmentBean {

    private int id;
    private int shopid;
    private String title;
    private String thumb;
    private String videourl;
    private String content;
    private String policytext;
    private String companyname;
    private String des;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }

    public int getShopid() {
        return shopid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getThumb() {
        return thumb;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setPolicytext(String policytext) {
        this.policytext = policytext;
    }

    public String getPolicytext() {
        return policytext;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDes() {
        return des;
    }

}