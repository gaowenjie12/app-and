package com.zsoe.businesssharing.bean;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-09-05 21:02
 * @version:
 * @类说明:
 */
public class DetailJiaoLiuBean {
    private int id;
    private String title;
    private String pubdate;
    private String image;
    private String guester;
    private String activitytime;
    private String activityaddress;
    private String content;
    private String resultcontent;
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

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
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

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setResultcontent(String resultcontent) {
        this.resultcontent = resultcontent;
    }

    public String getResultcontent() {
        return resultcontent;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public int getIs_collect() {
        return is_collect;
    }

}