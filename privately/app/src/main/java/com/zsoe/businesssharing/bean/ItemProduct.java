package com.zsoe.businesssharing.bean;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-08-31 13:17
 * @version:
 * @类说明:
 */
public class ItemProduct {

    private int id;
    private String name;
    private String thumb;
    private String content;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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