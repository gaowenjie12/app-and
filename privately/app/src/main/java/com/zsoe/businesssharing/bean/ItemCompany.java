package com.zsoe.businesssharing.bean;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-08-31 13:16
 * @version:
 * @类说明:
 */
public class ItemCompany {

    private int id;
    private String name;
    private String thumb;
    private String companydes;
    private String maincate;

    private int is_collect;

    public String getMaincate() {
        return maincate;
    }

    public void setMaincate(String maincate) {
        this.maincate = maincate;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getThumb() {
        return thumb;
    }

    public void setCompanydes(String companydes) {
        this.companydes = companydes;
    }

    public String getCompanydes() {
        return companydes;
    }

}