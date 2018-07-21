package com.wavy.o2o.service.impl;

import com.wavy.o2o.dao.ShopAuthMapDao;
import com.wavy.o2o.dto.ShopAuthMapDto;
import com.wavy.o2o.entity.ShopAuthMap;
import com.wavy.o2o.enums.ShopAuthStateEnum;
import com.wavy.o2o.exception.ShopAuthMapOperationException;
import com.wavy.o2o.service.ShopAuthMapService;
import com.wavy.o2o.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WavyPeng on 2018/7/21.
 */
@Service
public class ShopAuthMapServiceImpl implements ShopAuthMapService{

    @Autowired
    private ShopAuthMapDao shopAuthMapDao;

    /**
     * 根据店铺ID分页显示该店铺的授权信息
     * @param shopId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public ShopAuthMapDto listShopAuthMapByShopId(Long shopId, Integer pageIndex, Integer pageSize) {
        // 空值判断
        if(shopId!=null && pageIndex!=null && pageSize!=null){
            // 页转行
            int beginIndex = PageUtil.pageIndexToRowIndex(pageIndex,pageSize);
            // 查询返回该店铺的授权信息列表
            List<ShopAuthMap> shopAuthMapList = shopAuthMapDao.queryShopAuthMapListByShopId(shopId,beginIndex,pageSize);
            // 返回总数
            int count = shopAuthMapDao.queryShopAuthCountByShopId(shopId);
            ShopAuthMapDto sd = new ShopAuthMapDto();
            sd.setShopAuthMapList(shopAuthMapList);
            sd.setCount(count);
            return sd;
        }
        return null;
    }

    /**
     * 根据shopAuthId返回对应的授权信息
     * @param shopAuthId
     * @return
     */
    @Override
    public ShopAuthMap getShopAuthMapById(Long shopAuthId) {
        return shopAuthMapDao.queryShopAuthMapById(shopAuthId);
    }

    /**
     * 添加授权信息（扫码时涉及）
     * @param shopAuthMap
     * @return
     * @throws ShopAuthMapOperationException
     */
    @Override
    @Transactional
    public ShopAuthMapDto addShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException {
        // 空值判断
        if(shopAuthMap!=null && shopAuthMap.getShop()!=null
                &&shopAuthMap.getShop().getShopId()!=null
                &&shopAuthMap.getEmployee()!=null&&shopAuthMap.getEmployee().getUserId()!=null){
            shopAuthMap.setCreateTime(new Date());
            shopAuthMap.setLastEditTime(new Date());
            shopAuthMap.setEnableStatus(1);
            shopAuthMap.setTitleFlag(0);
            try{
                // 添加授权信息
                int effectNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
                if(effectNum<=0){
                    throw new ShopAuthMapOperationException("添加授权失败");
                }
                return new ShopAuthMapDto(ShopAuthStateEnum.SUCCESS,shopAuthMap);
            }catch (Exception e) {
                throw new ShopAuthMapOperationException("添加授权失败：" + e.toString());
            }
        }else {
            return new ShopAuthMapDto(ShopAuthStateEnum.NULL_SHOPAUTH_ID,shopAuthMap);
        }
    }

    /**
     * 更新授权信息
     * @param shopAuthMap
     * @return
     * @throws ShopAuthMapOperationException
     */
    @Override
    @Transactional
    public ShopAuthMapDto modifyShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException {
        // 判断空值
        if(shopAuthMap == null || shopAuthMap.getShopAuthId()==null){
            return new ShopAuthMapDto(ShopAuthStateEnum.NULL_SHOPAUTH_ID,shopAuthMap);
        }else {
            try{
                int effectedNum = shopAuthMapDao.updateShopAuthMap(shopAuthMap);
                if(effectedNum<=0){
                    return new ShopAuthMapDto(ShopAuthStateEnum.INNER_ERROR);
                }
                return new ShopAuthMapDto(ShopAuthStateEnum.SUCCESS,shopAuthMap);
            }catch (Exception e){
                throw new ShopAuthMapOperationException("修改授权错误："+e.toString());
            }
        }
    }
}
