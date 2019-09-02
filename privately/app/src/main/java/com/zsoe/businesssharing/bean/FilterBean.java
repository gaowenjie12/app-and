package com.zsoe.businesssharing.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 多筛选条件
 */

public class FilterBean implements Serializable {

    //行业分类
    private List<RootHangYe> industrycatelist;
    //商品分类
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

    /**
     * 场所
     */
    public static class InstitutionPlace implements Serializable {
        int id;
        String name;
        //本地的选中标记
        boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return name;
        }

        public void setType(String type) {
            this.name = type;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof InstitutionPlace))
                return false;
            InstitutionPlace that = (InstitutionPlace) o;
            return getId() == that.getId() &&
                    Objects.equals(getType(), that.getType());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getType());
        }
    }

    /**
     * PlaceProperty 场所性质
     */
    public static class PlaceProperty implements Serializable {
        int id;
        String name;
        //本地的选中标记
        boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

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
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof PlaceProperty))
                return false;
            PlaceProperty that = (PlaceProperty) o;
            return getId() == that.getId() &&
                    Objects.equals(getName(), that.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }
    }

    /**
     * 床位
     */
    public static class Bed implements Serializable {

        int id;
        String name;
        //本地的选中标记
        boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

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
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof Bed))
                return false;
            Bed bed = (Bed) o;
            return getId() == bed.getId() &&
                    Objects.equals(getName(), bed.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }
    }

    /**
     * 项目
     */

    public static class InstitutionObject implements Serializable {


        int id;
        String name;

        //本地的选中标记
        boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return name;
        }

        public void setType(String admittype) {
            this.name = admittype;
        }


    }

    /**
     * 服务
     */
    public static class InstitutionFeature implements Serializable {
        int id;
        String name;

        //本地的选中标记
        boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

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
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof InstitutionFeature))
                return false;
            InstitutionFeature that = (InstitutionFeature) o;
            return getId() == that.getId() &&
                    Objects.equals(getName(), that.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }
    }


}
