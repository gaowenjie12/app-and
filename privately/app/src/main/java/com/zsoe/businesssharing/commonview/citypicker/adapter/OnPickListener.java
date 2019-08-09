package com.zsoe.businesssharing.commonview.citypicker.adapter;


import com.zsoe.businesssharing.commonview.citypicker.model.City;

public interface OnPickListener {
    void onPick(int position, City data);
    void onLocate();
    void onCancel();
}
