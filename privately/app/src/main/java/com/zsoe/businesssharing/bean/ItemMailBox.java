package com.zsoe.businesssharing.bean;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-09-16 21:24
 * @version:
 * @类说明:
 */
public class ItemMailBox {

    private int id;
    private int to_uid;
    private int from_uid;
    private int is_showbut;
    private String msg;
    private int msg_id;
    private boolean is_me;
    private String createtime;
    private String username;


    public boolean isIs_me() {
        return is_me;
    }

    public void setIs_me(boolean is_me) {
        this.is_me = is_me;
    }

    public int getIs_showbut() {
        return is_showbut;
    }

    public void setIs_showbut(int is_showbut) {
        this.is_showbut = is_showbut;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTo_uid(int to_uid) {
        this.to_uid = to_uid;
    }

    public int getTo_uid() {
        return to_uid;
    }

    public void setFrom_uid(int from_uid) {
        this.from_uid = from_uid;
    }

    public int getFrom_uid() {
        return from_uid;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }

    public int getMsg_id() {
        return msg_id;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}