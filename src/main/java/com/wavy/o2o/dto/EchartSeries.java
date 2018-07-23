package com.wavy.o2o.dto;

import java.util.List;

/**
 * Echart中的series项
 * Created by WavyPeng on 2018/7/23.
 */
public class EchartSeries {
    private String name;
    private String type = "bar";
    private List<Integer> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }
}
