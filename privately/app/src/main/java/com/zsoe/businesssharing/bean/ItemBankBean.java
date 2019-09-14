package com.zsoe.businesssharing.bean;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-08-29 21:24
 * @version:
 * @类说明:
 */
public class ItemBankBean {

    private int id;
    private int type;
    private String thumb;
    private String title;
    private String des;
    private String linkurl;
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

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getThumb() {
        return thumb;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDes() {
        return des;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    public String getLinkurl() {
        return linkurl;
    }

}