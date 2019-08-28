package com.zsoe.businesssharing.base;


/**
 * 通用响应，解析外层数据。
 * Created by Administrator on 2016/5/19.
 */
public class RootResponse<P> {


    /**
     * status : 200  成功  800 服务器异常
     * message : 操作成功
     * result : {"id":1,"loginname":"ceshi","phone":"123456789","token":"C4CA4238A0B923820DCC509A6F75849B","path":"","idcard":"370811199909099999","bigpath":"","teachername":"测试"}
     */

    private int code;
    private String msg;
    private long time;
    private P data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public P getData() {
        return data;
    }

    public void setData(P data) {
        this.data = data;
    }
}
