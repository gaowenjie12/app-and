package com.zsoe.businesssharing.bean;

import java.util.List;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-08-31 16:49
 * @version:
 * @类说明:
 */
public class ProductDetail {

    private int id;
    private int shopid;
    private String productname;
    private String videourl;
    private String content;
    private String videocoverurl;
    private String companyname;
    private List<String> photos;
    private int is_collect;

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

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

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductname() {
        return productname;
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

    public void setVideocoverurl(String videocoverurl) {
        this.videocoverurl = videocoverurl;
    }

    public String getVideocoverurl() {
        return videocoverurl;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public List<String> getPhotos() {
        return photos;
    }

}