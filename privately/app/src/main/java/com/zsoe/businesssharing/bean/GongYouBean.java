package com.zsoe.businesssharing.bean;

import java.util.List;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-09-02 20:51
 * @version:
 * @类说明:
 */
public class GongYouBean {
    private List<RootHangYe> industrycatelist;
    private List<RootHangYe> productcatelist;

    public List<RootHangYe> getIndustrycatelist() {
        return industrycatelist;
    }

    public void setIndustrycatelist(List<RootHangYe> industrycatelist) {
        this.industrycatelist = industrycatelist;
    }

    public List<RootHangYe> getProductcatelist() {
        return productcatelist;
    }

    public void setProductcatelist(List<RootHangYe> productcatelist) {
        this.productcatelist = productcatelist;
    }

    @Override
    public String toString() {
        return "GongYouBean{" +
                "industrycatelist=" + industrycatelist +
                ", productcatelist=" + productcatelist +
                '}';
    }
}
