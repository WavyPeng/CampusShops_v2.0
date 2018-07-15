package com.wavy.o2o.service;

import com.wavy.o2o.entity.Area;

import java.util.List;

/**
 * Created by WavyPeng on 2018/6/1.
 */
public interface IAreaService {

    public static final String AREALISTKEY = "arealist";

    /**
     * 获取区域列表
     * @return
     */
    List<Area> getAreaList();
}
