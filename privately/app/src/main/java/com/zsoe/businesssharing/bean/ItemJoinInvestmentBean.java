package com.zsoe.businesssharing.bean;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-08-29 20:19
 * @version:
 * @类说明:
 */
public class ItemJoinInvestmentBean {

    private int id;
    private String title;
    private String thumb;
    private String joindate;
    private int readnum;

    public String getJoindate() {
        return joindate;
    }

    public void setJoindate(String joindate) {
        this.joindate = joindate;
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

    public void setReadnum(int readnum) {
        this.readnum = readnum;
    }

    public int getReadnum() {
        return readnum;
    }

}