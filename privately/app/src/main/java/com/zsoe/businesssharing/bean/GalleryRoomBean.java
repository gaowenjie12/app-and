package com.zsoe.businesssharing.bean;

import java.util.List;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-08-31 12:48
 * @version:
 * @类说明:
 */
public class GalleryRoomBean {

    private List<SlideBean> slide;
    private List<ItemInsdustry> insdustrylist;
    private List<ItemInsdustry> industrycafelist;
    private List<ItemInsdustry> industrybeamlist;

    private Communicationthumbs communicationthumbs;

    public List<SlideBean> getSlide() {
        return slide;
    }

    public void setSlide(List<SlideBean> slide) {
        this.slide = slide;
    }

    public List<ItemInsdustry> getInsdustrylist() {
        return insdustrylist;
    }

    public void setInsdustrylist(List<ItemInsdustry> insdustrylist) {
        this.insdustrylist = insdustrylist;
    }

    public List<ItemInsdustry> getIndustrycafelist() {
        return industrycafelist;
    }

    public void setIndustrycafelist(List<ItemInsdustry> industrycafelist) {
        this.industrycafelist = industrycafelist;
    }

    public List<ItemInsdustry> getIndustrybeamlist() {
        return industrybeamlist;
    }

    public void setIndustrybeamlist(List<ItemInsdustry> industrybeamlist) {
        this.industrybeamlist = industrybeamlist;
    }

    public Communicationthumbs getCommunicationthumbs() {
        return communicationthumbs;
    }

    public void setCommunicationthumbs(Communicationthumbs communicationthumbs) {
        this.communicationthumbs = communicationthumbs;
    }
}
