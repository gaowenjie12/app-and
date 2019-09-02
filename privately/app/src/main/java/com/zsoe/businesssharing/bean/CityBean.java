package com.zsoe.businesssharing.bean;

import java.io.Serializable;

/**
 * 市
 */

public class CityBean implements Serializable {
    int id;
    String name;
    String pid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

}
