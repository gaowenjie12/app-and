package com.yuyh.library.imgsel.bean;

import java.io.Serializable;

/**
 * Image bean
 * Created by Yancy on 2015/12/2.
 */
public class Image implements Serializable {

    public String path;
    public String name;
    public String thumbnailPath;

    public Image(String path, String name,String thumbnailPath) {
        this.path = path;
        this.name = name;
        this.thumbnailPath = thumbnailPath;
    }

    public Image() {

    }

    @Override
    public boolean equals(Object o) {
        try {
            Image other = (Image) o;
            return this.path.equalsIgnoreCase(other.path);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}