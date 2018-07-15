package com.wavy.o2o.dao;

import com.wavy.o2o.entity.Area;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by WavyPeng on 2018/6/1.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaDaoTest{
    @Autowired
    private AreaDao areaDao;

    @Test
    public void testAreaQuery(){
        List<Area> areaList = areaDao.queryArea();
        assertEquals(5,areaList.size());
        System.out.println(areaList.get(0).getAreaName());
    }
}
