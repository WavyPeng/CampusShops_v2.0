package com.wavy.o2o.dao;

import com.wavy.o2o.entity.Area;

import java.util.List;

/**
 * Created by WavyPeng on 2018/6/1.
 */
public interface AreaDao {
    /**
     * 列出区域列表
     * @return areaList
     */
    List<Area> queryArea();
}
