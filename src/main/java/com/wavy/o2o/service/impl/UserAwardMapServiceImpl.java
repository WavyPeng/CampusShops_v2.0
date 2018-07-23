package com.wavy.o2o.service.impl;

import com.wavy.o2o.dao.UserAwardMapDao;
import com.wavy.o2o.dao.UserShopMapDao;
import com.wavy.o2o.dto.UserAwardMapDto;
import com.wavy.o2o.entity.UserAwardMap;
import com.wavy.o2o.entity.UserShopMap;
import com.wavy.o2o.enums.UserAwardMapStateEnum;
import com.wavy.o2o.exception.UserAwardMapOperationException;
import com.wavy.o2o.service.IUserAwardMapService;
import com.wavy.o2o.util.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WavyPeng on 2018/7/23.
 */
@Service
public class UserAwardMapServiceImpl implements IUserAwardMapService {

    @Autowired
    private UserAwardMapDao userAwardMapDao;
    @Autowired
    private UserShopMapDao userShopMapDao;

    @Override
    public UserAwardMapDto listUserAwardMap(UserAwardMap userAwardCondition, Integer pageIndex, Integer pageSize) {
        if(userAwardCondition!=null && pageIndex>=0 && pageSize>=0){
            // 页转行
            int beginIndex = PageUtil.pageIndexToRowIndex(pageIndex,pageSize);
            // 根据传入的查询条件分页返回用户积分列表信息
            List<UserAwardMap> userAwardMapList = userAwardMapDao.queryUserAwardMapList(userAwardCondition,beginIndex,pageSize);
            // 返回总数
            int count = userAwardMapDao.queryUserAwardMapCount(userAwardCondition);
            UserAwardMapDto ud = new UserAwardMapDto();
            ud.setUserAwardMapList(userAwardMapList);
            ud.setCount(count);
            return ud;
        }
        return null;
    }

    @Override
    public UserAwardMap getUserAwardMapById(long userAwardMapId) {
        return userAwardMapDao.queryUserAwardMapById(userAwardMapId);
    }

    @Override
    @Transactional
    public UserAwardMapDto addUserAwardMap(UserAwardMap userAwardMap) throws UserAwardMapOperationException {
        if(userAwardMap!=null && userAwardMap.getUser()!=null && userAwardMap.getShop()!=null && userAwardMap.getShop().getShopId()!=null){
            userAwardMap.setCreateTime(new Date());
            userAwardMap.setUsedStatus(0);
            try{
                int effectNum = 0;
                if(userAwardMap.getPoint()!=null && userAwardMap.getPoint()>0){
                    UserShopMap userShopMap = userShopMapDao.queryUserShopMap(userAwardMap.getUser().getUserId(),
                            userAwardMap.getShop().getShopId());
                    if(userAwardMap!=null){
                        if(userShopMap.getPoint()>=userAwardMap.getPoint()){
                            userShopMap.setPoint(userShopMap.getPoint()-userAwardMap.getPoint());
                            effectNum = userShopMapDao.updateUserShopMapPoint(userShopMap);
                            if(effectNum<=0) {
                                throw new UserAwardMapOperationException("更新积分信息失败");
                            }
                        }else {
                            throw new UserAwardMapOperationException("积分不足，无法领取");
                        }
                    }else {
                        throw new UserAwardMapOperationException("在本店没有积分，无法兑换奖品");
                    }
                }
                effectNum = userAwardMapDao.insertUserAwardMap(userAwardMap);
                if(effectNum<=0)
                    throw new UserAwardMapOperationException("领取奖励失败");
                return new UserAwardMapDto(UserAwardMapStateEnum.SUCCESS,userAwardMap);
            }catch (Exception e){
                throw new UserAwardMapOperationException("领取奖励失败"+e.toString());
            }
        }else {
            return new UserAwardMapDto(UserAwardMapStateEnum.NULL_USER_AWARD_MAP);
        }
    }

    @Override
    @Transactional
    public UserAwardMapDto modifyUserrAwardMap(UserAwardMap userAwardMap) throws UserAwardMapOperationException {
        return null;
    }
}
