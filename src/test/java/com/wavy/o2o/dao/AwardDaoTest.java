package com.wavy.o2o.dao;

import com.wavy.o2o.entity.Award;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * AwardDao测试类
 * Created by WavyPeng on 2018/7/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AwardDaoTest {
    @Autowired
    private AwardDao awardDao;

    @Test
    public void testInsertAward() throws Exception{
        long shopId = 1;
        // 创建奖品
        Award award1 = new Award();
        award1.setAwardName("测试奖品一");
        award1.setAwardImg("test1");
        award1.setPoint(5);
        award1.setPriority(1);
        award1.setEnableStatus(1);
        award1.setCreateTime(new Date());
        award1.setLastEditTime(new Date());
        award1.setShopId(shopId);
        int effectNum = awardDao.insertAward(award1);
        assertEquals(1,effectNum);

        Award award2 = new Award();
        award2.setAwardName("测试奖品二");
        award2.setAwardImg("test2");
        award2.setPoint(5);
        award2.setPriority(1);
        award2.setEnableStatus(1);
        award2.setCreateTime(new Date());
        award2.setLastEditTime(new Date());
        award2.setShopId(shopId);

        int effectNum2 = awardDao.insertAward(award2);
        assertEquals(1,effectNum2);
    }

    @Test
    public void testBQueryAwardList() throws Exception{
        Award award = new Award();
        List<Award> awardList = awardDao.queryAwardList(award,0,3);
        int count = awardDao.queryAwardCount(award);
        award.setAwardName("测试");
        assertEquals(2,awardList.size());
        count = awardDao.queryAwardCount(award);
        assertEquals(2,count);
    }
}
