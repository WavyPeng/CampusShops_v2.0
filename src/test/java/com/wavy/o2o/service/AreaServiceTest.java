package com.wavy.o2o.service;

import com.wavy.o2o.entity.Area;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by WavyPeng on 2018/6/1.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaServiceTest{
    @Autowired
    private IAreaService areaService;

    @Test
    public void testGetAreaList(){
        List<Area> areaList = areaService.getAreaList();
        System.out.println(areaList.get(1).getAreaName());
//        assertEquals("西苑",areaList.get(0).getAreaName());
    }
}
