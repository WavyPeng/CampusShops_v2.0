package com.wavy.o2o.dto;

import java.util.HashSet;

/**
 * Echart中的xAxis项
 * Created by WavyPeng on 2018/7/23.
 */
public class EchartXAxis {
    private String type = "category";
    // 去重
    private HashSet<String> data;

    public HashSet<String> getData() {
        return data;
    }

    public void setData(HashSet<String> data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }
}
