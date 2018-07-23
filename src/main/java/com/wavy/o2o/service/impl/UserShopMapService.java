package com.wavy.o2o.service.impl;

import com.wavy.o2o.dao.UserShopMapDao;
import com.wavy.o2o.dto.UserShopMapDto;
import com.wavy.o2o.entity.UserShopMap;
import com.wavy.o2o.service.IUserShopMapService;
import com.wavy.o2o.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by WavyPeng on 2018/7/23.
 */
@Service
public class UserShopMapService implements IUserShopMapService {

    @Autowired
    private UserShopMapDao userShopMapDao;

    @Override
    public UserShopMapDto listUserShopMap(UserShopMap userShopMapCondition,
                                          int pageIndex, int pageSize) {
        // 空值判断
        if(userShopMapCondition!=null && pageIndex>=0&&pageSize>=0){
            // 页转行
            int beginIndex = PageUtil.pageIndexToRowIndex(pageIndex,pageSize);
            // 根据传入的查询条件分页返回用户积分列表信息
            List<UserShopMap> userShopMapList = userShopMapDao.queryUserShopMapList(userShopMapCondition,beginIndex,pageSize);
            // 返回总数
            int count = userShopMapDao.queryUserShopMapCount(userShopMapCondition);
            UserShopMapDto ud = new UserShopMapDto();
            ud.setUserProductMapList(userShopMapList);
            ud.setCount(count);
            return ud;
        }
        return null;
    }

    @Override
    public UserShopMap getUserShopMap(long userId, long shopId) {
        return userShopMapDao.queryUserShopMap(userId,shopId);
    }
}
