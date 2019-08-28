package com.zsoe.businesssharing.bean;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-08-27 20:12
 * @version:
 * @类说明:幻灯片
 */
public class SlideBean {

    private int id;
    private String slide;
    private int linktype;
    private int linkurlid;
    private String linkurl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSlide() {
        return slide;
    }

    public void setSlide(String slide) {
        this.slide = slide;
    }

    public int getLinktype() {
        return linktype;
    }

    public void setLinktype(int linktype) {
        this.linktype = linktype;
    }

    public int getLinkurlid() {
        return linkurlid;
    }

    public void setLinkurlid(int linkurlid) {
        this.linkurlid = linkurlid;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }
}
