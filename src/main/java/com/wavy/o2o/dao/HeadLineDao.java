package com.wavy.o2o.dao;

import com.wavy.o2o.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by WavyPeng on 2018/6/13.
 */
public interface HeadLineDao {
    /**
     * 根据传入的查询条件(头条名查询头条)
     * @param headLineCondition
     * @return
     */
    List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);
}