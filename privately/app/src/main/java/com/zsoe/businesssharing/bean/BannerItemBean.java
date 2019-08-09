package com.zsoe.businesssharing.bean;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-05-07 13:50
 * @version:
 * @类说明:
 */
public class BannerItemBean {
    private int id;
    private String img;
    private String url;
    private String url_title;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl_title(String url_title) {
        this.url_title = url_title;
    }

    public String getUrl_title() {
        return url_title;
    }

}
