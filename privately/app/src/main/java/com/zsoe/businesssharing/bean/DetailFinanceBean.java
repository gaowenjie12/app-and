package com.zsoe.businesssharing.bean;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-08-29 21:52
 * @version:
 * @类说明:
 */
public class DetailFinanceBean {


    private int id;
    private int shopid;
    private String title;
    private String videourl;
    private String content;
    private String filedata;
    private String companyname;

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

    public void setFiledata(String filedata) {
        this.filedata = filedata;
    }

    public String getFiledata() {
        return filedata;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanyname() {
        return companyname;
    }

}