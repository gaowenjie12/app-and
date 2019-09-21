package com.zsoe.businesssharing.bean;

import java.util.List;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-08-31 16:34
 * @version:
 * @类说明:
 */
public class CompanyInfo {

    private int id;
    private int uid;
    private String name;
    private String avatar;
    private String maincate;
    private String videourl;
    private String companydes;
    private String videocoverurl;
    private List<String> photos;
    private int is_collect;

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

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

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setMaincate(String maincate) {
        this.maincate = maincate;
    }

    public String getMaincate() {
        return maincate;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setCompanydes(String companydes) {
        this.companydes = companydes;
    }

    public String getCompanydes() {
        return companydes;
    }

    public void setVideocoverurl(String videocoverurl) {
        this.videocoverurl = videocoverurl;
    }

    public String getVideocoverurl() {
        return videocoverurl;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public List<String> getPhotos() {
        return photos;
    }

}