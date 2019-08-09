package com.zsoe.businesssharing.commonview.citypicker.adapter;


import com.zsoe.businesssharing.commonview.citypicker.model.City;

public interface InnerListener {
    void dismiss(int position, City data);
    void locate();
}
