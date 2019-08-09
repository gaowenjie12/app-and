package com.zsoe.businesssharing.bean;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-05-29 17:45
 * @version:
 * @类说明:
 */
public class VersionBean {

    private String new_version;
    private String title;
    private String content;
    private String download_url;
    private int has_new_version;
    private int force_update;


    public String getNew_version() {
        return new_version;
    }

    public void setNew_version(String new_version) {
        this.new_version = new_version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public int getHas_new_version() {
        return has_new_version;
    }

    public void setHas_new_version(int has_new_version) {
        this.has_new_version = has_new_version;
    }

    public int getForce_update() {
        return force_update;
    }

    public void setForce_update(int force_update) {
        this.force_update = force_update;
    }
}
