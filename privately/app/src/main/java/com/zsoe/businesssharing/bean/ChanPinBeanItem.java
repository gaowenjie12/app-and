package com.zsoe.businesssharing.bean;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-08-29 19:46
 * @version:
 * @类说明:
 */
public class ChanPinBeanItem {
    private int id;
    private String productname;
    private String thumb;
    private String content;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductname() {
        return productname;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getThumb() {
        return thumb;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}