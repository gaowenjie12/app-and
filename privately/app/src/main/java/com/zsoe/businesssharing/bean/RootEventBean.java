package com.zsoe.businesssharing.bean;

import java.util.List;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-09-07 18:29
 * @version:
 * @类说明:
 */
public class RootEventBean {
    private List<ItemEventBean> list;
    private List<SlideBean> slide;

    public List<ItemEventBean> getList() {
        return list;
    }

    public void setList(List<ItemEventBean> list) {
        this.list = list;
    }

    public List<SlideBean> getSlide() {
        return slide;
    }

    public void setSlide(List<SlideBean> slide) {
        this.slide = slide;
    }
}
