package com.zsoe.businesssharing.bean;

import java.util.List;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-08-31 21:09
 * @version:
 * @类说明:
 */
public class RootHangYe {
    /*"id": 22,
      "name": "IT/通信/互联网",
      */

    private int id;
    private String name;
    private List<ChildHangYe> child;

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

    public List<ChildHangYe> getChild() {
        return child;
    }

    public void setChild(List<ChildHangYe> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "RootHangYe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", child=" + child +
                '}';
    }
}
