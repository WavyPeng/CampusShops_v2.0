package com.wavy.o2o.util;

/**
 * 分页处理工具类
 * 将前端页码转换成Dao层行号 pageIndex -> rowIndex
 * Created by WavyPeng on 2018/6/10.
 */
public class PageUtil {
    /**
     * pageIndex转换成rowIndex
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public static int pageIndexToRowIndex(int pageIndex,int pageSize){
        return (pageIndex>0)?(pageIndex-1)*pageSize:0;
    }
}
