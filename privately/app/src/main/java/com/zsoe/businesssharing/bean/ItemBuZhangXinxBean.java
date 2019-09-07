package com.zsoe.businesssharing.bean;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-09-07 10:07
 * @version:
 * @类说明:
 */
public class ItemBuZhangXinxBean {

    private int id;
    private String avatar;
    private String staffname;
    private String realname;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setStaffname(String staffname) {
        this.staffname = staffname;
    }

    public String getStaffname() {
        return staffname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getRealname() {
        return realname;
    }

}