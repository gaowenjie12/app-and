package com.zsoe.businesssharing.bean;

import java.util.List;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-09-18 20:55
 * @version:
 * @类说明:
 */
public class MySignBean {
    private int signdays;
    private boolean is_todaysign;
    private String signtext;
    private String signimg;
    private String signrule;
    private String rankticketrule;
    private List<ItemInsdustry> rankticket_list;


    public boolean isIs_todaysign() {
        return is_todaysign;
    }

    public void setIs_todaysign(boolean is_todaysign) {
        this.is_todaysign = is_todaysign;
    }

    public List<ItemInsdustry> getRankticket_list() {
        return rankticket_list;
    }

    public void setRankticket_list(List<ItemInsdustry> rankticket_list) {
        this.rankticket_list = rankticket_list;
    }

    public void setSigndays(int signdays) {
        this.signdays = signdays;
    }

    public int getSigndays() {
        return signdays;
    }

    public void setSigntext(String signtext) {
        this.signtext = signtext;
    }

    public String getSigntext() {
        return signtext;
    }

    public void setSignimg(String signimg) {
        this.signimg = signimg;
    }

    public String getSignimg() {
        return signimg;
    }

    public void setSignrule(String signrule) {
        this.signrule = signrule;
    }

    public String getSignrule() {
        return signrule;
    }

    public void setRankticketrule(String rankticketrule) {
        this.rankticketrule = rankticketrule;
    }

    public String getRankticketrule() {
        return rankticketrule;
    }

}