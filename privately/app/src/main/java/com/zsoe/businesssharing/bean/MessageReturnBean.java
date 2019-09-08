package com.zsoe.businesssharing.bean;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-09-08 11:26
 * @version:
 * @类说明:
 */
public class MessageReturnBean {

    private int id;
    private int to_uid;
    private int from_uid;
    private String msg;
    private long createtime;

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

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public long getCreatetime() {
        return createtime;
    }

}