package com.wavy.o2o.service;

import com.wavy.o2o.entity.HeadLine;

import java.util.List;

/**
 * Created by WavyPeng on 2018/6/13.
 */
public interface IHeadLineService {

    public static final String HLLISTKEY = "headlinelist";

    /**
     * 根据传入的条件返回指定的头条列表
     * @param headLineCondition
     * @return
     */
    List<HeadLine> getHeadLineList(HeadLine headLineCondition);
}