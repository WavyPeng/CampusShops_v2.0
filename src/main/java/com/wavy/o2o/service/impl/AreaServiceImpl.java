package com.wavy.o2o.service.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wavy.o2o.cache.JedisUtil;
import com.wavy.o2o.dao.AreaDao;
import com.wavy.o2o.entity.Area;
import com.wavy.o2o.exception.AreaOperationException;
import com.wavy.o2o.service.IAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WavyPeng on 2018/6/1.
 */
@Service
public class AreaServiceImpl implements IAreaService {

    @Autowired
    private AreaDao areaDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;

    private static Logger log = LoggerFactory.getLogger(AreaServiceImpl.class);

    /**
     * 获取区域列表
     * @return
     */
    @Override
    @Transactional
    public List<Area> getAreaList() {
        // 定义redis的key
        String key = AREALISTKEY;
        // 定义接收对象
        List<Area> areaList = null;
        // 定义Jackson数据转换操作类
        ObjectMapper objectMapper = new ObjectMapper();
        // 判断key是否存在
        if(!jedisKeys.exists(key)){
            // 若不存在，从数据库中取出相关数据
            areaList = areaDao.queryArea();
            // 将相关的实体类集合转换成string,存入redis里面对应的key中
            String jsonString;
            try{
                jsonString = objectMapper.writeValueAsString(areaList);
            }catch (JsonProcessingException e){
                e.printStackTrace();
                log.error(e.getMessage());
                throw new AreaOperationException(e.getMessage());
            }
            // 将结果存入缓存
            jedisStrings.set(key,jsonString);
        }else{
            // 若缓存中有数据，直接从缓存中获取
            String jsonString = jedisStrings.get(key);
            // 指定要将String转换成集合类型
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class,Area.class);
            try {
                // 将相关key对应的value里的的string转换成对象的实体类集合
                areaList = objectMapper.readValue(jsonString, javaType);
            } catch (JsonParseException e) {
                e.printStackTrace();
                log.error(e.getMessage());
                throw new AreaOperationException(e.getMessage());
            } catch (JsonMappingException e) {
                e.printStackTrace();
                log.error(e.getMessage());
                throw new AreaOperationException(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage());
                throw new AreaOperationException(e.getMessage());
            }
        }
        return areaList;
    }
}
