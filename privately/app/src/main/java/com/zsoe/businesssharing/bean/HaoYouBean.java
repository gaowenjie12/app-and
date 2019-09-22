package com.zsoe.businesssharing.bean;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-09-22 16:30
 * @version:
 * @类说明:
 */
public class HaoYouBean {

    private int id;
    private int uid;
    private String friend_name;
    private String friend_username;
    private String avatar;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public void setFriend_name(String friend_name) {
        this.friend_name = friend_name;
    }

    public String getFriend_name() {
        return friend_name;
    }

    public void setFriend_username(String friend_username) {
        this.friend_username = friend_username;
    }

    public String getFriend_username() {
        return friend_username;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

}