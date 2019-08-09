package com.zsoe.businesssharing.bean;

import java.util.List;

/**
 * 分页父类
 * 子类须指定 T，否则影响缓存
 * Created by onion on 2016/8/10.
 */
public class OpenListBean<T> {
    //  data:{ content:[{T} ,{}]};     data -- bean
    public  List<T> list;

    public List<T> getContent() {
        return list;
    }

    public void setContent(List<T> content) {
        this.list = content;
    }

    @Override
    public String toString() {
        return "BroadSpeakListBean{" +
                "content=" + list +
                '}';
    }
}
