package com.zsoe.businesssharing.bean;

import java.util.List;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-08-27 20:36
 * @version:
 * @类说明:
 */
public class HomeBean {

    private List<SlideBean> slide;
    private List<HeadNews> headnews;
    private List<ExtenactivityBean> extenactivity;
    protected List<StockpurchaseBean> stockpurchase;
    private List<ProductBean> product;
    private List<JoinmerchantBean> joinmerchant;

    public List<SlideBean> getSlide() {
        return slide;
    }

    public void setSlide(List<SlideBean> slide) {
        this.slide = slide;
    }

    public List<HeadNews> getHeadnews() {
        return headnews;
    }

    public void setHeadnews(List<HeadNews> headnews) {
        this.headnews = headnews;
    }

    public List<ExtenactivityBean> getExtenactivity() {
        return extenactivity;
    }

    public void setExtenactivity(List<ExtenactivityBean> extenactivity) {
        this.extenactivity = extenactivity;
    }

    public List<StockpurchaseBean> getStockpurchase() {
        return stockpurchase;
    }

    public void setStockpurchase(List<StockpurchaseBean> stockpurchase) {
        this.stockpurchase = stockpurchase;
    }

    public List<ProductBean> getProduct() {
        return product;
    }

    public void setProduct(List<ProductBean> product) {
        this.product = product;
    }

    public List<JoinmerchantBean> getJoinmerchant() {
        return joinmerchant;
    }

    public void setJoinmerchant(List<JoinmerchantBean> joinmerchant) {
        this.joinmerchant = joinmerchant;
    }
}
