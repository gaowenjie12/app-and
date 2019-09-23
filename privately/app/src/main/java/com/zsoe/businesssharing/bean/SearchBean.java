package com.zsoe.businesssharing.bean;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-09-23 13:17
 * @version:
 * @类说明:
 */
public class SearchBean {

    private int id;
    private String title;
    private String thumb;
    private int contenttype;
    private String contenttypedes;
    private String linkurl;


    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public void setContenttype(int contenttype) {
        this.contenttype = contenttype;
    }

    public int getContenttype() {
        return contenttype;
    }

    public void setContenttypedes(String contenttypedes) {
        this.contenttypedes = contenttypedes;
    }

    public String getContenttypedes() {
        return contenttypedes;
    }

}