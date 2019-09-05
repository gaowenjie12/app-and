package com.zsoe.businesssharing.bean;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-09-05 20:32
 * @version:
 * @类说明:
 */
public class JiaoLiuBean {
    private int id;
    private String title;
    private String thumb;
    private String guester;
    private String activitytime;
    private String activityaddress;
    private int is_collect;

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

    public void setGuester(String guester) {
        this.guester = guester;
    }

    public String getGuester() {
        return guester;
    }

    public void setActivitytime(String activitytime) {
        this.activitytime = activitytime;
    }

    public String getActivitytime() {
        return activitytime;
    }

    public void setActivityaddress(String activityaddress) {
        this.activityaddress = activityaddress;
    }

    public String getActivityaddress() {
        return activityaddress;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public int getIs_collect() {
        return is_collect;
    }

}