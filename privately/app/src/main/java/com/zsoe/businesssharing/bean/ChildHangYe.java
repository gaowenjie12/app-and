package com.zsoe.businesssharing.bean;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-08-31 21:08
 * @version:
 * @类说明:
 */
public class ChildHangYe {
//    {
//        "id": 23,
//            "name": "互联网/电子商务"
//    }

    private int id;
    private String name;

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

    @Override
    public String toString() {
        return "ChildHangYe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
