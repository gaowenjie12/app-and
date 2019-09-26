package com.zsoe.businesssharing.bean;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-05-29 17:45
 * @version:
 * @类说明:
 */
public class VersionBean {

    private String newversion;
    private String packagesize;
    private String content;
    private String downloadurl;
    private int enforce;
    private int is_upd;


    public int getIs_upd() {
        return is_upd;
    }

    public void setIs_upd(int is_upd) {
        this.is_upd = is_upd;
    }

    public void setNewversion(String newversion) {
        this.newversion = newversion;
    }

    public String getNewversion() {
        return newversion;
    }

    public void setPackagesize(String packagesize) {
        this.packagesize = packagesize;
    }

    public String getPackagesize() {
        return packagesize;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setEnforce(int enforce) {
        this.enforce = enforce;
    }

    public int getEnforce() {
        return enforce;
    }
}
