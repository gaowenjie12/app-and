package com.zsoe.businesssharing.bean;

import java.util.List;

/**
* @author 于长亮   & E-mail: yuchangl3757@qq.com
* @create_time 创建时间：2019-09-22 19:41
* @version:
* @类说明:
*/
public    class RootSearchBean   {
    private List<ItemSearchBean> history_list;
    private List<ItemSearchBean> hot_list;

    public List<ItemSearchBean> getHistory_list() {
        return history_list;
    }

    public void setHistory_list(List<ItemSearchBean> history_list) {
        this.history_list = history_list;
    }

    public List<ItemSearchBean> getHot_list() {
        return hot_list;
    }

    public void setHot_list(List<ItemSearchBean> hot_list) {
        this.hot_list = hot_list;
    }
}
