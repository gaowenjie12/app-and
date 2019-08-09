package com.zsoe.businesssharing.commonview.wheelview.adapter;


import java.util.List;

/**
 * Numeric Wheel adapter.
 */
public class NumericMinutesWheelAdapter implements WheelAdapter {

//    private int minValue;
//    private int maxValue;

    private List<String> values;


    public void setValues(List<String> values) {
        this.values = values;
    }

    /**
     * Constructor
     */
    public NumericMinutesWheelAdapter(List<String> values) {
//        this.minValue = minValue;
//        this.maxValue = maxValue;

        this.values = values;
    }

    @Override
    public Object getItem(int index) {

//        if (index == 0) {
//            return minValue;
//        }
        return Integer.parseInt(values.get(index));
    }

    @Override
    public int getItemsCount() {
        return values.size();
    }

    @Override
    public int indexOf(Object o) {
        try {
            int index = 0;
            String value = (String) o;

            for (int i = 0; i < values.size(); i++) {

                String s = values.get(i);
                if (value.equals(s)) {
                    index = i;
                    break;
                }
            }

            return index;
        } catch (Exception e) {
            return -1;
        }

    }
}
