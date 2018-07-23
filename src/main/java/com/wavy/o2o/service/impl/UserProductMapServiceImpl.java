package com.wavy.o2o.service.impl;

import com.wavy.o2o.dao.UserProductMapDao;
import com.wavy.o2o.dto.UserProductMapDto;
import com.wavy.o2o.entity.UserProductMap;
import com.wavy.o2o.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by WavyPeng on 2018/7/23.
 */
@Service
public class UserProductMapServiceImpl implements IUserProductMapService{
    @Autowired
    private UserProductMapDao userProductMapDao;


    @Override
    public UserProductMapDto listUserProductMap(UserProductMap userProductCondition, Integer pageIndex, Integer pageSize) {
        // 判断空值
        if(userProductCondition!=null && pageIndex!=null && pageSize!=null){
            // 也转换成行
            int beginIndex = PageUtil.pageIndexToRowIndex(pageIndex,pageSize);
            // 依据查询条件分页取出列表
            List<UserProductMap> userProductMapList = userProductMapDao.queryUserProductMapList(userProductCondition,beginIndex,pageSize);
            // 按照同等的查询条件获取总数
            int count = userProductMapDao.queryUserProductMapCount(userProductCondition);
            UserProductMapDto sd = new UserProductMapDto();
            sd.setUserProductMapList(userProductMapList);
            sd.setCount(count);
            return sd;
        }
        return null;
    }
}
